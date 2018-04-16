package org.afterhourz.scalastarter.flights

import akka.stream.Materializer
import monix.execution.Scheduler
import org.afterhourz.scalastarter.fares.FaresClient
import org.afterhourz.scalastarter.flights.repository.{ConnectionRepository, ScheduleRepository}

trait FlightsContext {
  implicit val materializer : Materializer
  implicit val scheduler : Scheduler
  def connectionRepository: ConnectionRepository
  def scheduleRepository: ScheduleRepository
  def faresClient: FaresClient
}
