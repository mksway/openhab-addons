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
public class GRID {

    @SerializedName("active_power")
    @Expose
    private String activePower;
    @SerializedName("a_phase")
    @Expose
    private String aPhase;
    @SerializedName("freq")
    @Expose
    private String freq;
    @SerializedName("today_grid_feed_in_energy")
    @Expose
    private String todayGridFeedInEnergy;
    @SerializedName("today_grid_power_purchase_energy")
    @Expose
    private String todayGridPowerPurchaseEnergy;
    @SerializedName("month_grid_feed_in_energy")
    @Expose
    private String monthGridFeedInEnergy;
    @SerializedName("month_grid_power_purchase_energy")
    @Expose
    private String monthGridPowerPurchaseEnergy;

    public GRID() {
        this.activePower = "";
        this.aPhase = "";
        this.freq = "";
        this.todayGridFeedInEnergy = "";
        this.todayGridPowerPurchaseEnergy = "";
        this.monthGridFeedInEnergy = "";
        this.monthGridPowerPurchaseEnergy = "";
    }

    /**
     *
     * @param monthGridFeedInEnergy
     * @param aPhase
     * @param todayGridPowerPurchaseEnergy
     * @param freq
     * @param monthGridPowerPurchaseEnergy
     * @param todayGridFeedInEnergy
     * @param activePower
     */
    public GRID(String activePower, String aPhase, String freq, String todayGridFeedInEnergy,
            String todayGridPowerPurchaseEnergy, String monthGridFeedInEnergy, String monthGridPowerPurchaseEnergy) {
        super();
        this.activePower = activePower;
        this.aPhase = aPhase;
        this.freq = freq;
        this.todayGridFeedInEnergy = todayGridFeedInEnergy;
        this.todayGridPowerPurchaseEnergy = todayGridPowerPurchaseEnergy;
        this.monthGridFeedInEnergy = monthGridFeedInEnergy;
        this.monthGridPowerPurchaseEnergy = monthGridPowerPurchaseEnergy;
    }

    public String getActivePower() {
        return activePower;
    }

    public void setActivePower(String activePower) {
        this.activePower = activePower;
    }

    public String getAPhase() {
        return aPhase;
    }

    public void setAPhase(String aPhase) {
        this.aPhase = aPhase;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getTodayGridFeedInEnergy() {
        return todayGridFeedInEnergy;
    }

    public void setTodayGridFeedInEnergy(String todayGridFeedInEnergy) {
        this.todayGridFeedInEnergy = todayGridFeedInEnergy;
    }

    public String getTodayGridPowerPurchaseEnergy() {
        return todayGridPowerPurchaseEnergy;
    }

    public void setTodayGridPowerPurchaseEnergy(String todayGridPowerPurchaseEnergy) {
        this.todayGridPowerPurchaseEnergy = todayGridPowerPurchaseEnergy;
    }

    public String getMonthGridFeedInEnergy() {
        return monthGridFeedInEnergy;
    }

    public void setMonthGridFeedInEnergy(String monthGridFeedInEnergy) {
        this.monthGridFeedInEnergy = monthGridFeedInEnergy;
    }

    public String getMonthGridPowerPurchaseEnergy() {
        return monthGridPowerPurchaseEnergy;
    }

    public void setMonthGridPowerPurchaseEnergy(String monthGridPowerPurchaseEnergy) {
        this.monthGridPowerPurchaseEnergy = monthGridPowerPurchaseEnergy;
    }
}
