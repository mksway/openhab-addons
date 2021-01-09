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
 * The {@link PV} is an auto generated DTO
 *
 * @author Martin Klama - Initial contribution
 *
 */
@NonNullByDefault
public class PV {

    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("capacity")
    @Expose
    private String capacity;
    @SerializedName("pv1_voltage")
    @Expose
    private String pv1Voltage;
    @SerializedName("pv2_voltage")
    @Expose
    private String pv2Voltage;
    @SerializedName("pv1_power")
    @Expose
    private String pv1Power;
    @SerializedName("pv2_power")
    @Expose
    private String pv2Power;
    @SerializedName("pv1_current")
    @Expose
    private String pv1Current;
    @SerializedName("pv2_current")
    @Expose
    private String pv2Current;
    @SerializedName("today_pv_generation_sum")
    @Expose
    private String todayPvGenerationSum;
    @SerializedName("today_month_pv_generation_sum")
    @Expose
    private String todayMonthPvGenerationSum;

    public PV() {
        this.brand = "";
        this.capacity = "";
        this.pv1Voltage = "";
        this.pv2Voltage = "";
        this.pv1Power = "";
        this.pv2Power = "";
        this.pv1Current = "";
        this.pv2Current = "";
        this.todayPvGenerationSum = "";
        this.todayMonthPvGenerationSum = "";
    }

    /**
     *
     * @param pv1Voltage
     * @param pv2Voltage
     * @param pv1Current
     * @param pv1Power
     * @param pv2Current
     * @param pv2Power
     * @param brand
     * @param capacity
     * @param todayPvGenerationSum
     * @param todayMonthPvGenerationSum
     */
    public PV(String brand, String capacity, String pv1Voltage, String pv2Voltage, String pv1Power, String pv2Power,
            String pv1Current, String pv2Current, String todayPvGenerationSum, String todayMonthPvGenerationSum) {
        this.brand = brand;
        this.capacity = capacity;
        this.pv1Voltage = pv1Voltage;
        this.pv2Voltage = pv2Voltage;
        this.pv1Power = pv1Power;
        this.pv2Power = pv2Power;
        this.pv1Current = pv1Current;
        this.pv2Current = pv2Current;
        this.todayPvGenerationSum = todayPvGenerationSum;
        this.todayMonthPvGenerationSum = todayMonthPvGenerationSum;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getPv1Voltage() {
        return pv1Voltage;
    }

    public void setPv1Voltage(String pv1Voltage) {
        this.pv1Voltage = pv1Voltage;
    }

    public String getPv2Voltage() {
        return pv2Voltage;
    }

    public void setPv2Voltage(String pv2Voltage) {
        this.pv2Voltage = pv2Voltage;
    }

    public String getPv1Power() {
        return pv1Power;
    }

    public void setPv1Power(String pv1Power) {
        this.pv1Power = pv1Power;
    }

    public String getPv2Power() {
        return pv2Power;
    }

    public void setPv2Power(String pv2Power) {
        this.pv2Power = pv2Power;
    }

    public String getPv1Current() {
        return pv1Current;
    }

    public void setPv1Current(String pv1Current) {
        this.pv1Current = pv1Current;
    }

    public String getPv2Current() {
        return pv2Current;
    }

    public void setPv2Current(String pv2Current) {
        this.pv2Current = pv2Current;
    }

    public String getTodayPvGenerationSum() {
        return todayPvGenerationSum;
    }

    public void setTodayPvGenerationSum(String todayPvGenerationSum) {
        this.todayPvGenerationSum = todayPvGenerationSum;
    }

    public String getTodayMonthPvGenerationSum() {
        return todayMonthPvGenerationSum;
    }

    public void setTodayMonthPvGenerationSum(String todayMonthPvGenerationSum) {
        this.todayMonthPvGenerationSum = todayMonthPvGenerationSum;
    }
}
