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
 * The {@link DayResult} is an auto generated DTO
 *
 * @author Martin Klama - initial contribution
 */

public class DayResult {

    @SerializedName("systemId")
    @Expose
    private Integer systemId;
    @SerializedName("essId")
    @Expose
    private Integer essId;
    @SerializedName("essSerialNo")
    @Expose
    private Object essSerialNo;
    @SerializedName("targetDate")
    @Expose
    private String targetDate;
    @SerializedName("yyyymmdd")
    @Expose
    private Object yyyymmdd;
    @SerializedName("hhmm")
    @Expose
    private Object hhmm;
    @SerializedName("string1E")
    @Expose
    private Double string1E;
    @SerializedName("string1P")
    @Expose
    private Double string1P;
    @SerializedName("string2E")
    @Expose
    private Double string2E;
    @SerializedName("string2P")
    @Expose
    private Double string2P;
    @SerializedName("string3E")
    @Expose
    private Double string3E;
    @SerializedName("string3P")
    @Expose
    private Double string3P;
    @SerializedName("string4E")
    @Expose
    private Double string4E;
    @SerializedName("string4P")
    @Expose
    private Double string4P;
    @SerializedName("string5E")
    @Expose
    private Double string5E;
    @SerializedName("string5P")
    @Expose
    private Double string5P;
    @SerializedName("directConsumpE")
    @Expose
    private Double directConsumpE;
    @SerializedName("directConsumpP")
    @Expose
    private Double directConsumpP;
    @SerializedName("battSoc")
    @Expose
    private Double battSoc;
    @SerializedName("batt1Chg")
    @Expose
    private Double batt1Chg;
    @SerializedName("batt1Dis")
    @Expose
    private Double batt1Dis;
    @SerializedName("batt1Soc")
    @Expose
    private Double batt1Soc;
    @SerializedName("batt1Tmp")
    @Expose
    private Double batt1Tmp;
    @SerializedName("batt1P")
    @Expose
    private Double batt1P;
    @SerializedName("batt1ChgP")
    @Expose
    private Double batt1ChgP;
    @SerializedName("batt1DisP")
    @Expose
    private Double batt1DisP;
    @SerializedName("batt2Chg")
    @Expose
    private Double batt2Chg;
    @SerializedName("batt2Dis")
    @Expose
    private Double batt2Dis;
    @SerializedName("batt2Soc")
    @Expose
    private Double batt2Soc;
    @SerializedName("batt2Tmp")
    @Expose
    private Double batt2Tmp;
    @SerializedName("batt2P")
    @Expose
    private Double batt2P;
    @SerializedName("batt2ChgP")
    @Expose
    private Double batt2ChgP;
    @SerializedName("batt2DisP")
    @Expose
    private Double batt2DisP;
    @SerializedName("batt3Chg")
    @Expose
    private Double batt3Chg;
    @SerializedName("batt3Dis")
    @Expose
    private Double batt3Dis;
    @SerializedName("batt3Soc")
    @Expose
    private Double batt3Soc;
    @SerializedName("batt3Tmp")
    @Expose
    private Double batt3Tmp;
    @SerializedName("batt3P")
    @Expose
    private Double batt3P;
    @SerializedName("batt3ChgP")
    @Expose
    private Double batt3ChgP;
    @SerializedName("batt3DisP")
    @Expose
    private Double batt3DisP;
    @SerializedName("batt4Chg")
    @Expose
    private Double batt4Chg;
    @SerializedName("batt4Dis")
    @Expose
    private Double batt4Dis;
    @SerializedName("batt4Soc")
    @Expose
    private Double batt4Soc;
    @SerializedName("batt4Tmp")
    @Expose
    private Double batt4Tmp;
    @SerializedName("batt4P")
    @Expose
    private Double batt4P;
    @SerializedName("batt4ChgP")
    @Expose
    private Double batt4ChgP;
    @SerializedName("batt4DisP")
    @Expose
    private Double batt4DisP;
    @SerializedName("batt5Chg")
    @Expose
    private Double batt5Chg;
    @SerializedName("batt5Dis")
    @Expose
    private Double batt5Dis;
    @SerializedName("batt5Soc")
    @Expose
    private Double batt5Soc;
    @SerializedName("batt5Tmp")
    @Expose
    private Double batt5Tmp;
    @SerializedName("batt5P")
    @Expose
    private Double batt5P;
    @SerializedName("batt5ChgP")
    @Expose
    private Double batt5ChgP;
    @SerializedName("batt5DisP")
    @Expose
    private Double batt5DisP;
    @SerializedName("consumptionE")
    @Expose
    private Double consumptionE;
    @SerializedName("consumptionP")
    @Expose
    private Double consumptionP;
    @SerializedName("gridPSell")
    @Expose
    private Double gridPSell;
    @SerializedName("gridPBuy")
    @Expose
    private Double gridPBuy;
    @SerializedName("gridESell")
    @Expose
    private Double gridESell;
    @SerializedName("gridEBuy")
    @Expose
    private Double gridEBuy;
    @SerializedName("battChg")
    @Expose
    private Double battChg;
    @SerializedName("battDis")
    @Expose
    private Double battDis;
    @SerializedName("totalBattChg")
    @Expose
    private Double totalBattChg;
    @SerializedName("totalBattDis")
    @Expose
    private Double totalBattDis;
    @SerializedName("totalGridESell")
    @Expose
    private Double totalGridESell;
    @SerializedName("totalGridEBuy")
    @Expose
    private Double totalGridEBuy;
    @SerializedName("totalDirectConsumpE")
    @Expose
    private Double totalDirectConsumpE;
    @SerializedName("totalConsumpE")
    @Expose
    private Double totalConsumpE;
    @SerializedName("totalPvE")
    @Expose
    private Double totalPvE;
    @SerializedName("limitGridPSell")
    @Expose
    private Double limitGridPSell;
    @SerializedName("limitGridPSellPercent")
    @Expose
    private Double limitGridPSellPercent;
    @SerializedName("pvP")
    @Expose
    private Double pvP;
    @SerializedName("battP")
    @Expose
    private Double battP;
    @SerializedName("battChgP")
    @Expose
    private Double battChgP;
    @SerializedName("battDisP")
    @Expose
    private Double battDisP;
    @SerializedName("pvDcOut")
    @Expose
    private Double pvDcOut;
    @SerializedName("nationCode")
    @Expose
    private Object nationCode;
    @SerializedName("reductionFactor")
    @Expose
    private Double reductionFactor;
    @SerializedName("utc")
    @Expose
    private Object utc;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("winterModeSocLimit")
    @Expose
    private Integer winterModeSocLimit;
    @SerializedName("role")
    @Expose
    private Object role;
    @SerializedName("companyAuth")
    @Expose
    private Integer companyAuth;
    @SerializedName("installerId")
    @Expose
    private Integer installerId;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("currentPvP")
    @Expose
    private Double currentPvP;
    @SerializedName("currentBattP")
    @Expose
    private Double currentBattP;
    @SerializedName("currentLoadP")
    @Expose
    private Double currentLoadP;
    @SerializedName("currentGridP")
    @Expose
    private Double currentGridP;
    @SerializedName("currentDirectConsumption")
    @Expose
    private Integer currentDirectConsumption;
    @SerializedName("currentBattChg")
    @Expose
    private Integer currentBattChg;
    @SerializedName("currentBattDis")
    @Expose
    private Integer currentBattDis;
    @SerializedName("currentGridSell")
    @Expose
    private Integer currentGridSell;
    @SerializedName("currentGridBuy")
    @Expose
    private Integer currentGridBuy;
    @SerializedName("currentChgFromGrid")
    @Expose
    private Integer currentChgFromGrid;
    @SerializedName("totalCnt")
    @Expose
    private Integer totalCnt;

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

