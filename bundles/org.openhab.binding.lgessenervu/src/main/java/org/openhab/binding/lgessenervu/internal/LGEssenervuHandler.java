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
import org.openhab.binding.lgessenervu.internal.client.gson.cloud.OverviewData;
import org.openhab.binding.lgessenervu.internal.job.SnapshotJob;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.QuantityType;
import org.openhab.core.library.types.StringType;
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
    // private String CRON_15MIN = "0 0/15 * ? * * *";

    private LGEssClient lgessClient;
    private int refreshInterval;

    public LGEssenervuHandler(Thing thing, @Nullable HttpClient client, @Nullable CronScheduler cronScheduler) {
        super(thing);
        this.httpclient = client;
        this.cronscheduler = cronScheduler;
        this.config = new LGEssenervuConfiguration();
        lgessClient = new LGCloudClient(httpclient);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
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
        }

        refreshInterval = config.refreshInterval;

        lgessClient.setTimeout(config.timeout);
        lgessClient.registerCallback(this);

        scheduler.execute(() -> {
            if (false == lgessClient.getLoginStatus()) {
                lgessClient.Login();
            }
        });

        logger.info("LGEssEnervu Thing initialized");

    }

    @Override
    public void dispose() {
        logger.debug("dispose called");
        lgessClient.unregisterCallback();
        stopPolling();
        super.dispose();
    }

    @Override
    public void handleRemoval() {
        logger.debug("removal called");
        lgessClient.unregisterCallback();
        stopPolling();
        super.handleRemoval();
    }

    public void startPolling() {
        logger.debug("start polling");
        monitor.lock();
        try {
            SnapshotJob sjob = new SnapshotJob(lgessClient);

            ScheduledFuture<?> future = scheduler.scheduleWithFixedDelay(sjob, 0, refreshInterval, TimeUnit.SECONDS);
            scheduledFutures.add(future);
            logger.warn("Scheduled {} every {} seconds", "status job", refreshInterval);
            /*
             * FifteenMinJob runnable = new FifteenMinJob(lgcloudclient);
             * fifteenminJob = cronscheduler.schedule(runnable, CRON_15MIN);
             * runnable.run();
             * logger.debug("Scheduled {} every 15min", fifteenminJob);
             */
        } catch (Exception ex) {
            logger.error("{}\n{}", ex.getMessage(), ex);
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
            }, 60, TimeUnit.SECONDS);

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

                    logger.warn("unable to publish {} since type is {}", channelUID, value.getClass());
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
            logger.error("{}", e.getMessage());
        }

        return returnval;
    }

    @Override
    public void responseCallbackCurrentData(ResponseData responseData) {

        if (this.getThing().getStatus() != ThingStatus.ONLINE) {
            stopPolling();
            return;
        }

        logger.warn("handler callback called! with {}", responseData);

        // grid
        Channel chan_current_pwr_from_grid = getThing().getChannel(CHANNEL_CURRENT_POWER_FROM_GRID);
        Channel chan_current_pwr_to_grid = getThing().getChannel(CHANNEL_CURRENT_POWER_TO_GRID);
        // pv
        Channel chan_current_pwr_from_pv = getThing().getChannel(CHANNEL_CURRENT_POWER_FROM_PV);
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
                    getNumericValueOfString(responseData.getCommon().getGRID().getActivePower()), Units.WATT_HOUR));

        }
        if (chan_current_pwr_to_grid != null) {
            if (responseData.getStats().getDirection().getIsGridSelling().equals("1")) {
                publishChannelIfLinked(chan_current_pwr_to_grid.getUID(), new QuantityType<>(
                        getNumericValueOfString(responseData.getCommon().getGRID().getActivePower()), Units.WATT_HOUR));
            } else {
                publishChannelIfLinked(chan_current_pwr_to_grid.getUID(), new QuantityType<>(0.0, Units.WATT_HOUR));
            }

        }
        if (chan_current_pwr_from_pv != null) {
            publishChannelIfLinked(chan_current_pwr_from_pv.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getPV().getPv1Power()), Units.WATT_HOUR));
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
                    getNumericValueOfString(responseData.getCommon().getLOAD().getLoadPower()), Units.WATT_HOUR));
        }

        // calculated channels

        if (chan_current_direct_power_consumption != null) {
            double directpow = 0;
            logger.warn("load power -> {}", responseData.getCommon().getLOAD().getLoadPower());
            directpow = Double.parseDouble(responseData.getCommon().getLOAD().getLoadPower())
                    - Double.parseDouble(responseData.getCommon().getGRID().getActivePower());
            if (directpow < 0) {
                directpow = 0;
            }
            publishChannelIfLinked(chan_current_direct_power_consumption.getUID(),
                    new QuantityType<>(directpow, Units.WATT_HOUR));
        }

        if (null != chan_current_battery_power_charge && null != chan_current_battery_power_discharge
                && responseData.getStats().getDirection().getIsBatteryCharging().equals("0")
                && responseData.getStats().getDirection().getIsBatteryDischarging().equals("0")) {
            publishChannelIfLinked(chan_current_battery_power_discharge.getUID(),
                    new QuantityType<>(0.0, Units.WATT_HOUR));
            publishChannelIfLinked(chan_current_battery_power_charge.getUID(),
                    new QuantityType<>(0.0, Units.WATT_HOUR));
        }

        if (null != chan_current_battery_power_charge && null != chan_current_battery_power_discharge
                && responseData.getStats().getDirection().getIsBatteryCharging().equals("1")) {
            publishChannelIfLinked(chan_current_battery_power_discharge.getUID(),
                    new QuantityType<>(0.0, Units.WATT_HOUR));
            publishChannelIfLinked(chan_current_battery_power_charge.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getBATT().getDcPower()), Units.WATT_HOUR));
        }

        if (null != chan_current_battery_power_charge && null != chan_current_battery_power_discharge
                && responseData.getStats().getDirection().getIsBatteryDischarging().equals("1")) {

            publishChannelIfLinked(chan_current_battery_power_discharge.getUID(), new QuantityType<>(
                    getNumericValueOfString(responseData.getCommon().getBATT().getDcPower()), Units.WATT_HOUR));

            publishChannelIfLinked(chan_current_battery_power_charge.getUID(),
                    new QuantityType<>(0.0, Units.WATT_HOUR));
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
    public void responseCallbackDaily(OverviewData overviewdata) {
        // TODO Auto-generated method stub
    }
}
