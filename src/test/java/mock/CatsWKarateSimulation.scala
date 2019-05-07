package mock

import com.intuit.karate.gatling.PreDef._
import io.gatling.core.Predef._
import scala.concurrent.duration._

class CatsWKarateSimulation extends Simulation {

  MockUtils.startServer()

  val feeder = Iterator.continually(Map("catName" -> MockUtils.getNextCatName))

  val protocol = karateProtocol(
    "/cats/{id}" -> Nil,
    "/cats" -> pauseFor("get" -> 15, "post" -> 25)
  )

  protocol.nameResolver = (req, ctx) => req.getHeader("karate-name")

  val query003 = scenario("query003").exec(karateFeature("classpath:mock/query.feature"))
  val query004 = scenario("query004").exec(karateFeature("classpath:mock/query2.feature"))

  setUp(
    query003.inject(rampUsers(100) during (1 seconds)).protocols(protocol),
    query004.inject(rampUsers(100) during (1 seconds)).protocols(protocol)
  ).assertions(
    global.successfulRequests.percent.gt(10)
  )

}
