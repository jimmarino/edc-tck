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

package org.eclipse.edc.tck.dsp.data;

import org.eclipse.edc.connector.controlplane.asset.spi.domain.Asset;
import org.eclipse.edc.connector.controlplane.contract.spi.types.negotiation.ContractNegotiation;
import org.eclipse.edc.connector.controlplane.contract.spi.types.offer.ContractDefinition;
import org.eclipse.edc.connector.controlplane.policy.spi.PolicyDefinition;
import org.eclipse.edc.policy.model.Policy;
import org.eclipse.edc.spi.types.domain.DataAddress;
import org.eclipse.edc.tck.dsp.recorder.StepRecorder;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Assembles data for the TCK scenarios.
 */
public class DataAssembly {
    private static final Set<String> ASSET_IDS = Set.of("ACN0101", "ACN0102", "ACN0103");
    private static final String POLICY_ID = "P123";
    private static final String CONTRACT_DEFINITION_ID = "CD123";

    public static Set<Asset> createAssets() {
        return ASSET_IDS.stream().map(DataAssembly::createAsset).collect(toSet());
    }

    public static Set<PolicyDefinition> createPolicyDefinitions() {
        return Set.of(PolicyDefinition.Builder.newInstance()
                .id(POLICY_ID)
                .policy(Policy.Builder.newInstance().build())
                .build());
    }

    public static Set<ContractDefinition> createContractDefinitions() {
        return Set.of(ContractDefinition.Builder.newInstance()
                .id(CONTRACT_DEFINITION_ID)
                .accessPolicyId(POLICY_ID)
                .contractPolicyId(POLICY_ID)
                .build());
    }

    public static StepRecorder<ContractNegotiation> createNegotiationRecorder() {
        var recorder = new StepRecorder<ContractNegotiation>();
        recorder.record("ACN0101", ContractNegotiation::transitionOffering);

        recorder.record("ACN0102", ContractNegotiation::transitionOffering)
                .record("ACN0102", ContractNegotiation::transitionTerminating);

        return recorder.repeat();
    }


    private DataAssembly() {
    }

    private static Asset createAsset(String id) {
        return Asset.Builder.newInstance()
                .id(id)
                .dataAddress(DataAddress.Builder.newInstance().type("HTTP").build())
                .build();
    }
}
