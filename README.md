# Kotlin +  Groovy  + Java  混合编程 实现 Gradle 插件

> kor



使用 Kotlin 自己开发一个 Gradle 插件


![image.png](http://upload-images.jianshu.io/upload_images/1233356-d91481006ee8ec60.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![image.png](http://upload-images.jianshu.io/upload_images/1233356-c0f80ac0afd80a73.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![image.png](http://upload-images.jianshu.io/upload_images/1233356-3c421dbcc8ee2bba.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![image.png](http://upload-images.jianshu.io/upload_images/1233356-673bd781f0cfe277.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![image.png](http://upload-images.jianshu.io/upload_images/1233356-a5fa72d06c5f510d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)





build.gradle

```groovy
group 'com.easykotlin.plugin'
version '1.0-SNAPSHOT'

buildscript {
    ext.kotlin_version = '1.2.0'

    repositories {
        mavenCentral()
    }
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}

apply plugin: 'groovy'
apply plugin: 'java'
apply plugin: 'kotlin'
apply plugin: 'maven'

sourceCompatibility = 1.8

repositories {
    mavenCentral()
}

dependencies {
    compile "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
    compile 'org.codehaus.groovy:groovy-all:2.3.11'
    testCompile group: 'junit', name: 'junit', version: '4.12'
    compile gradleApi()
}

compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}
compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
}
```

![image.png](http://upload-images.jianshu.io/upload_images/1233356-f89a75db35cd1390.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


其中，compile gradleApi() 是使用 Gradle 的 API 依赖。


配置上传到 maven 仓库，这里我们配置上传至本机的目录下：

```
apply plugin: 'maven'

uploadArchives {
    repositories {
        mavenDeployer {
            repository(url: uri('/Users/jack/.m2/repository'))
        }
    }
}
```

点击右侧工具栏的： upload > uploadArchives

![image.png](http://upload-images.jianshu.io/upload_images/1233356-0a396d1465084838.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

执行日志：

```
23:41:48: Executing external task 'uploadArchives'...
:compileKotlin UP-TO-DATE
:compileJava NO-SOURCE
:processResources UP-TO-DATE
:classes UP-TO-DATE
:jar UP-TO-DATE
:uploadArchives

BUILD SUCCESSFUL in 0s
4 actionable tasks: 1 executed, 3 up-to-date
23:41:49: External task execution finished 'uploadArchives'.

```


看一下本机的上传情况：

```
~/.m2/repository/com/easykotlin/plugin/kor$ tree 
.
├── 1.0-SNAPSHOT
│   ├── kor-1.0-20171221.172201-1.jar
│   ├── kor-1.0-20171221.172201-1.jar.md5
│   ├── kor-1.0-20171221.172201-1.jar.sha1
│   ├── kor-1.0-20171221.172201-1.pom
│   ├── kor-1.0-20171221.172201-1.pom.md5
│   ├── kor-1.0-20171221.172201-1.pom.sha1
│   ├── kor-1.0-20171221.172210-2.jar
│   ├── kor-1.0-20171221.172210-2.jar.md5
│   ├── kor-1.0-20171221.172210-2.jar.sha1
│   ├── kor-1.0-20171221.172210-2.pom
│   ├── kor-1.0-20171221.172210-2.pom.md5
│   ├── kor-1.0-20171221.172210-2.pom.sha1
│   ├── maven-metadata-remote.xml
│   ├── maven-metadata-remote.xml.sha1
│   ├── maven-metadata.xml
│   ├── maven-metadata.xml.md5
│   ├── maven-metadata.xml.sha1
│   └── resolver-status.properties
├── maven-metadata-remote.xml
├── maven-metadata-remote.xml.sha1
├── maven-metadata.xml
├── maven-metadata.xml.md5
├── maven-metadata.xml.sha1
└── resolver-status.properties

1 directory, 24 files
```


其中，kor-1.0-20171221.154128-1.pom  文件的内容是：


```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.easykotlin.plugin</groupId>
  <artifactId>kor</artifactId>
  <version>1.0-SNAPSHOT</version>
  <dependencies>
    <dependency>
      <groupId>org.jetbrains.kotlin</groupId>
      <artifactId>kotlin-stdlib-jdk8</artifactId>
      <version>1.2.0</version>
      <scope>compile</scope>
    </dependency>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>

```

新建Gradle 插件的执行逻辑的实现类

![image.png](http://upload-images.jianshu.io/upload_images/1233356-305000e10a00b7e3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![image.png](http://upload-images.jianshu.io/upload_images/1233356-26ceda25ef21e07a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![image.png](http://upload-images.jianshu.io/upload_images/1233356-89a3e0a882b8501d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



实现代码编写：

![image.png](http://upload-images.jianshu.io/upload_images/1233356-0d82a808039b6108.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![image.png](http://upload-images.jianshu.io/upload_images/1233356-5d16f81c4dea5c63.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


```groovy
package com.easykotlin.kor

import org.gradle.api.Plugin
import org.gradle.api.Project

class KorPlugin implements Plugin<Project>{


    @Override
    void apply(Project project) {

    }
}

```

具体实现代码：

```groovy
package com.easykotlin.kor

import org.gradle.api.Plugin
import org.gradle.api.Project

class KorPlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {

        project.task("kor") << {
            println("Hello, Kor !")
            println(project.group.toString())
            println(project.artifacts.toString())
        }

        project.task("nowabuild") << {
            NowaBuildJava nb = new NowaBuildJava()
            String projectDir = project.projectDir.absolutePath
            println("projectDir = $projectDir")
            nb.nowaBuild(projectDir)
        }
    }
}

```



kor.properties

```
implementation-class=com.easykotlin.kor.KorPlugin
```

![image.png](http://upload-images.jianshu.io/upload_images/1233356-ef1a10152f70cd04.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



build.gradle

```groovy
group 'com.easykotlin.plugin'
version '1.0-SNAPSHOT'

buildscript {
    ext.kotlin_version = '1.2.0'

    repositories {
        mavenCentral()
    }
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}

apply plugin: 'groovy'
apply plugin: 'java'
apply plugin: 'kotlin'
apply plugin: 'maven'

sourceCompatibility = 1.8

repositories {
    mavenCentral()
}

dependencies {
    compile "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
//    compile 'org.codehaus.groovy:groovy-all:2.4.11'
    testCompile group: 'junit', name: 'junit', version: '4.12'
    compile gradleApi()
}

compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}
compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
}


uploadArchives {
    repositories {
        mavenDeployer {
            repository(url: uri('/Users/jack/.m2/repository'))
        }
    }
}

```



![image.png](http://upload-images.jianshu.io/upload_images/1233356-2110c124ca98c133.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)





然后，我们在另外项目 reakt 中使用刚才开发的插件：

build.gradle

```groovy
buildscript {
    ext {
        kotlinVersion = '1.2.0'
        springBootVersion = '2.0.0.M7'
    }
    repositories {
        mavenLocal()
        mavenCentral()
        maven { url "https://repo.spring.io/snapshot" }
        maven { url "https://repo.spring.io/milestone" }
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
        classpath("org.jetbrains.kotlin:kotlin-allopen:${kotlinVersion}")
        classpath('com.easykotlin.plugin:kor:1.0-SNAPSHOT')
    }
}

apply plugin: 'com.easykotlin.kor'

......

```



![image.png](http://upload-images.jianshu.io/upload_images/1233356-aede763558909334.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)




![image.png](http://upload-images.jianshu.io/upload_images/1233356-100fd883a14663f6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



---

## 本章工程源代码

Gradle 插件工程源代码：

https://github.com/EasySpringBoot/kor

使用插件的工程源代码：

https://github.com/EasyKotlin/reakt




# 新书上架：《Spring Boot 开发实战》

> — 基于 Kotlin + Gradle + Spring Boot 2.0 的企业级服务端开发实战



#### [京东下单链接](https://item.jd.com/31178320122.html)

https://item.jd.com/31178320122.html

#### [天猫下单链接](https://detail.tmall.com/item.htm?id=574928877711)

https://detail.tmall.com/item.htm?id=574928877711

![](https://upload-images.jianshu.io/upload_images/1233356-596a64de8adf2b27.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

