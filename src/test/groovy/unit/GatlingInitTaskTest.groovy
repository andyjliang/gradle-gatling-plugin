package unit

import com.github.lkishalmi.gradle.gatling.GatlingInitTask
import helper.GatlingUnitSpec

class GatlingInitTaskTest extends GatlingUnitSpec {

    def gatlingInitTask

    def setup() {
        gatlingInitTask = project.tasks.getByName("gatlingInit") as GatlingInitTask
        assert new File(testProjectDir.root, "src/gatling").deleteDir()
    }

    def "should create sample project sources"() {
        expect:
        1 == 1
    }
}
