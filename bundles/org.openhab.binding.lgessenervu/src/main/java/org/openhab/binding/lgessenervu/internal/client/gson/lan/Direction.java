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
public class Direction {

    @SerializedName("is_direct_consuming_")
    @Expose
    private String isDirectConsuming;
    @SerializedName("is_battery_charging_")
    @Expose
    private String isBatteryCharging;
    @SerializedName("is_battery_discharging_")
    @Expose
    private String isBatteryDischarging;
    @SerializedName("is_grid_selling_")
    @Expose
    private String isGridSelling;
    @SerializedName("is_grid_buying_")
    @Expose
    private String isGridBuying;
    @SerializedName("is_charging_from_grid_")
    @Expose
    private String isChargingFromGrid;

    public Direction() {
        this.isDirectConsuming = "";
        this.isBatteryCharging = "";
        this.isBatteryDischarging = "";
        this.isGridSelling = "";
        this.isGridBuying = "";
        this.isChargingFromGrid = "";
    }

    /**
     *
     * @param isDirectConsuming
     * @param isBatteryCharging
     * @param isChargingFromGrid
     * @param isBatteryDischarging
     * @param isGridSelling
     * @param isGridBuying
     */
    public Direction(String isDirectConsuming, String isBatteryCharging, String isBatteryDischarging,
            String isGridSelling, String isGridBuying, String isChargingFromGrid) {
        this.isDirectConsuming = isDirectConsuming;
        this.isBatteryCharging = isBatteryCharging;
        this.isBatteryDischarging = isBatteryDischarging;
        this.isGridSelling = isGridSelling;
        this.isGridBuying = isGridBuying;
        this.isChargingFromGrid = isChargingFromGrid;
    }

    public String getIsDirectConsuming() {
        return isDirectConsuming;
    }

    public void setIsDirectConsuming(String isDirectConsuming) {
        this.isDirectConsuming = isDirectConsuming;
    }

    public String getIsBatteryCharging() {
        return isBatteryCharging;
    }

    public void setIsBatteryCharging(String isBatteryCharging) {
        this.isBatteryCharging = isBatteryCharging;
    }

    public String getIsBatteryDischarging() {
        return isBatteryDischarging;
    }

    public void setIsBatteryDischarging(String isBatteryDischarging) {
        this.isBatteryDischarging = isBatteryDischarging;
    }

    public String getIsGridSelling() {
        return isGridSelling;
    }

    public void setIsGridSelling(String isGridSelling) {
        this.isGridSelling = isGridSelling;
    }

    public String getIsGridBuying() {
        return isGridBuying;
    }

    public void setIsGridBuying(String isGridBuying) {
        this.isGridBuying = isGridBuying;
    }

    public String getIsChargingFromGrid() {
        return isChargingFromGrid;
    }

    public void setIsChargingFromGrid(String isChargingFromGrid) {
        this.isChargingFromGrid = isChargingFromGrid;
    }
}
