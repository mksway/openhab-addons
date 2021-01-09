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
 * The {@link LOAD} is an auto generated DTO
 *
 * @author Martin Klama - Initial contribution
 *
 */
@NonNullByDefault
public class LOAD {

    @SerializedName("load_power")
    @Expose
    private String loadPower;
    @SerializedName("today_load_consumption_sum")
    @Expose
    private String todayLoadConsumptionSum;
    @SerializedName("today_pv_direct_consumption_enegy")
    @Expose
    private String todayPvDirectConsumptionEnegy;
    @SerializedName("today_batt_discharge_enery")
    @Expose
    private String todayBattDischargeEnery;
    @SerializedName("today_grid_power_purchase_energy")
    @Expose
    private String todayGridPowerPurchaseEnergy;
    @SerializedName("month_load_consumption_sum")
    @Expose
    private String monthLoadConsumptionSum;
    @SerializedName("month_pv_direct_consumption_energy")
    @Expose
    private String monthPvDirectConsumptionEnergy;
    @SerializedName("month_batt_discharge_energy")
    @Expose
    private String monthBattDischargeEnergy;
    @SerializedName("month_grid_power_purchase_energy")
    @Expose
    private String monthGridPowerPurchaseEnergy;

    public LOAD() {
        this.loadPower = "";
        this.todayLoadConsumptionSum = "";
        this.todayPvDirectConsumptionEnegy = "";
        this.todayBattDischargeEnery = "";
        this.todayGridPowerPurchaseEnergy = "";
        this.monthLoadConsumptionSum = "";
        this.monthPvDirectConsumptionEnergy = "";
        this.monthBattDischargeEnergy = "";
        this.monthGridPowerPurchaseEnergy = "";
    }

    /**
     *
     * @param loadPower
     * @param todayLoadConsumptionSum
     * @param monthLoadConsumptionSum
     * @param todayPvDirectConsumptionEnegy
     * @param monthPvDirectConsumptionEnergy
     * @param todayGridPowerPurchaseEnergy
     * @param todayBattDischargeEnery
     * @param monthGridPowerPurchaseEnergy
     * @param monthBattDischargeEnergy
     */
    public LOAD(String loadPower, String todayLoadConsumptionSum, String todayPvDirectConsumptionEnegy,
            String todayBattDischargeEnery, String todayGridPowerPurchaseEnergy, String monthLoadConsumptionSum,
            String monthPvDirectConsumptionEnergy, String monthBattDischargeEnergy,
            String monthGridPowerPurchaseEnergy) {
        super();
        this.loadPower = loadPower;
        this.todayLoadConsumptionSum = todayLoadConsumptionSum;
        this.todayPvDirectConsumptionEnegy = todayPvDirectConsumptionEnegy;
        this.todayBattDischargeEnery = todayBattDischargeEnery;
        this.todayGridPowerPurchaseEnergy = todayGridPowerPurchaseEnergy;
        this.monthLoadConsumptionSum = monthLoadConsumptionSum;
        this.monthPvDirectConsumptionEnergy = monthPvDirectConsumptionEnergy;
        this.monthBattDischargeEnergy = monthBattDischargeEnergy;
        this.monthGridPowerPurchaseEnergy = monthGridPowerPurchaseEnergy;
    }

    public String getLoadPower() {
        return loadPower;
    }

    public void setLoadPower(String loadPower) {
        this.loadPower = loadPower;
    }

    public String getTodayLoadConsumptionSum() {
        return todayLoadConsumptionSum;
    }

    public void setTodayLoadConsumptionSum(String todayLoadConsumptionSum) {
        this.todayLoadConsumptionSum = todayLoadConsumptionSum;
    }

    public String getTodayPvDirectConsumptionEnegy() {
        return todayPvDirectConsumptionEnegy;
    }

    public void setTodayPvDirectConsumptionEnegy(String todayPvDirectConsumptionEnegy) {
        this.todayPvDirectConsumptionEnegy = todayPvDirectConsumptionEnegy;
    }

    public String getTodayBattDischargeEnery() {
        return todayBattDischargeEnery;
    }

    public void setTodayBattDischargeEnery(String todayBattDischargeEnery) {
        this.todayBattDischargeEnery = todayBattDischargeEnery;
    }

    public String getTodayGridPowerPurchaseEnergy() {
        return todayGridPowerPurchaseEnergy;
    }

    public void setTodayGridPowerPurchaseEnergy(String todayGridPowerPurchaseEnergy) {
        this.todayGridPowerPurchaseEnergy = todayGridPowerPurchaseEnergy;
    }

    public String getMonthLoadConsumptionSum() {
        return monthLoadConsumptionSum;
    }

    public void setMonthLoadConsumptionSum(String monthLoadConsumptionSum) {
        this.monthLoadConsumptionSum = monthLoadConsumptionSum;
    }

    public String getMonthPvDirectConsumptionEnergy() {
        return monthPvDirectConsumptionEnergy;
    }

    public void setMonthPvDirectConsumptionEnergy(String monthPvDirectConsumptionEnergy) {
        this.monthPvDirectConsumptionEnergy = monthPvDirectConsumptionEnergy;
    }

    public String getMonthBattDischargeEnergy() {
        return monthBattDischargeEnergy;
    }

    public void setMonthBattDischargeEnergy(String monthBattDischargeEnergy) {
        this.monthBattDischargeEnergy = monthBattDischargeEnergy;
    }

    public String getMonthGridPowerPurchaseEnergy() {
        return monthGridPowerPurchaseEnergy;
    }

    public void setMonthGridPowerPurchaseEnergy(String monthGridPowerPurchaseEnergy) {
        this.monthGridPowerPurchaseEnergy = monthGridPowerPurchaseEnergy;
    }
}
