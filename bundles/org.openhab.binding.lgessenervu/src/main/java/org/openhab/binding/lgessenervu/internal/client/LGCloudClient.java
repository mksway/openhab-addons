/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.lgessenervu.internal.client;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.FormContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.util.Fields;
import org.openhab.binding.lgessenervu.internal.LGEssEnervuBindingConstants;
import org.openhab.binding.lgessenervu.internal.LGEssEnervuBindingConstants.DataSource;
import org.openhab.binding.lgessenervu.internal.LGEssEnervuBindingConstants.FailReason;
import org.openhab.binding.lgessenervu.internal.client.gson.cloud.OverviewData;
import org.openhab.binding.lgessenervu.internal.client.gson.cloud.Snapshot;
import org.openhab.binding.lgessenervu.internal.client.gson.lan.Common;
import org.openhab.binding.lgessenervu.internal.client.gson.lan.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link LGCloudClient} is responsible for retrieving data from the lg ess enervu cloud
 * which will be accessed via https://enervu.lg-ess.com
 *
 * @author Martin Klama - initial contribution
 */
@NonNullByDefault
public class LGCloudClient extends LGEssClient {

    private final Logger logger = LoggerFactory.getLogger(LGCloudClient.class);

    private final String URL_BASE_GET_SIGNINURL = "https://enervu.lg-ess.com/user/getSignInUrl";
    private final String URL_BASE_SIGNINURL = "https://de.lgaccount.com/login/sign_in?appKey=&svcCode=SVC951&country=DE&language=de-DE&callbackUrl=https%253A%252F%252Fenervu.lg-ess.com%252Femp%252FendUserSignIn&show_3rd_party_login=N&expire_session=N";
    private final String URL_LOGIN = "https://de.emp.lgsmartplatform.com/emp/v2.0/account/session/%s";
    private final String URL_BASE_ENDUSERSIGNIN = "https://enervu.lg-ess.com/emp/endUserSignIn?sid=%s";
    private final String URL_GETSNAPSHOTDATA = "https://enervu.lg-ess.com/installer/sm/snapshotData.json";
    private final String URL_GET15MinOverview = "https://enervu.lg-ess.com/installer/sm/get15MinOverViewData.json";
    private final String URL_GETBASICDATA = "https://enervu.lg-ess.com/user/endUserMain.do";

    public LGCloudClient(@Nullable HttpClient client) {
        super(client);
    }

