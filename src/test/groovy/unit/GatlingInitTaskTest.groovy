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
        when:
        gatlingInitTask.execute()
        then:
        new File(testProjectDir.root, "src/gatling/simulations/com/sample/SampleSimulation.scala").exists()
        and:
        new File(testProjectDir.root, "src/gatling/resources/search.csv").exists()
    }

    def "should not create sample project sources if src/gatling already exists"() {
        given:
        def f = new File(testProjectDir.root, "src/gatling/resources")
        assert f.mkdirs()
        new File(f, "1.txt").text = "some text"
        when:
        gatlingInitTask.execute()
        then:
        !new File(testProjectDir.root, "src/gatling/simulations/com/sample/SampleSimulation.scala").exists()
        and:
        !new File(testProjectDir.root, "src/gatling/resources/search.csv").exists()
    }
}
