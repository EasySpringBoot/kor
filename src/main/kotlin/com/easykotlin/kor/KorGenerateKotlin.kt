package com.easykotlin.kor

import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class KorGenerateKotlin {

    /**
    // /Users/jack/easykotlin/reakt/src/main/kotlin/com/easykotlin/reakt/entity/Role.kt
    // projectDir: /Users/jack/easykotlin/reakt
    // /src/main/kotlin/
    //  packageName : com.easykotlin.reakt
    //  entityName : Book
     */

    fun doGenerate(projectDir: String, packageName: String, entityName: String) {
        val packagePath = getPackagePath(packageName)

        val targetEntityDir = "$projectDir/src/main/kotlin/$packagePath/entity/"
        val targetDaoDir = "$projectDir/src/main/kotlin/$packagePath/dao/"
        val targetControllerDir = "$projectDir/src/main/kotlin/$packagePath/controller/"

        generateEntity(targetEntityDir, entityName, packageName)
        generateDao(targetDaoDir, entityName, packageName)
        generateController(targetControllerDir, entityName, packageName)
    }


    private fun generateEntity(targetEntityDir: String, entityName: String, packageName: String) {

        val f = File(targetEntityDir)

        if (!f.exists()) {
            println("Create Dir: $targetEntityDir")
            f.mkdirs()
        }

        val srcFileName = "$targetEntityDir$entityName.kt"

        val srcFile = File(srcFileName)
        if (!srcFile.exists()) {
            println("Create File: $srcFileName")
            srcFile.createNewFile()
        }
        val text = entityTemplate(entityName, packageName)
        println("Write Text:\n $text")
        srcFile.writeText(text)
    }


    private fun generateDao(targetDaoDir: String, entityName: String, packageName: String) {

        val f = File(targetDaoDir)

        if (!f.exists()) {
            println("Create Dir: $targetDaoDir")
            f.mkdirs()
        }

        val srcFileName = "$targetDaoDir${entityName}Dao.kt"

        val srcFile = File(srcFileName)
        if (!srcFile.exists()) {
            println("Create File: $srcFileName")
            srcFile.createNewFile()
        }
        val text = daoTemplate(entityName, packageName)
        println("Write Text:\n $text")
        srcFile.writeText(text)

    }

    private fun generateController(targetControllerDir: String, entityName: String, packageName: String) {
        val f = File(targetControllerDir)

        if (!f.exists()) {
            println("Create Dir: $targetControllerDir")
            f.mkdirs()
        }

        val srcFileName = "$targetControllerDir${entityName}Controller.kt"

        val srcFile = File(srcFileName)
        if (!srcFile.exists()) {
            println("Create File: $srcFileName")
            srcFile.createNewFile()
        }

        val text = controllerTemplate(entityName, packageName)
        println("Write Text:\n $text")
        srcFile.writeText(text)

    }


    /**
     * com.easykotlin.reakt --> com/easykotlin/reakt
     */
    private fun getPackagePath(packageName: String): String {
        return packageName.replace(".", "/")
    }


    private fun entityTemplate(entityName: String, packageName: String): String {
        val datestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        return """
package ${packageName}.entity

import javax.persistence.*
import java.util.Date


/**
 * Created by Kor on ${datestamp}.
 */
@Entity
class ${entityName} {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1
    var gmtCreate = Date()
    var gmtModify = Date()
    var isDeleted = 0
}
"""

    }

    private fun controllerTemplate(entityName: String, packageName: String): String {
        val datestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        return """
package ${packageName}.controller

import ${packageName}.dao.${entityName}Dao
import ${packageName}.entity.${entityName}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import javax.servlet.http.HttpServletRequest

/**
 * Created by Kor on ${datestamp}.
 */
@RestController
@RequestMapping("/${entityName.toLowerCase()}")
class ${entityName}Controller {

    @Autowired lateinit var ${entityName}Dao: ${entityName}Dao

    @GetMapping(value = ["/"])
    fun ${entityName.toLowerCase()}(request: HttpServletRequest): List<${entityName}>{
        return ${entityName}Dao.findAll()
    }

    @GetMapping(value = ["/{id}"])
    fun getOne(@PathVariable("id") id:Long): ${entityName} {
        return ${entityName}Dao.getOne(id)
    }
}
"""

    }

    private fun daoTemplate(entityName: String, packageName: String): String {
        val datestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        return """
package ${packageName}.dao

import ${packageName}.entity.${entityName}
import org.springframework.data.jpa.repository.JpaRepository


/**
 * Created by Kor on ${datestamp}.
 */
interface ${entityName}Dao : JpaRepository<${entityName}, Long> {

}
"""

    }
}
