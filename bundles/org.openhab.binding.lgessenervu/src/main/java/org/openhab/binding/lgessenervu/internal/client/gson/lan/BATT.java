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
public class BATT {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("soc")
    @Expose
    private String soc;
    @SerializedName("dc_power")
    @Expose
    private String dcPower;
    @SerializedName("winter_setting")
    @Expose
    private String winterSetting;
    @SerializedName("winter_status")
    @Expose
    private String winterStatus;
    @SerializedName("safty_soc")
    @Expose
    private String saftySoc;
    @SerializedName("today_batt_discharge_enery")
    @Expose
    private String todayBattDischargeEnery;
    @SerializedName("today_batt_charge_energy")
    @Expose
    private String todayBattChargeEnergy;
    @SerializedName("month_batt_charge_energy")
    @Expose
    private String monthBattChargeEnergy;
    @SerializedName("month_batt_discharge_energy")
    @Expose
    private String monthBattDischargeEnergy;

    /**
     * No args constructor for use in serialization
     *
     */
    public BATT() {
        this.status = "";
        this.soc = "";
        this.dcPower = "";
        this.winterSetting = "";
        this.winterStatus = "";
        this.saftySoc = "";
        this.todayBattDischargeEnery = "";
        this.todayBattChargeEnergy = "";
        this.monthBattChargeEnergy = "";
        this.monthBattDischargeEnergy = "";
    }

    /**
     *
     * @param monthBattChargeEnergy
     * @param winterSetting
     * @param soc
     * @param winterStatus
     * @param saftySoc
     * @param todayBattDischargeEnery
     * @param todayBattChargeEnergy
     * @param dcPower
     * @param status
     * @param monthBattDischargeEnergy
     */
    public BATT(String status, String soc, String dcPower, String winterSetting, String winterStatus, String saftySoc,
            String todayBattDischargeEnery, String todayBattChargeEnergy, String monthBattChargeEnergy,
            String monthBattDischargeEnergy) {
        super();
        this.status = status;
        this.soc = soc;
        this.dcPower = dcPower;
        this.winterSetting = winterSetting;
        this.winterStatus = winterStatus;
        this.saftySoc = saftySoc;
        this.todayBattDischargeEnery = todayBattDischargeEnery;
        this.todayBattChargeEnergy = todayBattChargeEnergy;
        this.monthBattChargeEnergy = monthBattChargeEnergy;
        this.monthBattDischargeEnergy = monthBattDischargeEnergy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSoc() {
        return soc;
    }

    public void setSoc(String soc) {
        this.soc = soc;
    }

    public String getDcPower() {
        return dcPower;
    }

    public void setDcPower(String dcPower) {
        this.dcPower = dcPower;
    }

    public String getWinterSetting() {
        return winterSetting;
    }

    public void setWinterSetting(String winterSetting) {
        this.winterSetting = winterSetting;
    }

    public String getWinterStatus() {
        return winterStatus;
    }

    public void setWinterStatus(String winterStatus) {
        this.winterStatus = winterStatus;
    }

    public String getSaftySoc() {
        return saftySoc;
    }

    public void setSaftySoc(String saftySoc) {
        this.saftySoc = saftySoc;
    }

    public String getTodayBattDischargeEnery() {
        return todayBattDischargeEnery;
    }

    public void setTodayBattDischargeEnery(String todayBattDischargeEnery) {
        this.todayBattDischargeEnery = todayBattDischargeEnery;
    }

    public String getTodayBattChargeEnergy() {
        return todayBattChargeEnergy;
    }

    public void setTodayBattChargeEnergy(String todayBattChargeEnergy) {
        this.todayBattChargeEnergy = todayBattChargeEnergy;
    }

    public String getMonthBattChargeEnergy() {
        return monthBattChargeEnergy;
    }

    public void setMonthBattChargeEnergy(String monthBattChargeEnergy) {
        this.monthBattChargeEnergy = monthBattChargeEnergy;
    }

    public String getMonthBattDischargeEnergy() {
        return monthBattDischargeEnergy;
    }

    public void setMonthBattDischargeEnergy(String monthBattDischargeEnergy) {
        this.monthBattDischargeEnergy = monthBattDischargeEnergy;
    }
}
