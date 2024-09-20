/*
 *  Copyright (c) 2024 Bayerische Motoren Werke Aktiengesellschaft (BMW AG)
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Bayerische Motoren Werke Aktiengesellschaft (BMW AG) - initial API and implementation
 *
 */

plugins {
    `java-library`
}

dependencies {
    implementation(libs.edc.spi.core)
    implementation(libs.edc.spi.contract)
    implementation(libs.edc.spi.asset)
    implementation(libs.edc.spi.control.plane.spi)
}

// If the EDC Build Plugin is used, every module gets visited during Publishing by default.
// Single modules can be excluded by setting the "publish" flag to false:

edcBuild {
//    publish.set(false)
}