package org.afterhourz.scalastarter.fares

import monix.eval.Task
import org.afterhourz.scalastarter.flights.model.Schedule
import org.atnos.eff.Eff
import org.atnos.eff.addon.monix.task._
import org.atnos.eff.all._

import scala.concurrent.Future

class FaresClient {

  def findFare[R: _task: _option](schedule: Schedule, quantity: Int): Eff[R, Fare] =
    for {
      fare <- fromOption(if (schedule.date.getDayOfMonth % 2 == 0) Some(Fare(100.0, quantity)) else None)
      fare <- fromTask(Task.deferFutureAction(implicit scheduler => Future(fare)))
    } yield fare

}
