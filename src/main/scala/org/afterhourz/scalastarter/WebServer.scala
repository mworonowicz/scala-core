package org.afterhourz.scalastarter

import akka.http.scaladsl.Http
import org.afterhourz.scalastarter.core.{ApplicationContext, Routing}


/**
  * Main object - http server is started here, all routing is mixed together with all application contexts
  * Routing and ApplicationContext and other core configurations are defined in "core" package
  * App trait from scala  package - all body of WebServer object is executed as it would be in a main method
  */
object WebServer extends App with Routing with ApplicationContext {

  Http().bindAndHandle(route, appConfig.host, appConfig.port)
}
