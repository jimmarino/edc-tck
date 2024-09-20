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
import org.eclipse.edc.connector.controlplane.contract.spi.types.negotiation.ContractNegotiation;
import org.eclipse.edc.spi.persistence.StateEntityStore;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Marker class for contract negotiation guard.
 */
public class ContractNegotiationGuard extends DelayedActionGuard<ContractNegotiation> implements ContractNegotiationPendingGuard {

    public ContractNegotiationGuard(Predicate<ContractNegotiation> filter,
                                    Consumer<ContractNegotiation> action,
                                    StateEntityStore<ContractNegotiation> store) {
        super(filter, action, store);
    }
}
