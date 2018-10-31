package com.bmuschko.gradle

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal

interface Instruction {
    @Internal String getKeyword()
    @Input String getBuild()
}