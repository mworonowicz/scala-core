package org.afterhourz.scalastarter.flights.model

case class ConnectionNotFound(departure: String) extends Throwable(s"Connection from $departure not found")
