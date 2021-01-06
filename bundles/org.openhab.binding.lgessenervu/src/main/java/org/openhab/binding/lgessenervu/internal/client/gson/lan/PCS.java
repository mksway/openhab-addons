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
public class PCS {

    @SerializedName("today_self_consumption")
    @Expose
    private String todaySelfConsumption;
    @SerializedName("month_co2_reduction_accum")
    @Expose
    private String monthCo2ReductionAccum;
    @SerializedName("today_pv_generation_sum")
    @Expose
    private String todayPvGenerationSum;
    @SerializedName("month_pv_generation_sum")
    @Expose
    private String monthPvGenerationSum;
    @SerializedName("today_grid_feed_in_energy")
    @Expose
    private String todayGridFeedInEnergy;
    @SerializedName("month_grid_feed_in_energy")
    @Expose
    private String monthGridFeedInEnergy;
    @SerializedName("pcs_stauts")
    @Expose
    private String pcsStauts;
    @SerializedName("feed_in_limitation")
    @Expose
    private String feedInLimitation;
    @SerializedName("operation_mode")
    @Expose
    private String operationMode;

    public PCS() {
        this.todaySelfConsumption = "";
        this.monthCo2ReductionAccum = "";
        this.todayPvGenerationSum = "";
        this.monthPvGenerationSum = "";
        this.todayGridFeedInEnergy = "";
        this.monthGridFeedInEnergy = "";
        this.pcsStauts = "";
        this.feedInLimitation = "";
        this.operationMode = "";
    }

    /**
     *
     * @param monthGridFeedInEnergy
     * @param pcsStauts
     * @param todaySelfConsumption
     * @param operationMode
     * @param feedInLimitation
     * @param monthCo2ReductionAccum
     * @param monthPvGenerationSum
     * @param todayGridFeedInEnergy
     * @param todayPvGenerationSum
     */
    public PCS(String todaySelfConsumption, String monthCo2ReductionAccum, String todayPvGenerationSum,
            String monthPvGenerationSum, String todayGridFeedInEnergy, String monthGridFeedInEnergy, String pcsStauts,
            String feedInLimitation, String operationMode) {
        this.todaySelfConsumption = todaySelfConsumption;
        this.monthCo2ReductionAccum = monthCo2ReductionAccum;
        this.todayPvGenerationSum = todayPvGenerationSum;
        this.monthPvGenerationSum = monthPvGenerationSum;
        this.todayGridFeedInEnergy = todayGridFeedInEnergy;
        this.monthGridFeedInEnergy = monthGridFeedInEnergy;
        this.pcsStauts = pcsStauts;
        this.feedInLimitation = feedInLimitation;
        this.operationMode = operationMode;
    }

    public String getTodaySelfConsumption() {
        return todaySelfConsumption;
    }

    public void setTodaySelfConsumption(String todaySelfConsumption) {
        this.todaySelfConsumption = todaySelfConsumption;
    }

    public String getMonthCo2ReductionAccum() {
        return monthCo2ReductionAccum;
    }

    public void setMonthCo2ReductionAccum(String monthCo2ReductionAccum) {
        this.monthCo2ReductionAccum = monthCo2ReductionAccum;
    }

    public String getTodayPvGenerationSum() {
        return todayPvGenerationSum;
    }

    public void setTodayPvGenerationSum(String todayPvGenerationSum) {
        this.todayPvGenerationSum = todayPvGenerationSum;
    }

    public String getMonthPvGenerationSum() {
        return monthPvGenerationSum;
    }

    public void setMonthPvGenerationSum(String monthPvGenerationSum) {
        this.monthPvGenerationSum = monthPvGenerationSum;
    }

    public String getTodayGridFeedInEnergy() {
        return todayGridFeedInEnergy;
    }

    public void setTodayGridFeedInEnergy(String todayGridFeedInEnergy) {
        this.todayGridFeedInEnergy = todayGridFeedInEnergy;
    }

    public String getMonthGridFeedInEnergy() {
        return monthGridFeedInEnergy;
    }

    public void setMonthGridFeedInEnergy(String monthGridFeedInEnergy) {
        this.monthGridFeedInEnergy = monthGridFeedInEnergy;
    }

    public String getPcsStauts() {
        return pcsStauts;
    }

    public void setPcsStauts(String pcsStauts) {
        this.pcsStauts = pcsStauts;
    }

    public String getFeedInLimitation() {
        return feedInLimitation;
    }

    public void setFeedInLimitation(String feedInLimitation) {
        this.feedInLimitation = feedInLimitation;
    }

    public String getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
    }
}
