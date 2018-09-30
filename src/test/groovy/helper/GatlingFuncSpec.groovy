package helper

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import spock.lang.Shared

abstract class GatlingFuncSpec extends GatlingSpec {

    static def GATLING_HOST_NAME_SYS_PROP = "-Dgatling.hostName=HTTP://COMPUTER-DATABASE.GATLING.IO"

    @Shared
    def pluginVersion = System.getProperty("com.github.lkishalmi.gatling.version")

    @Shared
    List<File> pluginClasspath

    def setupSpec() {
        assert pluginVersion != null : "Provide plugin version via `-Dcom.github.lkishalmi.gatling.version=`"

        def current = getClass().getResource("/").file
        pluginClasspath = [current.replace("classes/test", "classes/main"),
                           current.replace("classes/test", "resources/main")].collect { new File(it) }
    }

    def generateBuildScripts() {
        testProjectDir.newFile("build.gradle") << """
plugins {
    id 'com.github.lkishalmi.gatling' version '$pluginVersion'
}
repositories {
    jcenter()
}
dependencies {
    gatling group: 'commons-lang', name: 'commons-lang', version: '2.6'
}
"""

        testProjectDir.newFile("settings.gradle") << """
pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == 'com.github.lkishalmi') {
                useModule('com.github.lkishalmi.gatling:gradle-gatling-plugin:$pluginVersion')
            }
        }
    }
    repositories {
        maven {
            url "${new File(System.getProperty("user.home"), ".m2/repository").toURI()}"
        }
    }
}
"""
    }

    File prepareTest(boolean copyFiles = true) {
        createBuildFolder(copyFiles)
        generateBuildScripts()
    }

    BuildResult executeGradle(String task) {
        GradleRunner.create().forwardOutput()
            .withProjectDir(testProjectDir.getRoot())
            .withPluginClasspath(pluginClasspath)
            .withArguments("--stacktrace", GATLING_HOST_NAME_SYS_PROP, task)
            .withDebug(true)
            .build()
    }

    BuildResult executeGradle(String task,String gradleVersion) {
        GradleRunner.create().forwardOutput()
            .withProjectDir(testProjectDir.getRoot())
            .withPluginClasspath(pluginClasspath)
            .withArguments("--stacktrace", GATLING_HOST_NAME_SYS_PROP, task)
            .withDebug(true)
            .withGradleVersion(gradleVersion)
            .forwardOutput()
            .build()
    }
}
