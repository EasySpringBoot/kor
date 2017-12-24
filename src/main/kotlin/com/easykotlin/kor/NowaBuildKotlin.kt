package com.easykotlin.kor

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.lang.StringBuilder

class NowaBuildKotlin {
    fun nowaBuild(projectDir: String) {

        val srcPath = File(projectDir + "/front/dist/")

        val templatesPath = projectDir + "/src/main/resources/templates/"
        val jsFile = projectDir + "/src/main/resources/static/js/"
        val cssPath = projectDir + "/src/main/resources/static/css/"

        val templatesDir = File(projectDir + "/src/main/resources/templates/")
        val cssDir = File(projectDir + "/src/main/resources/static/css/")
        val jsDir = File(projectDir + "/src/main/resources/static/js/")

        if (!templatesDir.exists()) templatesDir.mkdirs()
        if (!cssDir.exists()) cssDir.mkdirs()
        if (!jsDir.exists()) jsDir.mkdirs()

        srcPath.listFiles().forEach {
            val fileName = it.name
            when {
                isHtmlRes(fileName) -> {
                    println("Copy file: $fileName")

                    val htmlFile = File("$templatesPath$fileName")
                    it.copyTo(target = htmlFile, overwrite = true)
                    replaceJsCssSrc(htmlFile)
                }
                isJsRes(fileName) -> {
                    println("Copy file: $fileName")
                    it.copyTo(target = File("$jsFile$fileName"), overwrite = true)
                }
                isCssRes(fileName) -> {
                    println("Copy file: $fileName")
                    it.copyTo(target = File("$cssPath$fileName"), overwrite = true)
                }
            }
        }


    }

    private fun isHtmlRes(fileName: String) = fileName.endsWith(".html")

    private fun isJsRes(fileName: String) = fileName.endsWith(".js")

    private fun isCssRes(fileName: String) =
        fileName.endsWith(".css")
                || fileName.endsWith(".jpg")
                || fileName.endsWith(".png")
                || fileName.endsWith(".jpeg")


    fun replaceJsCssSrc(htmlFile: File) {
        val oldJsSrc = """<script src="/"""
        val oldJsSrcParticular = """<script src="//"""
        val newJsSrc = """<script src="/js/"""

        val oldCssSrc = """<link rel="stylesheet" href="/"""
        val newCssSrc = """<link rel="stylesheet" href="/css/"""

        var lines = StringBuilder()
        htmlFile.readLines().forEach {
            var line = it
            if (line.contains(oldJsSrc) && !line.contains(oldJsSrcParticular)) {
                line = line.replace(oldJsSrc, newJsSrc)
            } else if (line.contains(oldCssSrc)) {
                line = line.replace(oldCssSrc, newCssSrc)
            }

            lines.append(line + "\n")
        }

        htmlFile.writeText(lines.toString())
    }


    /**
    在 Kotlin 中，目前还没有对 String 类和 Process 扩展这样的函数。其实扩展这样的函数非常简单。我们完全可以自己扩展。
    首先，我们来扩展 String 的 execute() 函数。
     */

    fun String.execute(): Process {
        val runtime = Runtime.getRuntime()
        return runtime.exec(this)
    }

    /** 然后，我们来给 Process 类扩展一个 text函数。 */

    fun Process.text(): String {
        var output = ""
        //  输出 Shell 执行的结果
        val inputStream = this.inputStream
        val isr = InputStreamReader(inputStream)
        val reader = BufferedReader(isr)
        var line: String? = ""
        while (line != null) {
            line = reader.readLine()
            output += line + "\n"
        }
        return output
    }
}


