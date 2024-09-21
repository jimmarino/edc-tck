/*
 *  Copyright (c) 2024 Metaform Systems, Inc.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Metaform Systems, Inc. - initial API and implementation
 *
 */

package org.eclipse.edc.tck.dsp.guard;

import org.eclipse.edc.connector.controlplane.contract.spi.negotiation.ContractNegotiationPendingGuard;
import org.eclipse.edc.connector.controlplane.contract.spi.negotiation.store.ContractNegotiationStore;
import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.runtime.metamodel.annotation.Provider;
import org.eclipse.edc.spi.system.ServiceExtension;

import java.util.Set;

import static org.eclipse.edc.connector.controlplane.contract.spi.types.negotiation.ContractNegotiationStates.AGREEING;
import static org.eclipse.edc.connector.controlplane.contract.spi.types.negotiation.ContractNegotiationStates.FINALIZING;
import static org.eclipse.edc.connector.controlplane.contract.spi.types.negotiation.ContractNegotiationStates.OFFERING;
import static org.eclipse.edc.connector.controlplane.contract.spi.types.negotiation.ContractNegotiationStates.TERMINATING;
import static org.eclipse.edc.tck.dsp.data.DataAssembly.createNegotiationRecorder;

/**
 * Loads the transition guard.
 */
public class TckGuardExtension implements ServiceExtension {
    private static final String NAME = "DSP TCK Guard";

    // the states to not apply the guard to - i.e. to allow automatic transitions by the contract negotiation manager
    private static final Set<Integer> AUTOMATIC_STATES = Set.of(OFFERING.code(),
            AGREEING.code(),
            TERMINATING.code(),
            FINALIZING.code());

    private ContractNegotiationGuard negotiationGuard;

    @Inject
    private ContractNegotiationStore store;

    @Override
    public String name() {
        return NAME;
    }

    @Provider
    public ContractNegotiationPendingGuard negotiationGuard() {
        var recorder = createNegotiationRecorder();
        negotiationGuard = new ContractNegotiationGuard(cn -> !AUTOMATIC_STATES.contains(cn.getState()),
                cn -> recorder.playNext(cn.getContractOffers().get(0).getAssetId(), cn),
                store);
        return negotiationGuard;
    }

    @Override
    public void prepare() {
        if (negotiationGuard != null) {
            negotiationGuard.start();
        }
    }

    @Override
    public void shutdown() {
        if (negotiationGuard != null) {
            negotiationGuard.stop();
        }
    }
}
