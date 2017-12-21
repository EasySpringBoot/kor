package com.easykotlin.kor

import org.gradle.api.Plugin
import org.gradle.api.Project

class KorPlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {

        project.extensions.create('korArgs', KorPluginExtension)

        project.task("kor") << {
            println("Hello, Kor !")
            println("Group: $project.group")
            println("Name: $project.name")
            println("korArgs: $project.korArgs.entity")
        }

        project.task("nowabuild") << {
            NowaBuildJava nb = new NowaBuildJava()
            String projectDir = project.projectDir.absolutePath
            println("projectDir = $projectDir")
            nb.build(projectDir)
        }
    }
}


class KorPluginExtension {
    def entity = "Article"
}
