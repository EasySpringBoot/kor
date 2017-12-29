package com.easykotlin.kor

import java.io.File

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
                    val componentName = fileName.substring(0, fileName.lastIndexOf(".html"))
                    val htmlText = htmlFileTemplate(component = componentName)
                    it.writeText(htmlText)

                    val htmlFile = File("$templatesPath$fileName")
                    it.copyTo(target = htmlFile, overwrite = true)
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


    fun htmlFileTemplate(component: String): String {
        return """<!-- Created By Kor Mon Dec 25 00:00:11 CST 2017, author: 东海陈光剑 -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${component}</title>

    <!-- 兼容包资源加载 -->
    <!--[if lte IE 9]>
    <script src="//g.alicdn.com/platform/c/??es5-shim/4.1.12/es5-shim.min.js,es5-shim/4.1.12/es5-sham.min.js,console-polyfill/0.2.1/index.min.js,respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <script src="//g.alicdn.com/platform/c/??lodash/4.6.1/lodash.min.js,react/0.14.2/react-with-addons.min.js,react/0.14.2/react-dom.min.js,lie/3.0.2/dist/lie.polyfill.min.js,react-router/2.8.1/umd/ReactRouter.min.js"></script>


    <!-- 使用 Uxcore.Mention 时，需要引入 rangy -->
    <script src="//g.alicdn.com/platform/c/rangy/1.3.0/rangy-core.min.js"></script>
    <!-- 使用 Uxcore.Tinymce 或 Uxcore.Form.EditorFormField 时，需要引入 tinymce -->
    <script src="//g.alicdn.com/platform/c/tinymce/4.3.12/tinymce.min.js"></script>

    <!-- Nowa Server 注入位置 -->
    <!-- nowa server injects -->

    <!-- 项目样式资源加载 -->
    <link rel="stylesheet" href="/css/app.css">
    <link rel="stylesheet" href="/css/${component}.css">
</head>
<body>
<div id="App"></div>
<!-- 项目脚本资源加载 -->
<script src="/js/app.js"></script>
<script src="/js/${component}.js"></script>
</body>
</html>"""

    }

}


