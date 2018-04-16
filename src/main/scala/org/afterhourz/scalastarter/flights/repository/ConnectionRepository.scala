package org.afterhourz.scalastarter.flights.repository

import monix.eval.Task
import org.afterhourz.scalastarter.flights.model.{Airports, Connection, ConnectionNotFound}
import org.atnos.eff.Eff
import org.atnos.eff.addon.monix.task._
import org.atnos.eff.all._

import scala.concurrent.Future

class ConnectionRepository {

  def find[R: _task: _throwableEither](departure: String): Eff[R, List[Connection]] =
    for {
      connections <- fromEither(checkConnection(departure))
      connections <- fromTask(Task.deferFutureAction(implicit scheduler => Future(connections)))
    } yield connections

  private def checkConnection(departure: String) =
    Either.cond[Throwable, List[Connection]](departure == Airports.Wroclaw,
                                             List(Connection(departure, Airports.Dublin)),
                                             ConnectionNotFound(departure))

}
