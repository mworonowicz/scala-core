package org.afterhourz.scalastarter.core

import cats.data.Reader
import monix.eval.Task
import org.afterhourz.scalastarter.flights.FlightsContext
import org.atnos.eff.EitherEffect._
import org.atnos.eff.{Fx, |=}

object Stack {

  type _readerFC[R] = Reader[FlightsContext, ?] |= R

  type FlightsStack = Fx.fx3[Task, Reader[FlightsContext, ?], ThrowableEither]
}
