package com.bmuschko.gradle

import org.gradle.api.tasks.Input

interface Instruction {
    String getKeyword()
    @Input String build()
}