package com.bmuschko.gradle

class FromInstruction implements Instruction {
    private final String baseImage

    FromInstruction(String baseImage) {
        this.baseImage = baseImage
    }

    String getKeyword() {
        'FROM'
    }

    String build() {
        "$keyword $baseImage".toString()
    }
}