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
package org.openhab.binding.lgessenervu.internal.client.gson.lan;

import org.eclipse.jdt.annotation.NonNullByDefault;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The {@link Statistics} is an auto generated DTO
 *
 * @author Martin Klama - Initial contribution
 *
 */
@NonNullByDefault
public class Statistics {

    @SerializedName("statistics")
    @Expose
    private Stats statistics;
    @SerializedName("direction")
    @Expose
    private Direction direction;
    @SerializedName("operation")
    @Expose
    private Operation operation;
    @SerializedName("wintermode")
    @Expose
    private Wintermode wintermode;
    @SerializedName("pcs_fault")
    @Expose
    private PcsFault pcsFault;

    /**
     * No args constructor for use in serialization
     *
     */
    public Statistics() {
        this.statistics = new Stats();
        this.direction = new Direction();
        this.operation = new Operation();
        this.wintermode = new Wintermode();
        this.pcsFault = new PcsFault();
    }

    /**
     *
     * @param pcsFault
     * @param wintermode
     * @param operation
     * @param statistics
     * @param direction
     */
    public Statistics(Stats statistics, Direction direction, Operation operation, Wintermode wintermode,
            PcsFault pcsFault) {
        super();
        this.statistics = statistics;
        this.direction = direction;
        this.operation = operation;
        this.wintermode = wintermode;
        this.pcsFault = pcsFault;
    }

    public Stats getStatistics() {
        return statistics;
    }

    public void setStatistics(Stats statistics) {
        this.statistics = statistics;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Wintermode getWintermode() {
        return wintermode;
    }

    public void setWintermode(Wintermode wintermode) {
        this.wintermode = wintermode;
    }

    public PcsFault getPcsFault() {
        return pcsFault;
    }

    public void setPcsFault(PcsFault pcsFault) {
        this.pcsFault = pcsFault;
    }
}
