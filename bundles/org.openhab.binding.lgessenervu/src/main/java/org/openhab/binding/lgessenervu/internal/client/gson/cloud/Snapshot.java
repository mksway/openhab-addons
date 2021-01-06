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
package org.openhab.binding.lgessenervu.internal.client.gson.cloud;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Auto generated class
 *
 * @author SkyRaVeR - Initial contribution
 */

public class Snapshot {
    @SerializedName("essSnapshotList")
    @Expose
    private List<EssSnapshotList> essSnapshotList = null;
    @SerializedName("pmsModelName")
    @Expose
    private String pmsModelName;

    public List<EssSnapshotList> getEssSnapshotList() {
        return essSnapshotList;
    }

    public void setEssSnapshotList(List<EssSnapshotList> essSnapshotList) {
        this.essSnapshotList = essSnapshotList;
    }

    public String getPmsModelName() {
        return pmsModelName;
    }

    public void setPmsModelName(String pmsModelName) {
        this.pmsModelName = pmsModelName;
    }
}
