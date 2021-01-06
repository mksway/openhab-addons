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
public class Common {

    @SerializedName("PV")
    @Expose
    private PV pV;
    @SerializedName("BATT")
    @Expose
    private BATT bATT;
    @SerializedName("GRID")
    @Expose
    private GRID gRID;
    @SerializedName("LOAD")
    @Expose
    private LOAD lOAD;
    @SerializedName("PCS")
    @Expose
    private PCS pCS;

    public Common() {
        this.pV = new PV();
        this.bATT = new BATT();
        this.gRID = new GRID();
        this.lOAD = new LOAD();
        this.pCS = new PCS();
    }

    /**
     *
     * @param pCS
     * @param bATT
     * @param lOAD
     * @param pV
     * @param gRID
     */
    public Common(PV pV, BATT bATT, GRID gRID, LOAD lOAD, PCS pCS) {
        this.pV = pV;
        this.bATT = bATT;
        this.gRID = gRID;
        this.lOAD = lOAD;
        this.pCS = pCS;
    }

    public PV getPV() {
        return pV;
    }

    public void setPV(PV pV) {
        this.pV = pV;
    }

    public BATT getBATT() {
        return bATT;
    }

    public void setBATT(BATT bATT) {
        this.bATT = bATT;
    }

    public GRID getGRID() {
        return gRID;
    }

    public void setGRID(GRID gRID) {
        this.gRID = gRID;
    }

    public LOAD getLOAD() {
        return lOAD;
    }

    public void setLOAD(LOAD lOAD) {
        this.lOAD = lOAD;
    }

    public PCS getPCS() {
        return pCS;
    }

    public void setPCS(PCS pCS) {
        this.pCS = pCS;
    }
}
