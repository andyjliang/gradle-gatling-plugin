package com.github.lkishalmi.gradle.gatling

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class GatlingInitTask extends DefaultTask {

    public GatlingInitTask() {
    }

    @TaskAction
    void createSampleProject() {
        if (!project.file("src/gatling").exists()) {
            def srcRoot = new File(project.projectDir, "src/gatling/simulations/com/sample")
            srcRoot.mkdirs()

            new File(srcRoot, "SampleSimulation.scala").text =
                    GatlingInitTask.class.getResource("/sample-project/SampleSimulation.scala").text

            def resRoot = new File(project.projectDir, "src/gatling/resources")
            resRoot.mkdirs()

            new File(resRoot, "search.csv").text =
                    GatlingInitTask.class.getResource("/sample-project/search.csv").text

            logger.info("Sample project created at `src/gatling`")

        } else {
            logger.warn("`src/gatling` already exists, skipping sample project creation")
        }
    }
}
