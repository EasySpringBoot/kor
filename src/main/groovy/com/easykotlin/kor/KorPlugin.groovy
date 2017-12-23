package com.easykotlin.kor

import org.gradle.api.Plugin
import org.gradle.api.Project

class KorPlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {

        String projectDir = project.projectDir.absolutePath

        project.extensions.create('korArgs', KorPluginExtension)

        project.task("korGenerate") << {
            println("Hello, Kor !")
            println("Group: $project.group")
            println("Name: $project.name")
            println("korArgs: $project.korArgs.entity")
            String packageName = "$project.group.$project.name"
            String entityName = project.korArgs.entity

            KorGenerateJava korGenerateJava = new KorGenerateJava()
            korGenerateJava.doGenerate(
                    projectDir,
                    packageName,
                    entityName
            )
        }

        project.task("korFront") << {
            NowaBuildJava nb = new NowaBuildJava()
            println("projectDir = $projectDir")
            nb.build(projectDir)
        }
    }
}


class KorPluginExtension {
    def entity = "Article"
}
