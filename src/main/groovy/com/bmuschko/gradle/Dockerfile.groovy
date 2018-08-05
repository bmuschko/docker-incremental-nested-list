package com.bmuschko.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.Nested

class Dockerfile extends DefaultTask {
    @Nested
    List<Instruction> instructions = new ArrayList<Instruction>()

    @OutputFile
    File destFile = project.file("$project.buildDir/docker/Dockerfile")
    
    @TaskAction
    void create() {
        getDestFile().withWriter { out ->
            getInstructions().each { instruction ->
                out.println instruction.build()
            }
        }
    }
    
    void from(String baseImage) {
        instructions << new FromInstruction(baseImage)
    }
    
    void run(String command) {
        instructions << new RunInstruction(command)
    }
}