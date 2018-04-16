package org.afterhourz.scalastarter.flights.service

import org.afterhourz.scalastarter.core.Stack._readerFC
import org.afterhourz.scalastarter.fares.FaresClient
import org.afterhourz.scalastarter.flights.FlightsContext
import org.afterhourz.scalastarter.flights.model._
import org.atnos.eff.addon.monix.task._
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._
import org.atnos.eff.{Eff, Fx}

object OfferService {

  def findOffers[R: _readerFC : _task : _throwableEither](offerSearch: OfferSearch): Eff[R, List[Offer]] =
    for {
      ctx            <- ask[R, FlightsContext]
      allConnections <- ctx.connectionRepository.find(offerSearch.departure)
      connection     <- findConnection(allConnections, offerSearch)
      allSchedules   <- ctx.scheduleRepository.getSchedule(offerSearch.startDate, offerSearch.endDate)
      schedules      <- findSchedules(allSchedules, connection)
      offers         <- findOffers(ctx.faresClient, schedules, offerSearch.quantity)
    } yield offers

  private def findConnection[R: _throwableEither](allConnections: List[Connection], offerSearch: OfferSearch): Eff[R, Connection] =
    fromEither(allConnections.find(_.arrival == offerSearch.arrival).toRight[Throwable](ConnectionNotFound(offerSearch.arrival)))

  private def findSchedules[R](allSchedules: List[Schedule], connection: Connection): Eff[R, List[Schedule]] =
    Eff.pure(allSchedules.filter(_.connection == connection))

  private def findOffers[R: _task](faresClient: FaresClient, schedules: List[Schedule], quantity: Int): Eff[R, List[Offer]] = {
    type S = Fx.append[Fx.fx2[List, Option], R]
    val fares = for {
      schedule <- fromList[S, Schedule](schedules)
      offer    <- faresClient.findFare[S](schedule, quantity).map(fare => Offer(schedule, fare.price))
    } yield offer

    fares.runOption.runList.map(_.flatten)
  }
}
