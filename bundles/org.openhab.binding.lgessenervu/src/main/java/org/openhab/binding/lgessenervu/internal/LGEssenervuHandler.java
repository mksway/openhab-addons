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

import static org.openhab.binding.lgessenervu.internal.LGEssEnervuBindingConstants.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.openhab.binding.lgessenervu.internal.LGEssEnervuBindingConstants.FailReason;
import org.openhab.binding.lgessenervu.internal.client.IResponseCallback;
import org.openhab.binding.lgessenervu.internal.client.LGCloudClient;
import org.openhab.binding.lgessenervu.internal.client.LGEssClient;
import org.openhab.binding.lgessenervu.internal.client.LGLanClient;
import org.openhab.binding.lgessenervu.internal.client.ResponseData;
import org.openhab.binding.lgessenervu.internal.job.FifteenMinJob;
import org.openhab.binding.lgessenervu.internal.job.SnapshotJob;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.QuantityType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.library.unit.SIUnits;
import org.openhab.core.library.unit.Units;
import org.openhab.core.scheduler.CronScheduler;
import org.openhab.core.scheduler.ScheduledCompletableFuture;
import org.openhab.core.thing.Channel;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link LGEssenervuHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Martin Klama - Initial contribution
 */
@NonNullByDefault
public class LGEssenervuHandler extends BaseThingHandler implements IResponseCallback {

    private final Logger logger = LoggerFactory.getLogger(LGEssenervuHandler.class);

    private LGEssenervuConfiguration config;
    private final @Nullable HttpClient httpclient;

    private @Nullable CronScheduler cronscheduler;
    private @Nullable ScheduledCompletableFuture<?> fifteenminJob;
    private final Lock monitor = new ReentrantLock();
    private final Set<ScheduledFuture<?>> scheduledFutures = new HashSet<>();
    private final String CRON_15MIN = "0 0/15 * ? * * *";

    private LGEssClient lgessClient;
    private int refreshInterval;
    private double co2factor;
    private double eurprokwh;
    private double eurprokwhs;

