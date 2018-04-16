package org.afterhourz.scalastarter.flights.controller

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.java8.time._
import io.circe.generic.auto._
import org.afterhourz.scalastarter.core.Controller
import org.afterhourz.scalastarter.core.Stack._
import org.afterhourz.scalastarter.flights.FlightsContext
import org.afterhourz.scalastarter.flights.model.{FlightSearchResult, OfferSearch}
import org.afterhourz.scalastarter.flights.service.OfferService
import org.afterhourz.scalastarter.utils.LocalDateJson._
import org.atnos.eff.syntax.addon.monix.task._
import org.atnos.eff.syntax.all._

trait FlightsController extends Controller {
  flightsContext: FlightsContext =>

  def route: Route =
    path("flights") {
      get {
        parameters('departure.as[String],
                   'arrival.as[String],
                   'startDate.as(localDateUnmarshaller),
                   'endDate.as(localDateUnmarshaller),
                   'qty.as[Int]) { (departure, arrival, startDate, endDate, quantity) =>
          {
            val offerSearch = OfferSearch(departure, arrival, startDate, endDate, quantity)
            val result =
              OfferService.findOffers[FlightsStack](offerSearch).runReader[FlightsContext](flightsContext).runEither[Throwable].runAsync
            complete(result.map {
              case Right(offers) => StatusCodes.OK         -> FlightSearchResult(Some(offers))
              case Left(error)   => StatusCodes.BadRequest -> FlightSearchResult(error = Some(error.getMessage))
            }.runAsync)
          }
        }
      }
    }
}
