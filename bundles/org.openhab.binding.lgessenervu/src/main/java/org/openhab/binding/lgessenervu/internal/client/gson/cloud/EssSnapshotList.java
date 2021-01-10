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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The {@link EssSnapshotList} is an auto generated DTO
 *
 * @author Martin Klama - initial contribution
 */
public class EssSnapshotList {

    @SerializedName("systemId")
    @Expose
    private Integer systemId;
    @SerializedName("essId")
    @Expose
    private Integer essId;
    @SerializedName("targetDate")
    @Expose
    private String targetDate;
    @SerializedName("timeMin")
    @Expose
    private String timeMin;
    @SerializedName("systemOperation")
    @Expose
    private Integer systemOperation;
    @SerializedName("pvPower")
    @Expose
    private Double pvPower;
    @SerializedName("loadPower")
    @Expose
    private Double loadPower;
    @SerializedName("battSoc")
    @Expose
    private Double battSoc;
    @SerializedName("battStatus")
    @Expose
    private Integer battStatus;
    @SerializedName("battPower")
    @Expose
    private Double battPower;
    @SerializedName("gridPower")
    @Expose
    private Double gridPower;
    @SerializedName("isDirectConsuming")
    @Expose
    private Integer isDirectConsuming;
    @SerializedName("isBatteryCharging")
    @Expose
    private Integer isBatteryCharging;
    @SerializedName("isBatteryDischarging")
    @Expose
    private Integer isBatteryDischarging;
    @SerializedName("isGridSelling")
    @Expose
    private Integer isGridSelling;
    @SerializedName("isGridBuying")
    @Expose
    private Integer isGridBuying;
    @SerializedName("isChargingFromGrid")
    @Expose
    private Integer isChargingFromGrid;
    @SerializedName("systemOperationStr")
    @Expose
    private String systemOperationStr;
    @SerializedName("battStatusStr")
    @Expose
    private String battStatusStr;
    @SerializedName("isEvChargerEnabled")
    @Expose
    private Integer isEvChargerEnabled;
    @SerializedName("isEvChargerConnected")
    @Expose
    private Integer isEvChargerConnected;
    @SerializedName("evChargerPower")
    @Expose
    private Double evChargerPower;
    @SerializedName("isAwhpEnabled")
    @Expose
    private Integer isAwhpEnabled;
    @SerializedName("isAwhpConnected")
    @Expose
    private Integer isAwhpConnected;

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public Integer getEssId() {
        return essId;
    }

    public void setEssId(Integer essId) {
        this.essId = essId;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public String getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(String timeMin) {
        this.timeMin = timeMin;
    }

    public Integer getSystemOperation() {
        return systemOperation;
    }

    public void setSystemOperation(Integer systemOperation) {
        this.systemOperation = systemOperation;
    }

    public Double getPvPower() {
        return pvPower;
    }

    public void setPvPower(Double pvPower) {
        this.pvPower = pvPower;
    }

    public Double getLoadPower() {
        return loadPower;
    }

    public void setLoadPower(Double loadPower) {
        this.loadPower = loadPower;
    }

    public Double getBattSoc() {
        return battSoc;
    }

    public void setBattSoc(Double battSoc) {
        this.battSoc = battSoc;
    }

    public Integer getBattStatus() {
        return battStatus;
    }

    public void setBattStatus(Integer battStatus) {
        this.battStatus = battStatus;
    }

    public Double getBattPower() {
        return battPower;
    }

    public void setBattPower(Double battPower) {
        this.battPower = battPower;
    }

    public Double getGridPower() {
        return gridPower;
    }

    public void setGridPower(Double gridPower) {
        this.gridPower = gridPower;
    }

    public Integer getIsDirectConsuming() {
        return isDirectConsuming;
    }

    public void setIsDirectConsuming(Integer isDirectConsuming) {
        this.isDirectConsuming = isDirectConsuming;
    }

    public Integer getIsBatteryCharging() {
        return isBatteryCharging;
    }

    public void setIsBatteryCharging(Integer isBatteryCharging) {
        this.isBatteryCharging = isBatteryCharging;
    }

    public Integer getIsBatteryDischarging() {
        return isBatteryDischarging;
    }

    public void setIsBatteryDischarging(Integer isBatteryDischarging) {
        this.isBatteryDischarging = isBatteryDischarging;
    }

    public Integer getIsGridSelling() {
        return isGridSelling;
    }

    public void setIsGridSelling(Integer isGridSelling) {
        this.isGridSelling = isGridSelling;
    }

    public Integer getIsGridBuying() {
        return isGridBuying;
    }

    public void setIsGridBuying(Integer isGridBuying) {
        this.isGridBuying = isGridBuying;
    }

    public Integer getIsChargingFromGrid() {
        return isChargingFromGrid;
    }

    public void setIsChargingFromGrid(Integer isChargingFromGrid) {
        this.isChargingFromGrid = isChargingFromGrid;
    }

    public String getSystemOperationStr() {
        return systemOperationStr;
    }

    public void setSystemOperationStr(String systemOperationStr) {
        this.systemOperationStr = systemOperationStr;
    }

    public String getBattStatusStr() {
        return battStatusStr;
    }

    public void setBattStatusStr(String battStatusStr) {
        this.battStatusStr = battStatusStr;
    }

    public Integer getIsEvChargerEnabled() {
        return isEvChargerEnabled;
    }

    public void setIsEvChargerEnabled(Integer isEvChargerEnabled) {
        this.isEvChargerEnabled = isEvChargerEnabled;
    }

    public Integer getIsEvChargerConnected() {
        return isEvChargerConnected;
    }

    public void setIsEvChargerConnected(Integer isEvChargerConnected) {
        this.isEvChargerConnected = isEvChargerConnected;
    }

    public Double getEvChargerPower() {
        return evChargerPower;
    }

    public void setEvChargerPower(Double evChargerPower) {
        this.evChargerPower = evChargerPower;
    }

    public Integer getIsAwhpEnabled() {
        return isAwhpEnabled;
    }

    public void setIsAwhpEnabled(Integer isAwhpEnabled) {
        this.isAwhpEnabled = isAwhpEnabled;
    }

    public Integer getIsAwhpConnected() {
        return isAwhpConnected;
    }

    public void setIsAwhpConnected(Integer isAwhpConnected) {
        this.isAwhpConnected = isAwhpConnected;
    }
}