    public String get_SHA_512_SecurePassword(String passwordToHash, String salt) {
        String generatedPassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            logger.error("{}", ex.getMessage(), ex);
            if (null != mycallb) {
                mycallb.responseCallbackError(FailReason.CRITICAL);
            }
        }
        return generatedPassword;
    }

    @Override
    public void setPassword(String passw) {
        this.password = get_SHA_512_SecurePassword(passw, "");
    }

    private Request addHeader(Request req, String timestamp, String signature) {
        req.getHeaders().add("Accept",
                "application/json,text/html,application/xhtml+xml,application/xml,image/webp,*/*; q=0.01");

        req.getHeaders().add("X-Lge-Svccode", "SVC709");
        req.getHeaders().add("X-Device-Country", "DE");
        req.getHeaders().add("X-Device-Language", "de-DE");
        req.getHeaders().add("X-Device-Publish-Flag", "Y");
        req.getHeaders().add("X-Device-Type", "P01");
        req.getHeaders().add("X-Application-Key", LGEssEnervuBindingConstants.LG_API_V1_APPKEY);
        req.getHeaders().add("X-Device-Platform", "PC");
        req.getHeaders().add("X-Device-Language-Type", "IETF");
        req.getHeaders().add("X-Timestamp", timestamp);
        req.getHeaders().add("X-Signature", signature);
        return req;
    }

    private String extractSessionID(String response) {
        String sessionid = "";

        try {
            sessionid = response.substring(response.indexOf("emp;"));
            sessionid = sessionid.substring(0, sessionid.indexOf("\""));
        } catch (Exception e) {
            sessionid = "";
        }
        return sessionid;
    }

    /**
     *
     * @param response http response as string
     * @param type either TimestampG or SignatureG
     * @return the value for either TimeStampG or SignatureG
     */
    private String extractTimestampOrSignature(String response, String type) {
        String retval = "";

        if (response.isBlank()) {
            return retval;
        }

        try {
            retval = response.substring(response.indexOf(type + " ||"));
            retval = retval.substring(retval.indexOf("\"") + 1, retval.indexOf(";") - 1);
        } catch (Exception e) {
            retval = "";
        }
        return retval;
    }

    private String extractSystemID(String response) {
        String retval = "";

        try {
            retval = response.substring(response.indexOf("var systemId"), response.indexOf("var systemId") + 25);
            retval = retval.substring(retval.indexOf("'") + 1, retval.indexOf(";") - 1);
        } catch (Exception e) {
            retval = "";
        }
        return retval;
    }

    private String extractESSID(String response) {
        String retval = "";

        try {
            retval = response.substring(response.indexOf("id=\"essId\""));
            retval = retval.substring(retval.indexOf("value"));
            retval = retval.substring(retval.indexOf("\"") + 1, retval.indexOf("/") - 1);
        } catch (Exception e) {
            retval = "";
        }
        return retval;
    }

    private String performRequest(HttpMethod method, String url, String tstamp, String signature) {
        Request req = null;
        ContentResponse res = null;
        String response = "";

        final HttpClient myhttpclient = this.httpClient;

        if (myhttpclient != null) {
            req = myhttpclient.newRequest(url).method(method).timeout(timeout, TimeUnit.SECONDS);
            req = addHeader(req, tstamp, signature);

            if (HttpMethod.POST == method) {
                Fields fields = new Fields(true);
                // not neccessary to post password every time..
                if (url.contains(userid)) {
                    fields.put("user_auth2", password);
                }
                fields.put("svc_list", "SVC951");
                fields.put("countryCode", "DE");
                fields.put("isMobile", "false");
                fields.put("type", "E");
                req.content(new FormContentProvider(fields));
            }

            try {
                res = req.send();

                if (res.getStatus() != HttpStatus.OK_200) {
                    response = "";
                } else {
                    response = res.getContentAsString();
                }

            } catch (TimeoutException ex) {
                response = "";
            } catch (InterruptedException e) {
                response = "";
            } catch (ExecutionException e) {
                response = "";
            }

        }

        return response;
    }

    @Override
    public void Login() {

        if (true == isLoginInProgess) {
            return;
        } else {
            isLoginInProgess = true;
        }

        String response = "";

        // get base url
        performRequest(HttpMethod.POST, URL_BASE_GET_SIGNINURL, "", "");
        response = performRequest(HttpMethod.GET, URL_BASE_SIGNINURL, "", "");

        String timestamp = extractTimestampOrSignature(response, "timestampG");
        String signature = extractTimestampOrSignature(response, "signatureG");

        if ((timestamp.isBlank() || signature.isBlank()) && (null != mycallb)) {
            mycallb.responseCallbackError(FailReason.PARSING_ERROR);
            return;
        }

        response = performRequest(HttpMethod.POST, String.format(URL_LOGIN, userid), timestamp, signature);
        sessionid = extractSessionID(response);

        if (sessionid.isBlank() && mycallb != null) {
            mycallb.responseCallbackError(FailReason.WRONG_CREDENTIALS);
            return;
        }

        response = performRequest(HttpMethod.GET, String.format(URL_BASE_ENDUSERSIGNIN, sessionid), "", "");

        if (response.isBlank()) {
            setLoginstatus(false, FailReason.WRONG_CREDENTIALS);
            return;
        }

        response = performRequest(HttpMethod.POST, URL_GETBASICDATA, "", "");

        SYSTEM_ID = extractSystemID(response);
        ESS_ID = extractESSID(response);

        if (false == ESS_ID.isBlank() && false == SYSTEM_ID.isBlank()) {
            setLoginstatus(true, FailReason.NONE);
        } else {
            setLoginstatus(false, FailReason.WRONG_CREDENTIALS);
        }
    }

    private ResponseData convertCloudAPItoLANAPI(@Nullable Snapshot snapshotdata, @Nullable OverviewData overview) {

        Common common = new Common();
        Statistics stats = new Statistics();

        ResponseData responseData = null;

        // cloud client does not support separate string readings (voltage/current/power ) of string 1 to 5

        if (null != snapshotdata) {

            common.getPV().setPv1Power(String.valueOf(snapshotdata.getEssSnapshotList().get(0).getPvPower()));

            common.getBATT().setSoc(String.valueOf(snapshotdata.getEssSnapshotList().get(0).getBattSoc()));
            common.getBATT().setStatus(String.valueOf(snapshotdata.getEssSnapshotList().get(0).getBattStatus()));
            common.getBATT().setDcPower(String.valueOf(snapshotdata.getEssSnapshotList().get(0).getBattPower()));

            common.getGRID().setActivePower(String.valueOf(snapshotdata.getEssSnapshotList().get(0).getGridPower()));

            common.getLOAD().setLoadPower(String.valueOf(snapshotdata.getEssSnapshotList().get(0).getLoadPower()));

            stats.getDirection().setIsDirectConsuming(
                    String.valueOf(snapshotdata.getEssSnapshotList().get(0).getIsDirectConsuming()));
            stats.getDirection().setIsBatteryCharging(
                    String.valueOf(snapshotdata.getEssSnapshotList().get(0).getIsBatteryCharging()));
            stats.getDirection().setIsBatteryDischarging(
                    String.valueOf(snapshotdata.getEssSnapshotList().get(0).getIsBatteryDischarging()));
            stats.getDirection()
                    .setIsGridSelling(String.valueOf(snapshotdata.getEssSnapshotList().get(0).getIsGridSelling()));
            stats.getDirection()
                    .setIsGridBuying(String.valueOf(snapshotdata.getEssSnapshotList().get(0).getIsGridBuying()));
            stats.getDirection().setIsChargingFromGrid(
                    String.valueOf(snapshotdata.getEssSnapshotList().get(0).getIsChargingFromGrid()));

        }

        if (null != overview) {

            common.getLOAD().setTodayLoadConsumptionSum(String.valueOf(overview.getDayResult().getTotalConsumpE()));
            common.getLOAD().setMonthLoadConsumptionSum(String.valueOf(
                    overview.getMonthResult().getTotalGridEBuy() + overview.getMonthResult().getTotalDirectConsumpE()));

            common.getPV().setTodayPvGenerationSum(String.valueOf(overview.getDayResult().getTotalPvE()));
            common.getPV().setTodayMonthPvGenerationSum(String.valueOf(overview.getMonthResult().getTotalPvE()));

            common.getGRID().setTodayGridFeedInEnergy(String.valueOf(overview.getDayResult().getTotalGridESell()));
            common.getGRID().setMonthGridFeedInEnergy(String.valueOf(overview.getMonthResult().getTotalGridESell()));

            common.getBATT().setTodayBattChargeEnergy(String.valueOf(overview.getDayResult().getTotalBattChg()));
            common.getBATT().setMonthBattChargeEnergy(String.valueOf(overview.getMonthResult().getTotalBattChg()));

            common.getBATT().setTodayBattDischargeEnery(String.valueOf(overview.getDayResult().getTotalBattDis()));
            common.getBATT().setMonthBattDischargeEnergy(String.valueOf(overview.getMonthResult().getTotalBattDis()));

            common.getLOAD()
                    .setTodayPvDirectConsumptionEnegy(String.valueOf(overview.getDayResult().getTotalDirectConsumpE()));
            common.getLOAD().setMonthPvDirectConsumptionEnergy(
                    String.valueOf(overview.getMonthResult().getTotalDirectConsumpE()));

            common.getLOAD()
                    .setMonthGridPowerPurchaseEnergy(String.valueOf(overview.getMonthResult().getTotalGridEBuy()));
            common.getGRID()
                    .setMonthGridPowerPurchaseEnergy(String.valueOf(overview.getMonthResult().getTotalGridEBuy()));

            common.getLOAD()
                    .setTodayGridPowerPurchaseEnergy(String.valueOf(overview.getDayResult().getTotalGridEBuy()));
            common.getGRID()
                    .setTodayGridPowerPurchaseEnergy(String.valueOf(overview.getDayResult().getTotalGridEBuy()));
            // common.getPV().setTodayPvGenerationSum())) * co2factor

        }

        responseData = new ResponseData(common, stats, DataSource.CLOUD_API_V1);
        return responseData;
    }

    @Override
    public void getCurrentData() {

        @Nullable
        Snapshot data = null;
        ResponseData responseData = null;

        String jsonresp = "";

        if (false == getLoginStatus()) {
            return;
        }

        final HttpClient myhttpclient = this.httpClient;

        if (myhttpclient != null) {

            Request req = myhttpclient.newRequest(URL_GETSNAPSHOTDATA).method(HttpMethod.POST).timeout(timeout,
                    TimeUnit.SECONDS);

            ContentResponse res = null;
            Fields fields = new Fields(true);
            fields.put("systemId", SYSTEM_ID);
            fields.put("essId", ESS_ID);
            req.content(new FormContentProvider(fields));
            req.method(HttpMethod.POST);
            req.getHeaders().add("START_POLL", "Y");

            try {
                res = req.send();
                jsonresp = res.getContentAsString();

                data = gson.fromJson(jsonresp, Snapshot.class);
                // match old cloud api data to lan/new cloud api data
                responseData = convertCloudAPItoLANAPI(data, null);

            } catch (Exception e) {
                logger.error("got exception on send! :/ {}", e.getMessage());
                responseData = new ResponseData();
            }

            if (mycallb != null) {
                mycallb.responseCallbackCurrentData(responseData);
            } else {
                setLoginstatus(false, FailReason.NONE);
            }
        } else {
            setLoginstatus(false, FailReason.NONE);
        }
    }

    /**
     * tries to retrieve all data from given date
     *
     * @param date in format yyyy-mm-dd
     * @return json response of current load
     */
    @Override
    public void get15MinOverview() {
        @Nullable
        OverviewData data = null;

        ResponseData responseData = null;
        String jsonresp = "";
        ContentResponse res = null;

        if (false == getLoginStatus()) {
            return;
        }

        final HttpClient myhttpclient = this.httpClient;
        if (myhttpclient != null) {

            Request req = myhttpclient.newRequest(URL_GET15MinOverview).method(HttpMethod.POST).timeout(timeout,
                    TimeUnit.SECONDS);

            Fields fields = new Fields(true);
            fields.put("systemId", SYSTEM_ID);
            req.content(new FormContentProvider(fields));

            try {
                res = req.send();
                jsonresp = res.getContentAsString();
                data = gson.fromJson(jsonresp, OverviewData.class);
                // match old cloud api data to lan/new cloud api data
                responseData = convertCloudAPItoLANAPI(null, data);
                responseData.setDatasource(DataSource.CLOUD_API_V1);
            } catch (Exception e) {
                logger.error("{}", e.getMessage(), e);
                responseData = new ResponseData();
            }

            if (mycallb != null) {
                mycallb.responseCallbackDaily(responseData);
            } else {
                setLoginstatus(false, FailReason.NONE);
            }
        } else {
            setLoginstatus(false, FailReason.NONE);
        }
    }
}