    public LGEssenervuHandler(Thing thing, @Nullable HttpClient client, @Nullable CronScheduler cronScheduler) {
        super(thing);
        this.httpclient = client;
        this.cronscheduler = cronScheduler;
        this.config = new LGEssenervuConfiguration();
        lgessClient = new LGCloudClient(httpclient);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {

        if (command instanceof RefreshType) {
            scheduler.execute(() -> {
                if (lgessClient.getLoginStatus()) {
                    lgessClient.getCurrentData();
                    if (true == config.dataSourceCloud) {
                        lgessClient.get15MinOverview();
                    }
                }
            });
        }
    }

    @Override
    public void initialize() {
        config = getConfigAs(LGEssenervuConfiguration.class);

        updateStatus(ThingStatus.UNKNOWN);

        if (true == config.dataSourceCloud) {

            if (config.passwordCloud.isBlank()) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                        "you need to provide the password for the Cloud-API");
                return;
            }
            if (config.user.isBlank()) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                        "you need to provide the username/email for the Cloud-API");
                return;
            }

            lgessClient = new LGCloudClient(httpclient);
            lgessClient.setUserID(config.user);
            lgessClient.setPassword(config.passwordCloud);
            refreshInterval = config.refreshIntervalCloud;

        } else {

            if (config.hostName.isBlank()) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                        "you need to provide the host/ip of the device! (enable advance config)");
                return;
            }
            if (config.passwordLocal.isBlank()) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                        "you need to provide the password for the LAN-API");
                return;
            }

            lgessClient = new LGLanClient(httpclient);
            lgessClient.setPassword(config.passwordLocal);
            lgessClient.setHostname(config.hostName);
            refreshInterval = config.refreshInterval;
        }

        co2factor = config.co2Factor;
        eurprokwh = config.kwhPrice;
        eurprokwhs = config.kwhPriceSell;

        lgessClient.setTimeout(config.timeout);
        lgessClient.registerCallback(this);

        scheduler.execute(() -> {
            if (false == lgessClient.getLoginStatus()) {
                try {
                    lgessClient.Login();
                } catch (Exception e) {
                    updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "Wrong credentials");
                }
            }
        });

        logger.info("LGEssEnervu Thing initialized");
    }

    @Override
    public void dispose() {
        lgessClient.unregisterCallback();
        stopPolling();
        super.dispose();
    }

    @Override
    public void handleRemoval() {
        lgessClient.unregisterCallback();
        stopPolling();
        super.handleRemoval();
    }

    public void startPolling() {
        monitor.lock();
        try {
            SnapshotJob sjob = new SnapshotJob(lgessClient);

            ScheduledFuture<?> future = scheduler.scheduleWithFixedDelay(sjob, 0, refreshInterval, TimeUnit.SECONDS);
            scheduledFutures.add(future);

            if (true == getConfigAs(LGEssenervuConfiguration.class).dataSourceCloud) {

                FifteenMinJob runnable = new FifteenMinJob(lgessClient);
                if (null != cronscheduler) {
                    fifteenminJob = cronscheduler.schedule(runnable, CRON_15MIN);
                    runnable.run();
                }
            }
        } catch (Exception ex) {
            logger.error("startpolling - refreshInterval {} with error {}", refreshInterval, ex.getMessage(), ex);
        } finally {
            monitor.unlock();
        }
    }

    public void stopPolling() {
        monitor.lock();
        try {
            if (cronscheduler != null) {
                if (fifteenminJob != null) {
                    fifteenminJob.cancel(true);
                }
                fifteenminJob = null;
            }

            for (ScheduledFuture<?> future : scheduledFutures) {
                if (!future.isDone()) {
                    future.cancel(true);
                }
            }
            scheduledFutures.clear();
        } catch (Exception e) {
            logger.error("Failed to stop polling jobs!");
        } finally {
            monitor.unlock();
        }
    }

    @Override
    public void responseCallbackLoggedIn(boolean isloggedin, FailReason reason) {

        if (false == isloggedin) {
            stopPolling();

            switch (reason) {
                case COMMUNICATION_ERROR:
                    updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                            "Communication with service failed :(");
                    break;
                case NONE:
                    updateStatus(ThingStatus.OFFLINE);
                    break;
                case WRONG_CREDENTIALS:
                    updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                            "Access denied. Check credentials!");
                    return;
                default:
                    updateStatus(ThingStatus.OFFLINE);
                    break;

            }

            scheduler.schedule(() -> {
                if (false == lgessClient.getLoginStatus()) {
                    lgessClient.Login();

                }
            }, LGEssEnervuBindingConstants.RECONNECT_DELAY, TimeUnit.SECONDS);

        } else {
            updateStatus(ThingStatus.ONLINE);

            if (scheduledFutures.isEmpty()) {
                startPolling();
            }
        }
    }

    public void publishChannelIfLinked(ChannelUID channelUID, Object value) {

        if (isLinked(channelUID.getId()) && getThing().isEnabled() && (getThing().getStatus() == ThingStatus.ONLINE)) {

            final Channel channel = getThing().getChannel(channelUID.getId());
            if (null == channel) {
                logger.error("Cannot find channel for {}", channelUID);
                return;
            }

            try {
                if (value instanceof String) {
                    updateState(channelUID, new StringType((String) value));
                } else if (value instanceof Integer) {
                    updateState(channelUID, new DecimalType((int) value));
                } else if (value instanceof QuantityType) {
                    updateState(channelUID, (QuantityType<?>) value);
                } else if (value instanceof OnOffType) {
                    updateState(channelUID, (OnOffType) value);
                } else if (value instanceof Double) {
                    updateState(channelUID, new DecimalType((double) value));
                } else {
                    logger.error("unable to publish {} since type is {}", channelUID, value.getClass());
                }

            } catch (Exception ex) {
                logger.error("Can't update state for channel {} : {}", channelUID, ex.getMessage(), ex);
            }

        }
    }

    private int getNumericValueOfString(String input) {
        int returnval = 0;

        try {
            returnval = (int) Double.parseDouble(input);
        } catch (Exception e) {
            logger.error("getNumericValueOfString {}", e.getMessage(), e);
        }

        return returnval;
    }

    @Override
    public void responseCallbackCurrentData(ResponseData responseData) {

        if (this.getThing().getStatus() != ThingStatus.ONLINE) {
            stopPolling();
            return;
        }

        // grid
        Channel chan_current_pwr_from_grid = getThing().getChannel(CHANNEL_CURRENT_POWER_FROM_GRID);
        Channel chan_current_pwr_to_grid = getThing().getChannel(CHANNEL_CURRENT_POWER_TO_GRID);
        // pv
        Channel chan_current_pwr_from_pv = getThing().getChannel(CHANNEL_CURRENT_POWER_FROM_PV);
        Channel chan_selfconsumption_pv = getThing().getChannel(CHANNEL_SELFCONSUMPTION_FROM_PV);

        Channel chan_string1_voltage_pv = getThing().getChannel(CHANNEL_STRING1_VOLTAGE);
        Channel chan_string2_voltage_pv = getThing().getChannel(CHANNEL_STRING2_VOLTAGE);
        Channel chan_string3_voltage_pv = getThing().getChannel(CHANNEL_STRING3_VOLTAGE);
        Channel chan_string4_voltage_pv = getThing().getChannel(CHANNEL_STRING4_VOLTAGE);
        Channel chan_string5_voltage_pv = getThing().getChannel(CHANNEL_STRING5_VOLTAGE);

        Channel chan_string1_current_pv = getThing().getChannel(CHANNEL_STRING1_CURRENT);
        Channel chan_string2_current_pv = getThing().getChannel(CHANNEL_STRING2_CURRENT);
        Channel chan_string3_current_pv = getThing().getChannel(CHANNEL_STRING3_CURRENT);
        Channel chan_string4_current_pv = getThing().getChannel(CHANNEL_STRING4_CURRENT);
        Channel chan_string5_current_pv = getThing().getChannel(CHANNEL_STRING5_CURRENT);

        Channel chan_string1_power_pv = getThing().getChannel(CHANNEL_STRING1_POWER);
        Channel chan_string2_power_pv = getThing().getChannel(CHANNEL_STRING2_POWER);
        Channel chan_string3_power_pv = getThing().getChannel(CHANNEL_STRING3_POWER);
        Channel chan_string4_power_pv = getThing().getChannel(CHANNEL_STRING4_POWER);
        Channel chan_string5_power_pv = getThing().getChannel(CHANNEL_STRING5_POWER);

        // battery
        Channel chan_current_battery_soc = getThing().getChannel(CHANNEL_BATTERY_SOC);
        Channel chan_current_battery_status = getThing().getChannel(CHANNEL_BATTERY_STATUS);
        Channel chan_current_battery_wintermode = getThing().getChannel(CHANNEL_BATTERY_WINTERMODE);

        Channel chan_current_battery_power_discharge = getThing().getChannel(CHANNEL_CURRENT_POWER_FROM_BATTERY);
        Channel chan_current_battery_power_charge = getThing().getChannel(CHANNEL_CURRENT_POWER_TO_BATTERY);

        // load
        Channel chan_current_total_power_consumption = getThing().getChannel(CHANNEL_CURRENT_TOTAL_POWER_CONSUMPTION);

        Channel chan_current_direct_power_consumption = getThing().getChannel(CHANNEL_CURRENT_DIRECT_POWER_CONSUMPTION);

        // status
        Channel chan_isdirect = getThing().getChannel(CHANNEL_ISDIRECTUSE);
        Channel chan_ischarging = getThing().getChannel(CHANNEL_ISCHARGING);
        Channel chan_isdischarging = getThing().getChannel(CHANNEL_ISDISCHARGING);
        Channel chan_isselling = getThing().getChannel(CHANNEL_ISSELLING);
        Channel chan_isbuying = getThing().getChannel(CHANNEL_ISBUYING);
        Channel chan_ischargingfromgrid = getThing().getChannel(CHANNEL_ISCHARGINGFROMGRID);

        if (chan_current_pwr_from_grid != null) {
            publishChannelIfLinked(chan_current_pwr_from_grid.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getGRID().getActivePower()), Units.WATT));

        }
        if (chan_current_pwr_to_grid != null) {
            if (responseData.getStats().getDirection().getIsGridSelling().equals("1")) {
                publishChannelIfLinked(chan_current_pwr_to_grid.getUID(), new QuantityType<>(
                        getNumericValueOfString(responseData.getCommon().getGRID().getActivePower()), Units.WATT));
            } else {
                publishChannelIfLinked(chan_current_pwr_to_grid.getUID(), new QuantityType<>(0.0, Units.WATT));
            }

        }

        if (chan_string1_voltage_pv != null) {
            publishChannelIfLinked(chan_string1_voltage_pv.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getPV().getPv1Voltage()), Units.VOLT));
        }
        if (chan_string2_voltage_pv != null) {
            publishChannelIfLinked(chan_string2_voltage_pv.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getPV().getPv2Voltage()), Units.VOLT));
        }
        if (chan_string3_voltage_pv != null) {
            publishChannelIfLinked(chan_string3_voltage_pv.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getPV().getPv3Voltage()), Units.VOLT));
        }
        if (chan_string4_voltage_pv != null) {
            publishChannelIfLinked(chan_string4_voltage_pv.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getPV().getPv4Voltage()), Units.VOLT));
        }
        if (chan_string5_voltage_pv != null) {
            publishChannelIfLinked(chan_string5_voltage_pv.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getPV().getPv5Voltage()), Units.VOLT));
        }

        if (chan_string1_current_pv != null) {
            publishChannelIfLinked(chan_string1_current_pv.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getPV().getPv1Current()), Units.AMPERE));
        }
        if (chan_string2_current_pv != null) {
            publishChannelIfLinked(chan_string2_current_pv.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getPV().getPv2Current()), Units.AMPERE));
        }
        if (chan_string3_current_pv != null) {
            publishChannelIfLinked(chan_string3_current_pv.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getPV().getPv3Current()), Units.AMPERE));
        }
        if (chan_string4_current_pv != null) {
            publishChannelIfLinked(chan_string4_current_pv.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getPV().getPv4Current()), Units.AMPERE));
        }
        if (chan_string5_current_pv != null) {
            publishChannelIfLinked(chan_string5_current_pv.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getPV().getPv5Current()), Units.AMPERE));
        }

        if (chan_string1_power_pv != null) {
            publishChannelIfLinked(chan_string1_power_pv.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getPV().getPv1Power()), Units.WATT));
        }
        if (chan_string2_power_pv != null) {
            publishChannelIfLinked(chan_string2_power_pv.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getPV().getPv2Power()), Units.WATT));
        }
        if (chan_string3_power_pv != null) {
            publishChannelIfLinked(chan_string3_power_pv.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getPV().getPv3Power()), Units.WATT));
        }
        if (chan_string4_power_pv != null) {
            publishChannelIfLinked(chan_string4_power_pv.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getPV().getPv4Power()), Units.WATT));
        }
        if (chan_string5_power_pv != null) {
            publishChannelIfLinked(chan_string5_power_pv.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getPV().getPv5Power()), Units.WATT));
        }

        if (chan_current_pwr_from_pv != null) {
            // take all 5 strings into account
            int allstrings = getNumericValueOfString(responseData.getCommon().getPV().getPv1Power());
            allstrings += getNumericValueOfString(responseData.getCommon().getPV().getPv2Power());
            allstrings += getNumericValueOfString(responseData.getCommon().getPV().getPv3Power());
            allstrings += getNumericValueOfString(responseData.getCommon().getPV().getPv4Power());
            allstrings += getNumericValueOfString(responseData.getCommon().getPV().getPv5Power());

            publishChannelIfLinked(chan_current_pwr_from_pv.getUID(), new QuantityType<>(allstrings, Units.WATT));
        }

        if (chan_selfconsumption_pv != null) {
            publishChannelIfLinked(chan_selfconsumption_pv.getUID(),
                    new QuantityType<>(
                            getNumericValueOfString(responseData.getCommon().getPCS().getTodaySelfConsumption()),
                            Units.PERCENT));
        }

        if (chan_current_battery_soc != null) {
            publishChannelIfLinked(chan_current_battery_soc.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getBATT().getSoc()), Units.PERCENT));
        }
        if (chan_current_battery_status != null) {
            publishChannelIfLinked(chan_current_battery_status.getUID(),
                    responseData.getCommon().getBATT().getStatus());
        }
        if (chan_current_battery_wintermode != null) {
            publishChannelIfLinked(chan_current_battery_wintermode.getUID(),
                    OnOffType.from(responseData.getCommon().getBATT().getWinterStatus())); //
        }
        if (chan_current_total_power_consumption != null) {
            publishChannelIfLinked(chan_current_total_power_consumption.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getLOAD().getLoadPower()), Units.WATT));
        }

        // calculated channels

        if (chan_current_direct_power_consumption != null) {
            double directpow = 0;
            try {
                directpow = Double.parseDouble(responseData.getCommon().getLOAD().getLoadPower())
                        - Double.parseDouble(responseData.getCommon().getGRID().getActivePower());

                if (directpow < 0) {
                    directpow = 0;
                }
            } catch (Exception e) {

            }
            publishChannelIfLinked(chan_current_direct_power_consumption.getUID(),
                    new QuantityType<>(directpow, Units.WATT));
        }

        if (null != chan_current_battery_power_charge && null != chan_current_battery_power_discharge
                && responseData.getStats().getDirection().getIsBatteryCharging().equals("0")
                && responseData.getStats().getDirection().getIsBatteryDischarging().equals("0")) {
            publishChannelIfLinked(chan_current_battery_power_discharge.getUID(), new QuantityType<>(0.0, Units.WATT));
            publishChannelIfLinked(chan_current_battery_power_charge.getUID(), new QuantityType<>(0.0, Units.WATT));
        }

        if (null != chan_current_battery_power_charge && null != chan_current_battery_power_discharge
                && responseData.getStats().getDirection().getIsBatteryCharging().equals("1")) {
            publishChannelIfLinked(chan_current_battery_power_discharge.getUID(), new QuantityType<>(0.0, Units.WATT));
            publishChannelIfLinked(chan_current_battery_power_charge.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getBATT().getDcPower()), Units.WATT));
        }

        if (null != chan_current_battery_power_charge && null != chan_current_battery_power_discharge
                && responseData.getStats().getDirection().getIsBatteryDischarging().equals("1")) {

            publishChannelIfLinked(chan_current_battery_power_discharge.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getBATT().getDcPower()), Units.WATT));

            publishChannelIfLinked(chan_current_battery_power_charge.getUID(), new QuantityType<>(0.0, Units.WATT));
        }

        if (chan_isdirect != null) {
            publishChannelIfLinked(chan_isdirect.getUID(),
                    OnOffType.from(responseData.getCommon().getBATT().getWinterStatus()));
        }

        if (chan_ischarging != null) {
            publishChannelIfLinked(chan_ischarging.getUID(),
                    OnOffType.from(responseData.getStats().getDirection().getIsBatteryCharging()));
        }
        if (chan_isdischarging != null) {
            publishChannelIfLinked(chan_isdischarging.getUID(),
                    OnOffType.from(responseData.getStats().getDirection().getIsBatteryDischarging()));
        }

        if (chan_isselling != null) {
            publishChannelIfLinked(chan_isselling.getUID(),
                    OnOffType.from(responseData.getStats().getDirection().getIsGridSelling()));
        }
        if (chan_isbuying != null) {
            publishChannelIfLinked(chan_isbuying.getUID(),
                    OnOffType.from(responseData.getStats().getDirection().getIsGridBuying()));
        }
        if (chan_ischargingfromgrid != null) {
            publishChannelIfLinked(chan_ischargingfromgrid.getUID(),
                    OnOffType.from(responseData.getStats().getDirection().getIsChargingFromGrid()));
        }
    }

    @Override
    public void responseCallbackDaily(ResponseData responseData) {
        if (this.getThing().getStatus() != ThingStatus.ONLINE) {
            stopPolling();
            return;
        }

        Channel chan_daily_consumption_from_grid = getThing().getChannel(CHANNEL_DAILY_POWER_FROM_GRID);
        Channel chan_monthly_consumption_from_grid = getThing().getChannel(CHANNEL_MONTHLY_POWER_FROM_GRID);

        Channel chan_daily_production_from_pv = getThing().getChannel(CHANNEL_DAILY_POWER_FROM_PV);
        Channel chan_monthly_production_from_pv = getThing().getChannel(CHANNEL_MONTHLY_POWER_FROM_PV);

        Channel chan_daily_soldpower_to_grid = getThing().getChannel(CHANNEL_DAILY_POWER_TO_GRID);
        Channel chan_monthly_soldpower_to_grid = getThing().getChannel(CHANNEL_MONTHLY_POWER_TO_GRID);

        Channel chan_daily_battery_power_charge = getThing().getChannel(CHANNEL_DAILY_POWER_TO_BATTERY);
        Channel chan_monthly_battery_power_charge = getThing().getChannel(CHANNEL_MONTHLY_POWER_TO_BATTERY);

        Channel chan_daily_battery_power_discharge = getThing().getChannel(CHANNEL_DAILY_POWER_FROM_BATTERY);
        Channel chan_monthly_battery_power_discharge = getThing().getChannel(CHANNEL_MONTHLY_POWER_FROM_BATTERY);

        Channel chan_daily_total_power_consumption = getThing().getChannel(CHANNEL_DAILY_TOTAL_POWER_CONSUMPTION);
        Channel chan_monthly_total_power_consumption = getThing().getChannel(CHANNEL_MONTHLY_TOTAL_POWER_CONSUMPTION);

        Channel chan_daily_direct_power_consumption = getThing()
                .getChannel(CHANNEL_DAILY_DIRECT_POWER_CONSUMPTION_FROM_PV);

        Channel chan_monthly_direct_power_consumption = getThing()
                .getChannel(CHANNEL_MONTHLY_DIRECT_POWER_CONSUMPTION_FROM_PV);

        Channel chan_month_co2_savings = getThing().getChannel(CHANNEL_MONTHLY_CO2SAVINGS);
        Channel chan_month_paid = getThing().getChannel(CHANNEL_MONTHLY_PAID);
        Channel chan_month_earnings = getThing().getChannel(CHANNEL_MONTHLY_EARNINGS);
        Channel chan_month_savings = getThing().getChannel(CHANNEL_MONTHLY_SAVINGS);

        if (chan_daily_consumption_from_grid != null) {

            publishChannelIfLinked(chan_daily_consumption_from_grid.getUID(),
                    new QuantityType<>(
                            getNumericValueOfString(
                                    responseData.getCommon().getGRID().getTodayGridPowerPurchaseEnergy()),
                            Units.WATT_HOUR));
        }
        if (chan_monthly_consumption_from_grid != null) {
            publishChannelIfLinked(chan_monthly_consumption_from_grid.getUID(),
                    new QuantityType<>(
                            getNumericValueOfString(
                                    responseData.getCommon().getGRID().getMonthGridPowerPurchaseEnergy()),
                            Units.WATT_HOUR));
        }

        if (chan_daily_production_from_pv != null) {
            publishChannelIfLinked(chan_daily_production_from_pv.getUID(),
                    new QuantityType<>(
                            getNumericValueOfString(responseData.getCommon().getPV().getTodayPvGenerationSum()),
                            Units.WATT_HOUR));
        }

        if (chan_daily_soldpower_to_grid != null) {
            publishChannelIfLinked(chan_daily_soldpower_to_grid.getUID(),
                    new QuantityType<>(
                            getNumericValueOfString(responseData.getCommon().getGRID().getTodayGridFeedInEnergy()),
                            Units.WATT_HOUR));
        }

        if (chan_monthly_production_from_pv != null) {
            publishChannelIfLinked(chan_monthly_production_from_pv.getUID(),
                    new QuantityType<>(
                            getNumericValueOfString(responseData.getCommon().getPV().getTodayMonthPvGenerationSum()),
                            Units.WATT_HOUR));
        }

        if (chan_monthly_soldpower_to_grid != null) {
            publishChannelIfLinked(chan_monthly_soldpower_to_grid.getUID(),
                    new QuantityType<>(
                            getNumericValueOfString(responseData.getCommon().getGRID().getMonthGridFeedInEnergy()),
                            Units.WATT_HOUR));
        }

        if (chan_daily_battery_power_charge != null) {
            publishChannelIfLinked(chan_daily_battery_power_charge.getUID(),
                    new QuantityType<>(
                            getNumericValueOfString(responseData.getCommon().getBATT().getTodayBattChargeEnergy()),
                            Units.WATT_HOUR));
        }

        if (chan_monthly_battery_power_charge != null) {
            publishChannelIfLinked(chan_monthly_battery_power_charge.getUID(),
                    new QuantityType<>(
                            getNumericValueOfString(responseData.getCommon().getBATT().getMonthBattChargeEnergy()),
                            Units.WATT_HOUR));
        }

        if (chan_daily_battery_power_discharge != null) {
            publishChannelIfLinked(chan_daily_battery_power_discharge.getUID(),
                    new QuantityType<>(
                            getNumericValueOfString(responseData.getCommon().getBATT().getTodayBattDischargeEnery()),
                            Units.WATT_HOUR));
        }
        if (chan_monthly_battery_power_discharge != null) {

            publishChannelIfLinked(chan_monthly_battery_power_discharge.getUID(),
                    new QuantityType<>(
                            getNumericValueOfString(responseData.getCommon().getBATT().getMonthBattDischargeEnergy()),
                            Units.WATT_HOUR));
        }

        if (chan_daily_total_power_consumption != null) {
            publishChannelIfLinked(chan_daily_total_power_consumption.getUID(),
                    new QuantityType<>(
                            getNumericValueOfString(responseData.getCommon().getLOAD().getTodayLoadConsumptionSum()),
                            Units.WATT_HOUR));
        }

        if (chan_monthly_total_power_consumption != null) {
            publishChannelIfLinked(chan_monthly_total_power_consumption.getUID(),
                    new QuantityType<>(
                            getNumericValueOfString(responseData.getCommon().getLOAD().getMonthLoadConsumptionSum()),
                            Units.WATT_HOUR));
        }

        if (chan_daily_direct_power_consumption != null) {
            publishChannelIfLinked(chan_daily_direct_power_consumption.getUID(),
                    new QuantityType<>(
                            getNumericValueOfString(
                                    responseData.getCommon().getLOAD().getTodayPvDirectConsumptionEnegy()),
                            Units.WATT_HOUR));
        }

        if (chan_monthly_direct_power_consumption != null) {
            publishChannelIfLinked(chan_monthly_direct_power_consumption.getUID(),
                    new QuantityType<>(
                            getNumericValueOfString(
                                    responseData.getCommon().getLOAD().getMonthPvDirectConsumptionEnergy()),
                            Units.WATT_HOUR));
        }

        if (chan_month_co2_savings != null) {
            publishChannelIfLinked(chan_month_co2_savings.getUID(),
                    new QuantityType<>(
                            ((getNumericValueOfString(responseData.getCommon().getPV().getTodayMonthPvGenerationSum()))
                                    * co2factor) / 1000,
                            SIUnits.KILOGRAM));

        }

        if (chan_month_paid != null) {
            double buy = getNumericValueOfString(responseData.getCommon().getGRID().getMonthGridPowerPurchaseEnergy());
            // given in Wh... need it in kWh
            buy = buy / 1000;
            buy = buy * eurprokwh;

            publishChannelIfLinked(chan_month_paid.getUID(), buy);
        }

        if (chan_month_earnings != null) {
            double sell = getNumericValueOfString(responseData.getCommon().getGRID().getMonthGridFeedInEnergy());
            // given in Wh... need it in kWh
            sell = sell / 1000;
            sell = sell * eurprokwhs;
            publishChannelIfLinked(chan_month_earnings.getUID(), sell);
        }

        if (chan_month_savings != null) {
            double savings = getNumericValueOfString(responseData.getCommon().getLOAD().getMonthLoadConsumptionSum())
                    - getNumericValueOfString(responseData.getCommon().getLOAD().getMonthGridPowerPurchaseEnergy());
            // given in Wh... need it in kWh
            savings = savings / 1000.00;
            savings = savings * eurprokwh;
            publishChannelIfLinked(chan_month_savings.getUID(), savings);
        }
    }

    @Override
    public void responseCallbackError(FailReason reason) {
        switch (reason) {
            case COMMUNICATION_ERROR:
                stopPolling();
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                        "Check network connection please.");
                break;
            case CRITICAL:
                stopPolling();
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.NONE, "Critical error - File a bug report please!");
                break;
            case NONE:
                break;
            case WRONG_CREDENTIALS:
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                        "Login failed. please check your credentials!");
                break;
            default:
                break;

        }
    }
}
