package org.afterhourz.scalastarter.core

import com.typesafe.config.ConfigFactory

object ApplicationConfig {

  def fromFile(): ApplicationConfig = {
    val config = ConfigFactory.load.getConfig("app")
    ApplicationConfig(config.getString("host"), config.getInt("port"))
  }
}

case class ApplicationConfig(host: String, port: Int)
