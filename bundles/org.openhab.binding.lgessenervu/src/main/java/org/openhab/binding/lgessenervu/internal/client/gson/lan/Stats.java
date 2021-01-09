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
 * The {@link Stats} is an auto generated DTO
 *
 * @author Martin Klama - Initial contribution
 *
 */
@NonNullByDefault
public class Stats {

    @SerializedName("pcs_pv_total_power")
    @Expose
    private String pcsPvTotalPower;
    @SerializedName("batconv_power")
    @Expose
    private String batconvPower;
    @SerializedName("bat_use")
    @Expose
    private String batUse;
    @SerializedName("bat_status")
    @Expose
    private String batStatus;
    @SerializedName("bat_user_soc")
    @Expose
    private String batUserSoc;
    @SerializedName("load_power")
    @Expose
    private String loadPower;
    @SerializedName("load_today")
    @Expose
    private String loadToday;
    @SerializedName("grid_power")
    @Expose
    private String gridPower;
    @SerializedName("current_day_self_consumption")
    @Expose
    private String currentDaySelfConsumption;
    @SerializedName("current_pv_generation_sum")
    @Expose
    private String currentPvGenerationSum;
    @SerializedName("current_grid_feed_in_energy")
    @Expose
    private String currentGridFeedInEnergy;

    public Stats() {
        this.pcsPvTotalPower = "";
        this.batconvPower = "";
        this.batUse = "";
        this.batStatus = "";
        this.batUserSoc = "";
        this.loadPower = "";
        this.loadToday = "";
        this.gridPower = "";
        this.currentDaySelfConsumption = "";
        this.currentPvGenerationSum = "";
        this.currentGridFeedInEnergy = "";
    }

    /**
     *
     * @param batUserSoc
     * @param batconvPower
     * @param loadPower
     * @param gridPower
     * @param currentPvGenerationSum
     * @param batUse
     * @param currentDaySelfConsumption
     * @param currentGridFeedInEnergy
     * @param pcsPvTotalPower
     * @param batStatus
     * @param loadToday
     */
    public Stats(String pcsPvTotalPower, String batconvPower, String batUse, String batStatus, String batUserSoc,
            String loadPower, String loadToday, String gridPower, String currentDaySelfConsumption,
            String currentPvGenerationSum, String currentGridFeedInEnergy) {
        this.pcsPvTotalPower = pcsPvTotalPower;
        this.batconvPower = batconvPower;
        this.batUse = batUse;
        this.batStatus = batStatus;
        this.batUserSoc = batUserSoc;
        this.loadPower = loadPower;
        this.loadToday = loadToday;
        this.gridPower = gridPower;
        this.currentDaySelfConsumption = currentDaySelfConsumption;
        this.currentPvGenerationSum = currentPvGenerationSum;
        this.currentGridFeedInEnergy = currentGridFeedInEnergy;
    }

    public String getPcsPvTotalPower() {
        return pcsPvTotalPower;
    }

    public void setPcsPvTotalPower(String pcsPvTotalPower) {
        this.pcsPvTotalPower = pcsPvTotalPower;
    }

    public String getBatconvPower() {
        return batconvPower;
    }

    public void setBatconvPower(String batconvPower) {
        this.batconvPower = batconvPower;
    }

    public String getBatUse() {
        return batUse;
    }

    public void setBatUse(String batUse) {
        this.batUse = batUse;
    }

    public String getBatStatus() {
        return batStatus;
    }

    public void setBatStatus(String batStatus) {
        this.batStatus = batStatus;
    }

    public String getBatUserSoc() {
        return batUserSoc;
    }

    public void setBatUserSoc(String batUserSoc) {
        this.batUserSoc = batUserSoc;
    }

    public String getLoadPower() {
        return loadPower;
    }

    public void setLoadPower(String loadPower) {
        this.loadPower = loadPower;
    }

    public String getLoadToday() {
        return loadToday;
    }

    public void setLoadToday(String loadToday) {
        this.loadToday = loadToday;
    }

    public String getGridPower() {
        return gridPower;
    }

    public void setGridPower(String gridPower) {
        this.gridPower = gridPower;
    }

    public String getCurrentDaySelfConsumption() {
        return currentDaySelfConsumption;
    }

    public void setCurrentDaySelfConsumption(String currentDaySelfConsumption) {
        this.currentDaySelfConsumption = currentDaySelfConsumption;
    }

    public String getCurrentPvGenerationSum() {
        return currentPvGenerationSum;
    }

    public void setCurrentPvGenerationSum(String currentPvGenerationSum) {
        this.currentPvGenerationSum = currentPvGenerationSum;
    }

    public String getCurrentGridFeedInEnergy() {
        return currentGridFeedInEnergy;
    }

    public void setCurrentGridFeedInEnergy(String currentGridFeedInEnergy) {
        this.currentGridFeedInEnergy = currentGridFeedInEnergy;
    }
}
