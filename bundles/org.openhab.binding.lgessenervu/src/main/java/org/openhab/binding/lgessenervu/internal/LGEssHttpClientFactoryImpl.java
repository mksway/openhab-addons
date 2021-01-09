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
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class to create Jetty web clients
 *
 * If accessing the powerrouter via LAN it is necessary to accept a self signed certificate.
 *
 *
 * The core HttpClientFactory does not allow self signed certificates.
 * Therefore this factory creates a HttpClient with SslContextFactory(true)
 * which will accept any ssl certificate.
 *
 * @author Martin Klama - Initial contribution
 */
@Component
@NonNullByDefault
public class LGEssHttpClientFactoryImpl implements LGEssHttpClientFactory {

    private final Logger logger = LoggerFactory.getLogger(LGEssHttpClientFactoryImpl.class);

    private @Nullable HttpClient httpClient;

    @Deactivate
    protected void deactivate() {
        if (httpClient != null) {
            try {
                httpClient.stop();
            } catch (Exception e) {
                logger.debug("error while stopping custom LGEssEnervu http client", e);
            }
            httpClient = null;
        }
    }

    @Override
    public @Nullable HttpClient getHttpClient() {
        initialize();
        return httpClient;
    }

    private synchronized void initialize() {
        if (httpClient == null) {
            httpClient = new HttpClient(new SslContextFactory.Client(true));
            try {
                httpClient.start();
            } catch (Exception e) {
                httpClient = null;
            }
        }
    }
}
