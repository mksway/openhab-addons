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

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.openhab.binding.lgessenervu.internal.LGEssEnervuBindingConstants.FailReason;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * This class is a prototype for the client towards the lg enervu data.
 *
 * It may retrieve data from either from cloud or the LAN.
 *
 * @author Martin Klama - Initial contribution
 *
 */
@NonNullByDefault
public abstract class LGEssClient {

    private final Logger logger = LoggerFactory.getLogger(LGEssClient.class);

    protected final @Nullable HttpClient httpClient;
    protected String hostname;
    protected String sessionid;
    protected String SYSTEM_ID;
    protected String ESS_ID;
    protected String userid;
    protected String password;
    protected boolean loginstatus;
    protected boolean isLoginInProgess;
    protected int timeout;

    protected Gson gson;

    @Nullable
    protected IResponseCallback mycallb;

    public LGEssClient(@Nullable HttpClient client) {
        logger.debug("LGEssClient client started");
        httpClient = client;
        hostname = "";
        sessionid = "";
        SYSTEM_ID = "";
        ESS_ID = "";
        userid = "";
        password = "";
        loginstatus = false;
        timeout = 5000;
        isLoginInProgess = false;
        gson = new Gson();
    }

    public void registerCallback(IResponseCallback callback) {
        logger.debug("LGEssClient callback registered!");
        mycallb = callback;
    }

    public void unregisterCallback() {
        logger.debug("LGEssClient callback un-registered!");
        mycallb = null;
    }

    public boolean getLoginStatus() {
        return this.loginstatus;
    }

    public void setLoginstatus(boolean loginstatus, FailReason reason) {
        this.loginstatus = loginstatus;

        if (false == loginstatus) {
            SYSTEM_ID = "";
            ESS_ID = "";
            sessionid = "";
        } else {
            isLoginInProgess = false;
        }

        if (null != mycallb) {
            mycallb.responseCallbackLoggedIn(this.loginstatus, reason);
        }
    }

    public void setHostname(String host) {
        this.hostname = host;
    }

    public String getHostname() {
        return this.hostname;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public void setUserID(String uid) {
        this.userid = uid;
    }

    public void setPassword(String passw) {
        this.password = passw;
    }

    public String getPassword() {
        return this.password;
    }

    public abstract void Login();

    public abstract void get15MinOverview();

    public abstract void getCurrentData();
}