    public Object getEssSerialNo() {
        return essSerialNo;
    }

    public void setEssSerialNo(Object essSerialNo) {
        this.essSerialNo = essSerialNo;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public Object getYyyymmdd() {
        return yyyymmdd;
    }

    public void setYyyymmdd(Object yyyymmdd) {
        this.yyyymmdd = yyyymmdd;
    }

    public Object getHhmm() {
        return hhmm;
    }

    public void setHhmm(Object hhmm) {
        this.hhmm = hhmm;
    }

    public Double getString1E() {
        return string1E;
    }

    public void setString1E(Double string1E) {
        this.string1E = string1E;
    }

    public Double getString1P() {
        return string1P;
    }

    public void setString1P(Double string1P) {
        this.string1P = string1P;
    }

    public Double getString2E() {
        return string2E;
    }

    public void setString2E(Double string2E) {
        this.string2E = string2E;
    }

    public Double getString2P() {
        return string2P;
    }

    public void setString2P(Double string2P) {
        this.string2P = string2P;
    }

    public Double getString3E() {
        return string3E;
    }

    public void setString3E(Double string3E) {
        this.string3E = string3E;
    }

    public Double getString3P() {
        return string3P;
    }

    public void setString3P(Double string3P) {
        this.string3P = string3P;
    }

    public Double getString4E() {
        return string4E;
    }

    public void setString4E(Double string4E) {
        this.string4E = string4E;
    }

    public Double getString4P() {
        return string4P;
    }

    public void setString4P(Double string4P) {
        this.string4P = string4P;
    }

    public Double getString5E() {
        return string5E;
    }

    public void setString5E(Double string5E) {
        this.string5E = string5E;
    }

    public Double getString5P() {
        return string5P;
    }

    public void setString5P(Double string5P) {
        this.string5P = string5P;
    }

    public Double getDirectConsumpE() {
        return directConsumpE;
    }

    public void setDirectConsumpE(Double directConsumpE) {
        this.directConsumpE = directConsumpE;
    }

    public Double getDirectConsumpP() {
        return directConsumpP;
    }

    public void setDirectConsumpP(Double directConsumpP) {
        this.directConsumpP = directConsumpP;
    }

    public Double getBattSoc() {
        return battSoc;
    }

    public void setBattSoc(Double battSoc) {
        this.battSoc = battSoc;
    }

    public Double getBatt1Chg() {
        return batt1Chg;
    }

    public void setBatt1Chg(Double batt1Chg) {
        this.batt1Chg = batt1Chg;
    }

    public Double getBatt1Dis() {
        return batt1Dis;
    }

    public void setBatt1Dis(Double batt1Dis) {
        this.batt1Dis = batt1Dis;
    }

    public Double getBatt1Soc() {
        return batt1Soc;
    }

    public void setBatt1Soc(Double batt1Soc) {
        this.batt1Soc = batt1Soc;
    }

    public Double getBatt1Tmp() {
        return batt1Tmp;
    }

    public void setBatt1Tmp(Double batt1Tmp) {
        this.batt1Tmp = batt1Tmp;
    }

    public Double getBatt1P() {
        return batt1P;
    }

    public void setBatt1P(Double batt1P) {
        this.batt1P = batt1P;
    }

    public Double getBatt1ChgP() {
        return batt1ChgP;
    }

    public void setBatt1ChgP(Double batt1ChgP) {
        this.batt1ChgP = batt1ChgP;
    }

    public Double getBatt1DisP() {
        return batt1DisP;
    }

    public void setBatt1DisP(Double batt1DisP) {
        this.batt1DisP = batt1DisP;
    }

    public Double getBatt2Chg() {
        return batt2Chg;
    }

    public void setBatt2Chg(Double batt2Chg) {
        this.batt2Chg = batt2Chg;
    }

    public Double getBatt2Dis() {
        return batt2Dis;
    }

    public void setBatt2Dis(Double batt2Dis) {
        this.batt2Dis = batt2Dis;
    }

    public Double getBatt2Soc() {
        return batt2Soc;
    }

    public void setBatt2Soc(Double batt2Soc) {
        this.batt2Soc = batt2Soc;
    }

    public Double getBatt2Tmp() {
        return batt2Tmp;
    }

    public void setBatt2Tmp(Double batt2Tmp) {
        this.batt2Tmp = batt2Tmp;
    }

    public Double getBatt2P() {
        return batt2P;
    }

    public void setBatt2P(Double batt2P) {
        this.batt2P = batt2P;
    }

    public Double getBatt2ChgP() {
        return batt2ChgP;
    }

    public void setBatt2ChgP(Double batt2ChgP) {
        this.batt2ChgP = batt2ChgP;
    }

    public Double getBatt2DisP() {
        return batt2DisP;
    }

    public void setBatt2DisP(Double batt2DisP) {
        this.batt2DisP = batt2DisP;
    }

    public Double getBatt3Chg() {
        return batt3Chg;
    }

    public void setBatt3Chg(Double batt3Chg) {
        this.batt3Chg = batt3Chg;
    }

    public Double getBatt3Dis() {
        return batt3Dis;
    }

    public void setBatt3Dis(Double batt3Dis) {
        this.batt3Dis = batt3Dis;
    }

    public Double getBatt3Soc() {
        return batt3Soc;
    }

    public void setBatt3Soc(Double batt3Soc) {
        this.batt3Soc = batt3Soc;
    }

    public Double getBatt3Tmp() {
        return batt3Tmp;
    }

    public void setBatt3Tmp(Double batt3Tmp) {
        this.batt3Tmp = batt3Tmp;
    }

    public Double getBatt3P() {
        return batt3P;
    }

    public void setBatt3P(Double batt3P) {
        this.batt3P = batt3P;
    }

    public Double getBatt3ChgP() {
        return batt3ChgP;
    }

    public void setBatt3ChgP(Double batt3ChgP) {
        this.batt3ChgP = batt3ChgP;
    }

    public Double getBatt3DisP() {
        return batt3DisP;
    }

    public void setBatt3DisP(Double batt3DisP) {
        this.batt3DisP = batt3DisP;
    }

    public Double getBatt4Chg() {
        return batt4Chg;
    }

    public void setBatt4Chg(Double batt4Chg) {
        this.batt4Chg = batt4Chg;
    }

    public Double getBatt4Dis() {
        return batt4Dis;
    }

    public void setBatt4Dis(Double batt4Dis) {
        this.batt4Dis = batt4Dis;
    }

    public Double getBatt4Soc() {
        return batt4Soc;
    }

    public void setBatt4Soc(Double batt4Soc) {
        this.batt4Soc = batt4Soc;
    }

    public Double getBatt4Tmp() {
        return batt4Tmp;
    }

    public void setBatt4Tmp(Double batt4Tmp) {
        this.batt4Tmp = batt4Tmp;
    }

    public Double getBatt4P() {
        return batt4P;
    }

    public void setBatt4P(Double batt4P) {
        this.batt4P = batt4P;
    }

    public Double getBatt4ChgP() {
        return batt4ChgP;
    }

    public void setBatt4ChgP(Double batt4ChgP) {
        this.batt4ChgP = batt4ChgP;
    }

    public Double getBatt4DisP() {
        return batt4DisP;
    }

    public void setBatt4DisP(Double batt4DisP) {
        this.batt4DisP = batt4DisP;
    }

    public Double getBatt5Chg() {
        return batt5Chg;
    }

    public void setBatt5Chg(Double batt5Chg) {
        this.batt5Chg = batt5Chg;
    }

    public Double getBatt5Dis() {
        return batt5Dis;
    }

    public void setBatt5Dis(Double batt5Dis) {
        this.batt5Dis = batt5Dis;
    }

    public Double getBatt5Soc() {
        return batt5Soc;
    }

    public void setBatt5Soc(Double batt5Soc) {
        this.batt5Soc = batt5Soc;
    }

    public Double getBatt5Tmp() {
        return batt5Tmp;
    }

    public void setBatt5Tmp(Double batt5Tmp) {
        this.batt5Tmp = batt5Tmp;
    }

    public Double getBatt5P() {
        return batt5P;
    }

    public void setBatt5P(Double batt5P) {
        this.batt5P = batt5P;
    }

    public Double getBatt5ChgP() {
        return batt5ChgP;
    }

    public void setBatt5ChgP(Double batt5ChgP) {
        this.batt5ChgP = batt5ChgP;
    }

    public Double getBatt5DisP() {
        return batt5DisP;
    }

    public void setBatt5DisP(Double batt5DisP) {
        this.batt5DisP = batt5DisP;
    }

    public Double getConsumptionE() {
        return consumptionE;
    }

    public void setConsumptionE(Double consumptionE) {
        this.consumptionE = consumptionE;
    }

    public Double getConsumptionP() {
        return consumptionP;
    }

    public void setConsumptionP(Double consumptionP) {
        this.consumptionP = consumptionP;
    }

    public Double getGridPSell() {
        return gridPSell;
    }

    public void setGridPSell(Double gridPSell) {
        this.gridPSell = gridPSell;
    }

    public Double getGridPBuy() {
        return gridPBuy;
    }

    public void setGridPBuy(Double gridPBuy) {
        this.gridPBuy = gridPBuy;
    }

    public Double getGridESell() {
        return gridESell;
    }

    public void setGridESell(Double gridESell) {
        this.gridESell = gridESell;
    }

    public Double getGridEBuy() {
        return gridEBuy;
    }

    public void setGridEBuy(Double gridEBuy) {
        this.gridEBuy = gridEBuy;
    }

    public Double getBattChg() {
        return battChg;
    }

    public void setBattChg(Double battChg) {
        this.battChg = battChg;
    }

    public Double getBattDis() {
        return battDis;
    }

    public void setBattDis(Double battDis) {
        this.battDis = battDis;
    }

    public Double getTotalBattChg() {
        return totalBattChg;
    }

    public void setTotalBattChg(Double totalBattChg) {
        this.totalBattChg = totalBattChg;
    }

    public Double getTotalBattDis() {
        return totalBattDis;
    }

    public void setTotalBattDis(Double totalBattDis) {
        this.totalBattDis = totalBattDis;
    }

    public Double getTotalGridESell() {
        return totalGridESell;
    }

    public void setTotalGridESell(Double totalGridESell) {
        this.totalGridESell = totalGridESell;
    }

    public Double getTotalGridEBuy() {
        return totalGridEBuy;
    }

    public void setTotalGridEBuy(Double totalGridEBuy) {
        this.totalGridEBuy = totalGridEBuy;
    }

    public Double getTotalDirectConsumpE() {
        return totalDirectConsumpE;
    }

    public void setTotalDirectConsumpE(Double totalDirectConsumpE) {
        this.totalDirectConsumpE = totalDirectConsumpE;
    }

    public Double getTotalConsumpE() {
        return totalConsumpE;
    }

    public void setTotalConsumpE(Double totalConsumpE) {
        this.totalConsumpE = totalConsumpE;
    }

    public Double getTotalPvE() {
        return totalPvE;
    }

    public void setTotalPvE(Double totalPvE) {
        this.totalPvE = totalPvE;
    }

    public Double getLimitGridPSell() {
        return limitGridPSell;
    }

    public void setLimitGridPSell(Double limitGridPSell) {
        this.limitGridPSell = limitGridPSell;
    }

    public Double getLimitGridPSellPercent() {
        return limitGridPSellPercent;
    }

    public void setLimitGridPSellPercent(Double limitGridPSellPercent) {
        this.limitGridPSellPercent = limitGridPSellPercent;
    }

    public Double getPvP() {
        return pvP;
    }

    public void setPvP(Double pvP) {
        this.pvP = pvP;
    }

    public Double getBattP() {
        return battP;
    }

    public void setBattP(Double battP) {
        this.battP = battP;
    }

    public Double getBattChgP() {
        return battChgP;
    }

    public void setBattChgP(Double battChgP) {
        this.battChgP = battChgP;
    }

    public Double getBattDisP() {
        return battDisP;
    }

    public void setBattDisP(Double battDisP) {
        this.battDisP = battDisP;
    }

    public Double getPvDcOut() {
        return pvDcOut;
    }

    public void setPvDcOut(Double pvDcOut) {
        this.pvDcOut = pvDcOut;
    }

    public Object getNationCode() {
        return nationCode;
    }

    public void setNationCode(Object nationCode) {
        this.nationCode = nationCode;
    }

    public Double getReductionFactor() {
        return reductionFactor;
    }

    public void setReductionFactor(Double reductionFactor) {
        this.reductionFactor = reductionFactor;
    }

    public Object getUtc() {
        return utc;
    }

    public void setUtc(Object utc) {
        this.utc = utc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getWinterModeSocLimit() {
        return winterModeSocLimit;
    }

    public void setWinterModeSocLimit(Integer winterModeSocLimit) {
        this.winterModeSocLimit = winterModeSocLimit;
    }

    public Object getRole() {
        return role;
    }

    public void setRole(Object role) {
        this.role = role;
    }

    public Integer getCompanyAuth() {
        return companyAuth;
    }

    public void setCompanyAuth(Integer companyAuth) {
        this.companyAuth = companyAuth;
    }

    public Integer getInstallerId() {
        return installerId;
    }

    public void setInstallerId(Integer installerId) {
        this.installerId = installerId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getCurrentPvP() {
        return currentPvP;
    }

    public void setCurrentPvP(Double currentPvP) {
        this.currentPvP = currentPvP;
    }

    public Double getCurrentBattP() {
        return currentBattP;
    }

    public void setCurrentBattP(Double currentBattP) {
        this.currentBattP = currentBattP;
    }

    public Double getCurrentLoadP() {
        return currentLoadP;
    }

    public void setCurrentLoadP(Double currentLoadP) {
        this.currentLoadP = currentLoadP;
    }

    public Double getCurrentGridP() {
        return currentGridP;
    }

    public void setCurrentGridP(Double currentGridP) {
        this.currentGridP = currentGridP;
    }

    public Integer getCurrentDirectConsumption() {
        return currentDirectConsumption;
    }

    public void setCurrentDirectConsumption(Integer currentDirectConsumption) {
        this.currentDirectConsumption = currentDirectConsumption;
    }

    public Integer getCurrentBattChg() {
        return currentBattChg;
    }

    public void setCurrentBattChg(Integer currentBattChg) {
        this.currentBattChg = currentBattChg;
    }

    public Integer getCurrentBattDis() {
        return currentBattDis;
    }

    public void setCurrentBattDis(Integer currentBattDis) {
        this.currentBattDis = currentBattDis;
    }

    public Integer getCurrentGridSell() {
        return currentGridSell;
    }

    public void setCurrentGridSell(Integer currentGridSell) {
        this.currentGridSell = currentGridSell;
    }

    public Integer getCurrentGridBuy() {
        return currentGridBuy;
    }

    public void setCurrentGridBuy(Integer currentGridBuy) {
        this.currentGridBuy = currentGridBuy;
    }

    public Integer getCurrentChgFromGrid() {
        return currentChgFromGrid;
    }

    public void setCurrentChgFromGrid(Integer currentChgFromGrid) {
        this.currentChgFromGrid = currentChgFromGrid;
    }

    public Integer getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }
}
