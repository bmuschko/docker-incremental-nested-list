package com.bmuschko.gradle

class RunInstruction implements Instruction {
    private final String command

    RunInstruction(String command) {
        this.command = command
    }

    String getKeyword() {
        'RUN'
    }

    String build() {
        "$keyword $command".toString()
    }
}