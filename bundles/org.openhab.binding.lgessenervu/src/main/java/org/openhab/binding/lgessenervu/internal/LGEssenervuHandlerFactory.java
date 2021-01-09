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

import static org.openhab.binding.lgessenervu.internal.LGEssEnervuBindingConstants.THING_TYPE_POWERROUTER;

import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.openhab.core.i18n.LocaleProvider;
import org.openhab.core.scheduler.CronScheduler;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.binding.BaseThingHandlerFactory;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link LGEssenervuHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Martin Klama - Initial contribution
 */
@NonNullByDefault
@Component(configurationPid = "binding.lgessenervu", service = ThingHandlerFactory.class)
public class LGEssenervuHandlerFactory extends BaseThingHandlerFactory {

    private static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Set.of(THING_TYPE_POWERROUTER);

    private final @Nullable HttpClient httpClient;
    private @Nullable CronScheduler cronscheduler;
    protected @NonNullByDefault({}) LocaleProvider localeProvider;

    @Activate
    public LGEssenervuHandlerFactory(@Reference LGEssHttpClientFactory httpClientFactory) {
        this.httpClient = httpClientFactory.getHttpClient();
    }

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (THING_TYPE_POWERROUTER.equals(thingTypeUID)) {
            return new LGEssenervuHandler(thing, httpClient, this.cronscheduler);
        }

        return null;
    }

    @Reference
    protected void setCronScheduler(CronScheduler scheduler) {
        this.cronscheduler = scheduler;
    }

    protected void unsetCronScheduler(CronScheduler scheduler) {
        this.cronscheduler = null;
    }
}
