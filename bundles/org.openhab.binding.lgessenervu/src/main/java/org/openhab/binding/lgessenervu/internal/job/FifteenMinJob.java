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
package org.openhab.binding.lgessenervu.internal.job;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.lgessenervu.internal.client.LGCloudClient;
import org.openhab.core.scheduler.SchedulerRunnable;

/**
 * The {@link FifteenMinJob} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author SkyRaVeR - Initial contribution
 */
@NonNullByDefault
public class FifteenMinJob implements SchedulerRunnable, Runnable {
    private @Nullable LGCloudClient lgclient;

    public FifteenMinJob(@Nullable LGCloudClient client) {
        this.lgclient = client;
    }

    @Override
    public void run() {
        if (lgclient != null) {
            lgclient.get15MinOverview();
        }
    }
}
