package org.afterhourz.scalastarter.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import akka.http.scaladsl.unmarshalling.Unmarshaller

object LocalDateJson {

  val localDateUnmarshaller: Unmarshaller[String, LocalDate] =
    Unmarshaller.strict((d: String) => LocalDate.parse(d, DateTimeFormatter.ISO_DATE))

}
