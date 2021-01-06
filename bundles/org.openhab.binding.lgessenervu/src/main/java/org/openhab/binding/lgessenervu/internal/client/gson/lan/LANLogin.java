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
package org.openhab.binding.lgessenervu.internal.client.gson.lan;

import org.eclipse.jdt.annotation.NonNullByDefault;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Auto generated class
 *
 * @author Martin Klama - Initial contribution
 *
 */
@NonNullByDefault
public class LANLogin {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("auth_key")
    @Expose
    private String authKey;
    @SerializedName("regnum")
    @Expose
    private String regnum;
    @SerializedName("role")
    @Expose
    private String role;

    /**
     *
     * @param authKey
     * @param role
     * @param regnum
     * @param status
     */
    public LANLogin(String status, String authKey, String regnum, String role) {
        this.status = status;
        this.authKey = authKey;
        this.regnum = regnum;
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getSuccess() {
        return status.toLowerCase().equals("success") ? true : false;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getRegnum() {
        return regnum;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
