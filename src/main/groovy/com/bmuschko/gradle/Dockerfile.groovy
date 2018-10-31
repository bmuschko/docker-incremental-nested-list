package com.bmuschko.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Nested
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

class Dockerfile extends DefaultTask {
    @Nested
    final ListProperty<Instruction> instructions

    @OutputFile
    @PathSensitive(PathSensitivity.RELATIVE)
    final RegularFileProperty destFile

    Dockerfile() {
        instructions = project.objects.listProperty(Instruction)
        destFile = project.objects.fileProperty()
        destFile.set(project.layout.buildDirectory.file('docker/Dockerfile'))
    }
    
    @TaskAction
    void create() {
        destFile.get().asFile.withWriter { out ->
            instructions.get().forEach() { instruction ->
                out.println instruction.getBuild()
            }
        }
    }
    
    void from(String baseImage) {
        instructions.add(new FromInstruction(baseImage))
    }
    
    void run(String command) {
        instructions.add(new RunInstruction(command))
    }
}