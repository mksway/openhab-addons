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
import org.openhab.binding.lgessenervu.internal.client.LGEssClient;
import org.openhab.core.scheduler.SchedulerRunnable;

/**
 * The {@link FifteenMinJob} is scheduled every 15 minutes and retrieves accumulated data like day/monthly stats from
 * the cloud.
 *
 * @author Martin Klama - Initial contribution
 */
@NonNullByDefault
public class FifteenMinJob implements SchedulerRunnable, Runnable {
    private @Nullable LGEssClient lgclient;

    public FifteenMinJob(LGEssClient lgessClient) {
        this.lgclient = lgessClient;
    }

    @Override
    public void run() {
        if (lgclient != null) {
            lgclient.get15MinOverview();
        }
    }
}
