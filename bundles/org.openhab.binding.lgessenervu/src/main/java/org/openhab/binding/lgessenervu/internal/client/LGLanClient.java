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

import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.openhab.binding.lgessenervu.internal.LGEssEnervuBindingConstants.DataSource;
import org.openhab.binding.lgessenervu.internal.LGEssEnervuBindingConstants.FailReason;
import org.openhab.binding.lgessenervu.internal.client.gson.lan.Common;
import org.openhab.binding.lgessenervu.internal.client.gson.lan.LANLogin;
import org.openhab.binding.lgessenervu.internal.client.gson.lan.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Auto generated class
 *
 * @author Martin Klama - Initial contribution
 *
 */
@NonNullByDefault
public class LGLanClient extends LGEssClient {

    private String json_req_login = "{\"password\":\"%s\"}";
    private String json_req_body = "{\"auth_key\":\"%s\"}";

    private String endpoint_login = "v1/user/setting/login";
    private String endpoint_home = "v1/user/essinfo/home";
    private String endpoint_common = "v1/user/essinfo/common";

    public LGLanClient(@Nullable HttpClient client) {
        super(client);
    }

    private final Logger logger = LoggerFactory.getLogger(LGLanClient.class);

    private String createEndpoint(String endpoint) {
        return String.format("https://%s/%s", getHostname(), endpoint);
    }

    @Override
    public void Login() {

        LANLogin loginresponse = null;
        String tmp;

        tmp = performRequest(createEndpoint(endpoint_login), HttpMethod.PUT,
                String.format(json_req_login, getPassword()));

        if (true == isLoginInProgess) {
            return;
        } else {
            isLoginInProgess = true;
        }

        try {
            loginresponse = gson.fromJson(tmp, LANLogin.class);
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
        }

        if (null == loginresponse) {
            loginresponse = new LANLogin("error", "", "", "");
        }

        if (true == loginresponse.getSuccess()) {
            sessionid = loginresponse.getAuthKey();
        }

        setLoginstatus(loginresponse.getSuccess(),
                loginresponse.getSuccess() ? FailReason.NONE : FailReason.WRONG_CREDENTIALS);
    }

    /***
     * tries to send a rest request to the ac unit
     *
     * @param requesturi enpoint to request
     * @param payload json payload
     * @return JSONData containing all informations
     */
    private String performRequest(String requesturi, HttpMethod method, String payload) {

        String response = "";
        ContentResponse res = null;
        Request req = null;

        final HttpClient myhttpclient = this.httpClient;

        if (null != myhttpclient) {

            req = myhttpclient.newRequest(requesturi).method(method).timeout(timeout, TimeUnit.SECONDS);
            req.header(HttpHeader.CONTENT_TYPE, "application/json");
            req.content(new StringContentProvider(payload, "utf-8"));

            try {
                res = req.send();
                response = res.getContentAsString();
            } catch (Exception e) {
                logger.error("{}", e.getMessage());
                response = "";
            }
        }

        return response;
    }

    @Override
    public void getCurrentData() {

        logger.warn("DATA FROM LAN");

        String jsonresp_common = performRequest(createEndpoint(endpoint_common), HttpMethod.POST,
                String.format(json_req_body, sessionid));

        String jsonresp_stats = performRequest(createEndpoint(endpoint_home), HttpMethod.POST,
                String.format(json_req_body, sessionid));

        ResponseData responseData = new ResponseData();
        Common com = null;
        Statistics stats = null;

        try {
            com = gson.fromJson(jsonresp_common, Common.class);
            stats = gson.fromJson(jsonresp_stats, Statistics.class);
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            // auth_key failed
        }

        responseData.setDatasource(DataSource.LAN_API);

        if (null != com) {
            responseData.setCommon(com);
        }
        if (null != stats) {
            responseData.setStats(stats);
        }

        final IResponseCallback tmpcallb = mycallb;

        if (tmpcallb != null) {
            tmpcallb.responseCallbackCurrentData(responseData);
            tmpcallb.responseCallbackDaily(responseData);
        } else {
            setLoginstatus(false, FailReason.NONE);
        }
    }

    @Override
    public void get15MinOverview() {
        // not needed in lan mode. all data is provided within the same request..
    }

    // post
}
