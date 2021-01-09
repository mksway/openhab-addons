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
 * The {@link Wintermode} is an auto generated DTO
 *
 * @author Martin Klama - Initial contribution
 *
 */
@NonNullByDefault
public class Wintermode {

    @SerializedName("winter_status")
    @Expose
    private String winterStatus;

    /**
     * No args constructor for use in serialization
     *
     */
    public Wintermode() {
        this.winterStatus = "";
    }

    /**
     *
     * @param winterStatus
     */
    public Wintermode(String winterStatus) {
        super();
        this.winterStatus = winterStatus;
    }

    public String getWinterStatus() {
        return winterStatus;
    }

    public void setWinterStatus(String winterStatus) {
        this.winterStatus = winterStatus;
    }
}
