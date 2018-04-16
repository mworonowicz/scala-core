package org.afterhourz.scalastarter.core

import akka.http.scaladsl.server.{Directives, Route}
import io.circe.Printer
import org.afterhourz.scalastarter.flights.controller.FlightsController

/**
  * All controllers need its own context received via self-type
  * and that's why Routing needs to have all application context applied as self-type as well (cake pattern)
  */
trait Routing extends FlightsController {
  applicationContext: ApplicationContext =>

  /**
    * All routes need to be combined here using super reference to each of the controllers
    */
  override def route: Route =
    super[FlightsController].route
}

/**
  * Controller extends Directives from akka http to have all needed methods for constructing routing
  * all concrete controllers need to override route field with its defined routing
  */
trait Controller extends Directives {

  implicit val  printer: Printer = Printer.noSpaces.copy(dropNullValues = true)

  def route: Route
}
