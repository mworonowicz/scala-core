package org.afterhourz.scalastarter.core

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import monix.execution.Scheduler
import org.afterhourz.scalastarter.fares.FaresClient
import org.afterhourz.scalastarter.flights.FlightsContext
import org.afterhourz.scalastarter.flights.repository.{ConnectionRepository, ScheduleRepository}

/**
  * Main application context - here are all object instantiated and dependencies injected via constructor
  * ApplicationContext mix all contexts from all domain packages (each context in root domain package e.g. "FlightsContext" defined in "flights" package
  * All specific context have only non-implemented defs (just for encapsulation and contract fulfillment) which are overridden and implemented here as vals
  */
trait ApplicationContext extends FlightsContext {

  /**
    * These vals are for akka http part and for starting the server (in WebServer object)
    */
  implicit val system       = ActorSystem("scalastarter")
  implicit val materializer = ActorMaterializer()

  /**
    * This val is used for Task handling
    */
  implicit val scheduler: Scheduler = Scheduler(system.dispatcher)

  val appConfig                                  = ApplicationConfig.fromFile()
  val connectionRepository: ConnectionRepository = new ConnectionRepository
  val scheduleRepository: ScheduleRepository     = new ScheduleRepository
  val faresClient: FaresClient                   = new FaresClient
}
