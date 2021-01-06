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

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * The {@link LGEssenervuConfiguration} class contains fields mapping thing configuration parameters.
 *
 * @author Martin Klama - Initial contribution
 */
@NonNullByDefault
public class LGEssenervuConfiguration {

    /**
     * Hostname or IP address of the device if LAN-Mode is choosen
     */
    public String hostName = "";
    /**
     * Refresh interval data is pulled
     */
    public int refreshInterval = 60;
    /**
     * Timeout when a connection timeout is considered
     */
    public int timeout = 5;
    /**
     * username/email for login to the cloud-api
     */
    public String user = "";

    /**
     * password for the cloud-api
     */
    public String passwordCloud = "";
    /**
     * password for the local-api
     */
    public String passwordLocal = "";

    /**
     * Source of the data (cloud api, lan api)
     */
    public boolean dataSourceCloud = false;
    /**
     * username/email for login to the cloud-api
     */
    public Double co2Factor = 0.0;
    public Double kwhPrice = 0.0;
    public Double kwhPricesell = 0.0;

}
