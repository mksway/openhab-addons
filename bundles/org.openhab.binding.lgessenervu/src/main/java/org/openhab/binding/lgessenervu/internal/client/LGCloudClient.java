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
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.FormContentProvider;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.util.Fields;
import org.openhab.binding.lgessenervu.internal.LGEssEnervuBindingConstants.DataSource;
import org.openhab.binding.lgessenervu.internal.LGEssEnervuBindingConstants.FailReason;
import org.openhab.binding.lgessenervu.internal.client.gson.cloud.OverviewData;
import org.openhab.binding.lgessenervu.internal.client.gson.cloud.Snapshot;
import org.openhab.binding.lgessenervu.internal.client.gson.lan.BATT;
import org.openhab.binding.lgessenervu.internal.client.gson.lan.Common;
import org.openhab.binding.lgessenervu.internal.client.gson.lan.Direction;
import org.openhab.binding.lgessenervu.internal.client.gson.lan.GRID;
import org.openhab.binding.lgessenervu.internal.client.gson.lan.LOAD;
import org.openhab.binding.lgessenervu.internal.client.gson.lan.PCS;
import org.openhab.binding.lgessenervu.internal.client.gson.lan.PV;
import org.openhab.binding.lgessenervu.internal.client.gson.lan.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link LGCloudClient} is responsible for retrieving data from the lg ess enervu cloud.
 *
 *
 * @author SkyRaVeR - initial contribution
 */
@NonNullByDefault
public class LGCloudClient extends LGEssClient {

    private final Logger logger = LoggerFactory.getLogger(LGCloudClient.class);

