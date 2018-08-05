package com.bmuschko.gradle

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class DockerPluginFunctionalTest extends Specification {
    @Rule
    TemporaryFolder temporaryFolder = new TemporaryFolder()
    
    File buildFile
    File projectDir
    
    def setup() {
        buildFile = temporaryFolder.newFile('build.gradle')
        projectDir = temporaryFolder.root
    }
    
    private BuildResult build(String... arguments) {
        createAndConfigureGradleRunner(arguments).build()
    }

    private GradleRunner createAndConfigureGradleRunner(String... arguments) { GradleRunner.create().withProjectDir(projectDir).withArguments(arguments).withPluginClasspath().forwardOutput()
    }
    
    def "Dockerfile task is incremental"() {
        given:
        buildFile << """
            plugins {
                id 'com.bmuschko.docker'
            }
            
            ext.baseImage = project.hasProperty('baseImage') ? project.getProperty('baseImage') : 'openjdk'
            println "---> \$baseImage"

            dockerfile {
                from baseImage
                run '/bin/bash'
            }
        """

        when:
        def result = build('dockerfile')

        then:
        result.task(':dockerfile').outcome == TaskOutcome.SUCCESS

        when:
        result = build('dockerfile')

        then:
        result.task(':dockerfile').outcome == TaskOutcome.UP_TO_DATE

        when:
        result = build('dockerfile', '-PbaseImage=other')

        then:
        result.task(':dockerfile').outcome == TaskOutcome.SUCCESS
    }
}