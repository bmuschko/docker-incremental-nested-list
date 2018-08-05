package com.bmuschko.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class DockerPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.tasks.create('dockerfile', Dockerfile)
    }
}