    // private final String URL_BASE = "https://enervu.lg-ess.com/";
    // private final String URL_BASE_ENDUSERLOGIN = "https://enervu.lg-ess.com/end_user_login.do";
    private final String URL_BASE_GETSIGNINURL = "https://enervu.lg-ess.com/user/getSignInUrl";
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
        }
        return generatedPassword;
    }

    @Override
    public void setPassword(String passw) {
        this.password = get_SHA_512_SecurePassword(passw, "");
    }

    @Override
    public void Login() {

        if (true == isLoginInProgess) {
            return;
        } else {
            isLoginInProgess = true;
        }

        String response = "";
        ContentResponse res = null;
        // Request req = null;
        Fields fields = null;

        final HttpClient myhttpclient = this.httpClient;
        if (myhttpclient != null) {

            // get signin url
            Request req = myhttpclient.newRequest(URL_BASE_GETSIGNINURL).method(HttpMethod.POST).timeout(timeout,
                    TimeUnit.SECONDS);
            fields = new Fields(true);
            fields.put("countryCode", "DE");
            fields.put("isMobile", "false");
            req.content(new FormContentProvider(fields));
            req.method(HttpMethod.POST);
            // add header
            req.getHeaders().add("Host", "enervu.lg-ess.com");
            req.getHeaders().add("Accept", "application/json, text/javascript, */*; q=0.01");
            req.getHeaders().add("Accept-Language", "de,en-US;q=0.7,en;q=0.3");
            req.getHeaders().add("Accept-Encoding", "gzip, deflate, br");
            req.getHeaders().add("X-Requested-With", "XMLHttpRequest");
            req.getHeaders().add("Origin", "https://enervu.lg-ess.com");
            req.getHeaders().add("Referer", "https://enervu.lg-ess.com/end_user_login.do");

            try {
                res = req.send();

                req = myhttpclient.newRequest(URL_BASE_SIGNINURL).method(HttpMethod.GET).timeout(timeout,
                        TimeUnit.SECONDS);
                res = req.send();

                response = res.getContentAsString();

            } catch (Exception ex) {
                logger.error("{}", ex.getMessage(), ex);
            }

            if (null == response || response.isBlank()) {
                setLoginstatus(false, FailReason.COMMUNICATION_ERROR);
                return;
            }

            // extract timestamp and signature...
            String timestamp = response.substring(response.indexOf("timestampG ||"));
            timestamp = timestamp.substring(timestamp.indexOf("\"") + 1, timestamp.indexOf(";") - 1);

            String signature = response.substring(response.indexOf("signatureG ||"));
            signature = signature.substring(signature.indexOf("\"") + 1, signature.indexOf(";") - 1);

            // login post
            fields = new Fields(true);
            fields.put("user_auth2", password);
            fields.put("svc_list", "SVC951");

            req = myhttpclient.newRequest(String.format(URL_LOGIN, userid)).method(HttpMethod.POST).timeout(timeout,
                    TimeUnit.SECONDS);

            req.getHeaders().add("Accept", "application/json");
            req.getHeaders().add("Accept-Encoding", "gzip, deflate, br");
            req.getHeaders().add("Access-Control-Allow-Origin", "*");

            req.getHeaders().add("X-Lge-Svccode", "SVC709");
            req.getHeaders().add("X-Device-Country", "DE");
            req.getHeaders().add("X-Device-Language", "de-DE");
            req.getHeaders().add("X-Device-Publish-Flag", "Y");
            req.getHeaders().add("X-Device-Type", "P01");
            req.getHeaders().add("X-Application-Key", "6V1V8H2BN5P9ZQGOI5DAQ92YZBDO3EK9");
            req.getHeaders().add("X-Device-Platform", "PC");
            req.getHeaders().add("X-Device-Language-Type", "IETF");

            req.getHeaders().add("Origin", "https://de.lgaccount.com");
            req.getHeaders().add("Host", "de.emp.lgsmartplatform.com");
            req.getHeaders().add("Referer", "https://de.lgaccount.com/");
            req.getHeaders().add("X-Timestamp", timestamp);
            req.getHeaders().add("X-Signature", signature);

            req.agent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:72.0) Gecko/20100101 Firefox/72.0");
            req.accept("application/json");
            // req.header("X-Requested-With", "XMLHttpRequest");
            req.content(new FormContentProvider(fields));
            req.method(HttpMethod.POST);

            try {
                res = req.send();
                logger.warn("status -> {}", res.getStatus());
                if (res.getStatus() == HttpStatus.FORBIDDEN_403 || res.getStatus() == HttpStatus.NOT_ACCEPTABLE_406) {
                    response = "";
                } else if (res.getStatus() == HttpStatus.OK_200) {

                    response = res.getContentAsString();
                    sessionid = response.substring(response.indexOf("emp;"));
                    sessionid = sessionid.substring(0, sessionid.indexOf("\""));

                    req = myhttpclient.newRequest(String.format(URL_BASE_ENDUSERSIGNIN, sessionid))
                            .method(HttpMethod.GET).timeout(timeout, TimeUnit.SECONDS);
                    res = req.send();
                }

            } catch (Exception ex) {
                logger.error("{}", ex.getMessage(), ex);
                response = "";
            }

            if (response.isBlank()) {
                setLoginstatus(false, FailReason.WRONG_CREDENTIALS);
                return;
            }

            getSystemIDs();
        } else {
            setLoginstatus(false, FailReason.NONE);
        }

    }

    /**
     * retrieve System and ESS-ID which are needed for further requests
     */
    private void getSystemIDs() {
        ContentResponse res = null;

        final HttpClient myhttpclient = this.httpClient;

        if (myhttpclient != null) {

            Request req = myhttpclient.newRequest(URL_GETBASICDATA).method(HttpMethod.POST).timeout(timeout,
                    TimeUnit.SECONDS);

            String response = "";

            Fields fields = new Fields(true);
            fields.put("isMobile", "N");
            fields.put("type", "E");
            req.content(new FormContentProvider(fields));
            req.method(HttpMethod.POST);

            req.getHeaders().add("Host", "enervu.lg-ess.com");
            req.getHeaders().add("Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            req.getHeaders().add("Accept-Language", "de,en-US;q=0.7,en;q=0.3");
            req.getHeaders().add("Accept-Encoding", "gzip, deflate, br");
            req.getHeaders().add("Origin", "https://enervu.lg-ess.com");
            req.getHeaders().add("Referer", "https://enervu.lg-ess.com/");
            req.getHeaders().add("DNT", "1");
            req.getHeaders().add("Upgrade-Insecure-Requests", "1");

            try {
                res = req.send();
                response = res.getContentAsString();
            } catch (Exception ex) {
                logger.error("{}", ex.getMessage(), ex);
            }

            if (response.isBlank()) {
                setLoginstatus(false, FailReason.COMMUNICATION_ERROR);
                return;
            }

            // extract systemid
            String systemid = "";
            try {
                systemid = response.substring(response.indexOf("var systemId"), response.indexOf("var systemId") + 25);
                systemid = systemid.substring(systemid.indexOf("'") + 1, systemid.indexOf(";") - 1);
                SYSTEM_ID = systemid;

                String nowdate = "";
                nowdate = response.substring(response.indexOf("nowDate"), response.indexOf("nowDate") + 25);
                nowdate = nowdate.substring(nowdate.indexOf("'") + 1, nowdate.indexOf(";") - 1);
                // TODAY = nowdate;

                String essId = "";
                essId = response.substring(response.indexOf("id=\"essId\""));
                essId = essId.substring(essId.indexOf("value"));
                essId = essId.substring(essId.indexOf("\"") + 1, essId.indexOf("/") - 1);
                ESS_ID = essId;
            } catch (Exception ex) {
                logger.error("{}", ex.getMessage(), ex);
            }

            if (false == ESS_ID.isBlank() && false == SYSTEM_ID.isBlank()) {
                setLoginstatus(true, FailReason.NONE);
            } else {
                setLoginstatus(false, FailReason.WRONG_CREDENTIALS);
            }
        } else {
            setLoginstatus(false, FailReason.NONE);
        }
    }

    private ResponseData convertCloudAPItoLANAPI(@Nullable Snapshot data) {

        Common common = null;
        Statistics stats = null;
        Direction direction = null;
        ResponseData responseData = null;

        if (null == data) {
            return new ResponseData();
        }

        PV pv = new PV();
        pv.setPv1Power(String.valueOf(data.getEssSnapshotList().get(0).getPvPower()));

        BATT batt = new BATT();
        batt.setSoc(String.valueOf(data.getEssSnapshotList().get(0).getBattSoc()));
        batt.setStatus(String.valueOf(data.getEssSnapshotList().get(0).getBattStatus()));
        batt.setDcPower(String.valueOf(data.getEssSnapshotList().get(0).getBattPower()));

        GRID grid = new GRID();
        grid.setActivePower(String.valueOf(data.getEssSnapshotList().get(0).getGridPower()));

        LOAD load = new LOAD();
        load.setLoadPower(String.valueOf(data.getEssSnapshotList().get(0).getLoadPower()));

        common = new Common(pv, batt, grid, load, new PCS());

        stats = new Statistics();
        direction = new Direction();
        direction.setIsDirectConsuming(String.valueOf(data.getEssSnapshotList().get(0).getIsDirectConsuming()));
        direction.setIsBatteryCharging(String.valueOf(data.getEssSnapshotList().get(0).getIsBatteryCharging()));
        direction.setIsBatteryDischarging(String.valueOf(data.getEssSnapshotList().get(0).getIsBatteryDischarging()));
        direction.setIsGridSelling(String.valueOf(data.getEssSnapshotList().get(0).getIsGridSelling()));
        direction.setIsGridBuying(String.valueOf(data.getEssSnapshotList().get(0).getIsGridBuying()));
        direction.setIsChargingFromGrid(String.valueOf(data.getEssSnapshotList().get(0).getIsChargingFromGrid()));

        stats.setDirection(direction);

        responseData = new ResponseData(common, stats, DataSource.CLOUD_API_V1);

        return responseData;
    }

    @Override
    public void getCurrentData() {

        logger.warn("DATA FROM CLOUD");

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
                logger.debug("Snapshot -> {}", jsonresp);
                data = gson.fromJson(jsonresp, Snapshot.class);
                // match old cloud api data to lan/new cloud api data

                responseData = convertCloudAPItoLANAPI(data);

            } catch (Exception e) {
                logger.error("got exception on send! :/ {}", e.getMessage());
            }

            if (null == responseData) {
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
        OverviewData data = null;
        String jsonresp = "";

        logger.debug("get15MinSOverview called with {}", getLoginStatus());
        if (false == getLoginStatus()) {
            return;
        }

        final HttpClient myhttpclient = this.httpClient;
        if (myhttpclient != null) {

            Request req = myhttpclient.newRequest(URL_GET15MinOverview).method(HttpMethod.POST).timeout(timeout,
                    TimeUnit.SECONDS);

            // Request req = httpClient.POST(URL_GET15MinOverview);
            ContentResponse res = null;
            Fields fields = new Fields(true);
            fields.put("systemId", SYSTEM_ID);
            req.content(new FormContentProvider(fields));
            req.method(HttpMethod.POST);

            try {
                res = req.send();
                jsonresp = res.getContentAsString();
            } catch (Exception e) {
                logger.error("{}", e.getMessage(), e);
            }
            if (jsonresp.isBlank() || jsonresp.toLowerCase().contains("error")
                    || jsonresp.toLowerCase().contains("nosession") || jsonresp.toLowerCase().contains("timeout")) {
                setLoginstatus(false, FailReason.NONE);
                return;
            }

            try {
                data = gson.fromJson(jsonresp, OverviewData.class);
            } catch (Exception e) {
                logger.error("{}", e.getMessage());
            }

            if (null != mycallb && null != data) {
                mycallb.responseCallbackDaily(data);
            } else {
                setLoginstatus(false, FailReason.NONE);
            }
        } else {
            setLoginstatus(false, FailReason.NONE);
        }
    }
}
