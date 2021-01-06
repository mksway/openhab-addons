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
 * Some Daikin controllers communicate via https using a custom common name,
 * and they are accessed using an ip address.
 *
 * The core HttpClientFactory creates a HttpClient that will fail because of this.
 * This factory creates a HttpClient with SslContextFactory(true)
 * which will accept any ssl certificate without checking for common name mismatches.
 *
 * @author Jimmy Tanagra - Initial contribution
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
                logger.debug("LGEssEnervu http client stopped");
            } catch (Exception e) {
                logger.debug("error while stopping LGEssEnervu http client", e);
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
                logger.debug("LGEssEnervu http client started");
            } catch (Exception e) {
                logger.warn("Could not start LGEssEnervu http client", e);
                httpClient = null;
            }
        }
    }
}