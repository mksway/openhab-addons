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
    @SerializedName("pv3_voltage")
    @Expose
    private String pv3Voltage;
    @SerializedName("pv4_voltage")
    @Expose
    private String pv4Voltage;
    @SerializedName("pv5_voltage")
    @Expose
    private String pv5Voltage;

    @SerializedName("pv1_current")
    @Expose
    private String pv1Current;
    @SerializedName("pv2_current")
    @Expose
    private String pv2Current;
    @SerializedName("pv3_current")
    @Expose
    private String pv3Current;
    @SerializedName("pv4_current")
    @Expose
    private String pv4Current;
    @SerializedName("pv5_current")
    @Expose
    private String pv5Current;

    @SerializedName("pv1_power")
    @Expose
    private String pv1Power;
    @SerializedName("pv2_power")
    @Expose
    private String pv2Power;

    @SerializedName("pv3_power")
    @Expose
    private String pv3Power;

    @SerializedName("pv4_power")
    @Expose
    private String pv4Power;

    @SerializedName("pv5_power")
    @Expose
    private String pv5Power;

    @SerializedName("today_pv_generation_sum")
    @Expose
    private String todayPvGenerationSum;
    @SerializedName("today_month_pv_generation_sum")
    @Expose
    private String todayMonthPvGenerationSum;

    public PV() {
        this.brand = "0";
        this.capacity = "0";
        this.pv1Voltage = "0";
        this.pv2Voltage = "0";
        this.pv3Voltage = "0";
        this.pv4Voltage = "0";
        this.pv5Voltage = "0";

        this.pv1Current = "0";
        this.pv2Current = "0";
        this.pv3Current = "0";
        this.pv4Current = "0";
        this.pv5Current = "0";

        this.pv1Power = "0";
        this.pv2Power = "0";
        this.pv3Power = "0";
        this.pv4Power = "0";
        this.pv5Power = "0";

        this.todayPvGenerationSum = "";
        this.todayMonthPvGenerationSum = "";
    }

    /**
     *
     * @param brand
     * @param capacity
     * @param pv1Voltage
     * @param pv2Voltage
     * @param pv3Voltage
     * @param pv4Voltage
     * @param pv5Voltage
     * @param pv1Power
     * @param pv2Power
     * @param pv3Power
     * @param pv4Power
     * @param pv5Power
     * @param pv1Current
     * @param pv2Current
     * @param pv3Current
     * @param pv4Current
     * @param pv5Current
     * @param todayPvGenerationSum
     * @param todayMonthPvGenerationSum
     */
    public PV(String brand, String capacity, String pv1Voltage, String pv2Voltage, String pv3Voltage, String pv4Voltage,
            String pv5Voltage, String pv1Power, String pv2Power, String pv3Power, String pv4Power, String pv5Power,
            String pv1Current, String pv2Current, String pv3Current, String pv4Current, String pv5Current,
            String todayPvGenerationSum, String todayMonthPvGenerationSum) {
        this.brand = brand;
        this.capacity = capacity;
        this.pv1Voltage = pv1Voltage;
        this.pv2Voltage = pv2Voltage;
        this.pv3Voltage = pv3Voltage;
        this.pv4Voltage = pv4Voltage;
        this.pv5Voltage = pv5Voltage;
        this.pv1Current = pv1Current;
        this.pv2Current = pv2Current;
        this.pv3Current = pv3Current;
        this.pv4Current = pv4Current;
        this.pv5Current = pv5Current;

        this.pv1Power = pv1Power;
        this.pv2Power = pv2Power;
        this.pv3Power = pv3Power;
        this.pv4Power = pv4Power;
        this.pv5Power = pv5Power;
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

    public String getPv3Voltage() {
        return pv3Voltage;
    }

    public void setPv3Voltage(String pv3Voltage) {
        this.pv3Voltage = pv3Voltage;
    }

    public String getPv4Voltage() {
        return pv4Voltage;
    }

    public void setPv4Voltage(String pv4Voltage) {
        this.pv4Voltage = pv4Voltage;
    }

    public String getPv5Voltage() {
        return pv5Voltage;
    }

    public void setPv5Voltage(String pv5Voltage) {
        this.pv5Voltage = pv5Voltage;
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

    public String getPv3Power() {
        if (pv3Power.isBlank()) {
            pv3Power = "0";
        }
        return pv3Power;
    }

    public void setPv3Power(String pv3Power) {
        this.pv3Power = pv3Power;
    }

    public String getPv4Power() {
        if (pv4Power.isBlank()) {
            pv4Power = "0";
        }
        return pv4Power;
    }

    public void setPv4Power(String pv4Power) {
        this.pv4Power = pv4Power;
    }

    public String getPv5Power() {
        if (pv5Power.isBlank()) {
            pv5Power = "0";
        }
        return pv5Power;
    }

    public void setPv5Power(String pv5Power) {
        this.pv5Power = pv5Power;
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

    public String getPv3Current() {
        return pv3Current;
    }

    public void setPv3Current(String pv3Current) {
        this.pv3Current = pv3Current;
    }

    public String getPv4Current() {
        return pv4Current;
    }

    public void setPv4Current(String pv4Current) {
        this.pv4Current = pv4Current;
    }

    public String getPv5Current() {
        return pv5Current;
    }

    public void setPv5Current(String pv5Current) {
        this.pv5Current = pv5Current;
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
