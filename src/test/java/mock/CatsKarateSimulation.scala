package mock

import com.intuit.karate.gatling.PreDef._
import io.gatling.core.Predef._
import scala.concurrent.duration._

class CatsKarateSimulation extends Simulation {

  MockUtils.startServer()

  val feeder = Iterator.continually(Map("catName" -> MockUtils.getNextCatName))

  val protocol = karateProtocol(
    "/cats/{id}" -> Nil,
    "/cats" -> pauseFor("get" -> 15, "post" -> 25)
  )

  protocol.nameResolver = (req, ctx) => req.getHeader("karate-name")

  val create = scenario("create").feed(feeder).exec(karateFeature("classpath:mock/cats-create.feature"))
  val delete = scenario("delete").exec(karateFeature("classpath:mock/cats-delete.feature@name=delete"))
  val custom = scenario("custom").exec(karateFeature("classpath:mock/custom-rpc.feature"))
  val query001 = scenario("query001").exec(
    group("query-001") {
      exec(karateFeature("classpath:mock/query.feature"))
    })
  val query002 = scenario("query002").exec(    
    group("query-002") {
      exec(karateFeature("classpath:mock/query2.feature"))
    })

  setUp(
    // create.inject(rampUsers(10) during (5 seconds)).protocols(protocol),
    // delete.inject(rampUsers(5) during (5 seconds)).protocols(protocol),
    // custom.inject(rampUsers(10) during (5 seconds)).protocols(protocol)
    query001.inject(rampUsers(100) during (1 seconds)).protocols(protocol),
    query002.inject(rampUsers(100) during (1 seconds)).protocols(protocol)
  )

}
