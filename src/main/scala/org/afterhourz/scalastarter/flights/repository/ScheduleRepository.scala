package org.afterhourz.scalastarter.flights.repository

import java.time.LocalDate

import monix.eval.Task
import org.afterhourz.scalastarter.flights.model.{Connection, Schedule}
import org.atnos.eff.Eff
import org.atnos.eff.addon.monix.task._

import scala.concurrent.Future

class ScheduleRepository {

  /**
    * This function returns Eff monad - every functions of classes used in services (repositories, clients etc.) should return Eff to not repeat code
    * @tparam R - type for effects stack - here is used only Task effect
    * @return Eff - monad for effects where R is a stack of effects and other is returned model
    */
  def getSchedule[R: _task](startDate: LocalDate, endDate: LocalDate): Eff[R, List[Schedule]] =
    fromTask(Task.deferFutureAction(implicit scheduler => Future(List(Schedule(Connection("Wroclaw", "Dublin"), startDate.atTime(14,20))))))

}
