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
 * The {@link OverviewData} is an auto generated DTO
 *
 * @author Martin Klama - initial contribution
 */
public class OverviewData {

    @SerializedName("dayResult")
    @Expose
    private DayResult dayResult;
    @SerializedName("coResult")
    @Expose
    private CoResult coResult;
    @SerializedName("monthResult")
    @Expose
    private MonthResult monthResult;

    public DayResult getDayResult() {
        return dayResult;
    }

    public void setDayResult(DayResult dayResult) {
        this.dayResult = dayResult;
    }

    public CoResult getCoResult() {
        return coResult;
    }

    public void setCoResult(CoResult coResult) {
        this.coResult = coResult;
    }

    public MonthResult getMonthResult() {
        return monthResult;
    }

    public void setMonthResult(MonthResult monthResult) {
        this.monthResult = monthResult;
    }
}
