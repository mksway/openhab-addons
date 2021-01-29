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
package org.openhab.binding.lgessenervu.internal.client;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.lgessenervu.internal.LGEssEnervuBindingConstants.DataSource;
import org.openhab.binding.lgessenervu.internal.client.gson.lan.Common;
import org.openhab.binding.lgessenervu.internal.client.gson.lan.Statistics;

/**
 * Auto generated class
 *
 * @author Martin Klama - Initial contribution
 *
 */
@NonNullByDefault
public class ResponseData {

    private Common common;
    private Statistics stats;
    private DataSource datasrc;
    private boolean success = true;

    public ResponseData() {
        common = new Common();
        stats = new Statistics();
        datasrc = DataSource.NONE;
    }

    public ResponseData(Common common, Statistics stats, DataSource ds) {
        this.common = common;
        this.stats = stats;
        this.datasrc = ds;
    }

    public Common getCommon() {
        return common;
    }

    public void setCommon(Common common) {
        this.common = common;
    }

    public Statistics getStats() {
        return stats;
    }

    public void setStats(Statistics stats) {
        this.stats = stats;
    }

    public DataSource getDatasource() {
        return this.datasrc;
    }

    public void setDatasource(DataSource ds) {
        this.datasrc = ds;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
