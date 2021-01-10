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
package org.openhab.binding.lgessenervu.internal.discovery;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.jmdns.ServiceInfo;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.lgessenervu.internal.LGEssEnervuBindingConstants;
import org.openhab.core.config.discovery.DiscoveryResult;
import org.openhab.core.config.discovery.DiscoveryResultBuilder;
import org.openhab.core.config.discovery.mdns.MDNSDiscoveryParticipant;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.ThingUID;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link LGEssEnervuDiscoveryParticipant} is responsible for discovering devices via mdns
 * on the local network
 *
 * @author Martin Klama - Initial contribution
 */
@NonNullByDefault
@Component(service = MDNSDiscoveryParticipant.class)
public class LGEssEnervuDiscoveryParticipant implements MDNSDiscoveryParticipant {

    private final Logger logger = LoggerFactory.getLogger(LGEssEnervuDiscoveryParticipant.class);

    @Override
    public Set<ThingTypeUID> getSupportedThingTypeUIDs() {
        return LGEssEnervuBindingConstants.SUPPORTED_THING_TYPES_UIDS;
    }

    @Override
    public String getServiceType() {
        return "_pmsctrl._tcp.local.";
    }

    @Override
    public @Nullable DiscoveryResult createResult(ServiceInfo info) {
        logger.debug("ServiceInfo {}", info);
        String ipAddress = getIPAddress(info);
        if (ipAddress != null && isLGEssEnervuDevice(info.getName())) {
            logger.debug("LGEss device discovered: IP-Adress={}, name={}", ipAddress, info.getName());
            ThingUID uid = getThingUID(info);
            if (uid != null) {
                Map<String, Object> properties = new HashMap<>();
                properties.put("hostName", ipAddress);
                properties.put("dataSourceCloud", false);
                return DiscoveryResultBuilder.create(uid).withProperties(properties).withLabel(info.getName()).build();
            }
        }
        return null;
    }

    private boolean isLGEssEnervuDevice(String name) {

        if (name.toUpperCase().startsWith("LGE_ESS")) {
            return true;
        }

        return false;
    }

    private @Nullable String getIPAddress(ServiceInfo info) {
        InetAddress[] addresses = info.getInet4Addresses();
        if (addresses.length > 1) {
            logger.debug("LGEss device {} reported multiple addresses - using the first one! {}", info.getName(),
                    addresses);
        }
        return Stream.of(addresses).findFirst().map(InetAddress::getHostAddress).orElse(null);
    }

    @Override
    public @Nullable ThingUID getThingUID(ServiceInfo info) {
        logger.debug("ServiceInfo {}", info);
        String ipAddress = getIPAddress(info);
        if (ipAddress != null) {
            return new ThingUID(LGEssEnervuBindingConstants.THING_TYPE_POWERROUTER, ipAddress.replace(".", "_"));
        }
        return null;
    }
}
