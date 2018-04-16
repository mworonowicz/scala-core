package org.afterhourz.scalastarter.flights.model

import java.time.{LocalDate, LocalDateTime}

case class Connection(departure: String, arrival: String)

case class Schedule(connection: Connection, date: LocalDateTime)

object Airports {

  val Wroclaw = "Wroclaw"
  val Dublin  = "Dublin"
}


case class OfferSearch(departure: String, arrival: String, startDate: LocalDate, endDate: LocalDate, quantity: Int)

case class Offer(schedule: Schedule, price: Double)


case class FlightSearchResult(offers:Option[List[Offer]] = None, error : Option[String] = None)


