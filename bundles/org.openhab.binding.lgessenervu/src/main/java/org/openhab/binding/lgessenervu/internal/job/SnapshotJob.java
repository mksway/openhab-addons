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
import org.openhab.binding.lgessenervu.internal.client.LGEssClient;
import org.openhab.core.scheduler.SchedulerRunnable;

/**
 * The {@link SnapshotJob} retrieves current data every x seconds from either the cloud or the lan
 *
 * @author Martin Klama - Initial contribution
 */
@NonNullByDefault
public class SnapshotJob implements SchedulerRunnable, Runnable {

    private LGEssClient lgclient;

    public SnapshotJob(LGEssClient client) {
        this.lgclient = client;
    }

    @Override
    public void run() {
        lgclient.getCurrentData();
    }
}
