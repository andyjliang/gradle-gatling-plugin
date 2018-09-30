package com.github.lkishalmi.gradle.gatling

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import static org.apache.commons.io.FileUtils.copyDirectory

class GatlingInitTask extends DefaultTask {

    public GatlingInitTask() {
    }

    @TaskAction
    void createSampleProject() {
        if (!project.file("src/gatling").exists()) {
            copyDirectory(new File(GatlingInitTask.class.getResource("/sample-project").file), project.projectDir)
            logger.info("Sample project created at `src/gatling`")
        } else {
            logger.warn("`src/gatling` already exists, skipping sample project creation")
        }
    }
}
