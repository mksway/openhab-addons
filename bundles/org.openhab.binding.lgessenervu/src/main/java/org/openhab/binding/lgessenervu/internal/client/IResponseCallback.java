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
import org.openhab.binding.lgessenervu.internal.LGEssEnervuBindingConstants.FailReason;

/**
 * The {@link ResponseCallback} interface is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Martin Klama - Initial contribution
 */
@NonNullByDefault
public interface IResponseCallback {

    void responseCallbackError(FailReason reason);

    void responseCallbackLoggedIn(boolean isloggedin, FailReason reason);

    void responseCallbackDaily(ResponseData responseData);

    void responseCallbackCurrentData(ResponseData responseData);
}
