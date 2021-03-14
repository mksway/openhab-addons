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
package org.openhab.binding.lgessenervu.internal;

import java.util.Collections;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link LGEssEnervuBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Martin Klama - Initial contribution
 */
@NonNullByDefault
public class LGEssEnervuBindingConstants {

    private static final String BINDING_ID = "lgessenervu";

    public static final String POWERROUTER_THING_NAME = "powerrouter";

    public static final String LG_API_V1_APPKEY = "6V1V8H2BN5P9ZQGOI5DAQ92YZBDO3EK9";

    public static final int RECONNECT_DELAY = 60; // seconds

    public static enum DataSource {
        NONE,
        CLOUD_API_V1,
        CLOUD_API_V2,
        LAN_API
    }

    public static enum FailReason {
        NONE,
        WRONG_CREDENTIALS,
        COMMUNICATION_ERROR,
        PARSING_ERROR,
        CRITICAL
    }

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_POWERROUTER = new ThingTypeUID(BINDING_ID, POWERROUTER_THING_NAME);
    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections.singleton(THING_TYPE_POWERROUTER);

    // List of all Channel ids

    // GRID
    public static final String CHANNEL_CURRENT_POWER_FROM_GRID = "currentPowerFromGrid";
    public static final String CHANNEL_CURRENT_POWER_TO_GRID = "currentPowerToGrid";

    public static final String CHANNEL_DAILY_POWER_FROM_GRID = "dailyPowerFromGrid";
    public static final String CHANNEL_DAILY_POWER_TO_GRID = "dailyPowerToGrid";

    public static final String CHANNEL_MONTHLY_POWER_FROM_GRID = "monthlyPowerFromGrid";
    public static final String CHANNEL_MONTHLY_POWER_TO_GRID = "monthlyPowerToGrid";

    // PV
    public static final String CHANNEL_CURRENT_POWER_FROM_PV = "currentPowerFromPV";
    public static final String CHANNEL_DAILY_POWER_FROM_PV = "dailyPowerFromPV";
    public static final String CHANNEL_MONTHLY_POWER_FROM_PV = "monthlyPowerFromPV";
    public static final String CHANNEL_SELFCONSUMPTION_FROM_PV = "selfConsumption";

    public static final String CHANNEL_STRING1_VOLTAGE = "string1CurrentPotential";
    public static final String CHANNEL_STRING2_VOLTAGE = "string2CurrentPotential";
    public static final String CHANNEL_STRING3_VOLTAGE = "string3CurrentPotential";
    public static final String CHANNEL_STRING4_VOLTAGE = "string4CurrentPotential";
    public static final String CHANNEL_STRING5_VOLTAGE = "string5CurrentPotential";

    public static final String CHANNEL_STRING1_CURRENT = "string1CurrentCurrent";
    public static final String CHANNEL_STRING2_CURRENT = "string2CurrentCurrent";
    public static final String CHANNEL_STRING3_CURRENT = "string3CurrentCurrent";
    public static final String CHANNEL_STRING4_CURRENT = "string4CurrentCurrent";
    public static final String CHANNEL_STRING5_CURRENT = "string5CurrentCurrent";

    public static final String CHANNEL_STRING1_POWER = "string1CurrentPower";
    public static final String CHANNEL_STRING2_POWER = "string2CurrentPower";
    public static final String CHANNEL_STRING3_POWER = "string3CurrentPower";
    public static final String CHANNEL_STRING4_POWER = "string4CurrentPower";
    public static final String CHANNEL_STRING5_POWER = "string5CurrentPower";

    // BATTERY
    public static final String CHANNEL_BATTERY_SOC = "batterySoc";
    public static final String CHANNEL_BATTERY_SAFETYSOC = "battery_safety_soc";

    public static final String CHANNEL_BATTERY_STATUS = "batteryStatus";
    public static final String CHANNEL_BATTERY_WINTERMODE = "batteryWintermode";

    public static final String CHANNEL_CURRENT_POWER_FROM_BATTERY = "currentPowerDischargingFromBattery";
    public static final String CHANNEL_CURRENT_POWER_TO_BATTERY = "currentPowerChargingToBattery";

    public static final String CHANNEL_DAILY_POWER_FROM_BATTERY = "dailyPowerDischargingFromBattery";
    public static final String CHANNEL_DAILY_POWER_TO_BATTERY = "dailyBatteryCharge";

    public static final String CHANNEL_MONTHLY_POWER_FROM_BATTERY = "monthlyPowerDischargingFromBattery";
    public static final String CHANNEL_MONTHLY_POWER_TO_BATTERY = "monthlyBatteryCharge";

    // LOAD
    public static final String CHANNEL_CURRENT_TOTAL_POWER_CONSUMPTION = "currentTotalPowerConsumption";
    public static final String CHANNEL_DAILY_TOTAL_POWER_CONSUMPTION = "dailyTotalPowerConsumption";
    public static final String CHANNEL_MONTHLY_TOTAL_POWER_CONSUMPTION = "monthlyTotalPowerConsumption";

    public static final String CHANNEL_CURRENT_DIRECT_POWER_CONSUMPTION = "currentDirectPowerConsumption";
    public static final String CHANNEL_DAILY_DIRECT_POWER_CONSUMPTION_FROM_PV = "dailyDirectPowerConsumption";
    public static final String CHANNEL_MONTHLY_DIRECT_POWER_CONSUMPTION_FROM_PV = "monthlyDirectPowerConsumption";

    // STATUS
    public static final String CHANNEL_ISDIRECTUSE = "isDirectConsuming";
    public static final String CHANNEL_ISCHARGING = "isBatteryCharging";
    public static final String CHANNEL_ISDISCHARGING = "isBatteryDischarging";
    public static final String CHANNEL_ISSELLING = "isGridSelling";
    public static final String CHANNEL_ISBUYING = "isGridBuying";
    public static final String CHANNEL_ISCHARGINGFROMGRID = "isChargingFromGrid";

    // monthly
    public static final String CHANNEL_MONTHLY_CO2SAVINGS = "monthlyCO2Savings";
    public static final String CHANNEL_MONTHLY_EARNINGS = "monthlyEarnings";
    public static final String CHANNEL_MONTHLY_PAID = "monthlyPaid";
    public static final String CHANNEL_MONTHLY_SAVINGS = "monthlyMoneySavings";
}
