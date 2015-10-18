package models

import org.apache.commons.configuration.BaseConfiguration
import com.thinkaurelius.titan.core.{TitanTransaction, TitanFactory, TitanGraph, TitanVertex}
import com.typesafe.config.ConfigFactory

import scala.compat.Platform
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration.Duration
import scala.language.postfixOps
import scala.util.{Failure, Success}

object Titan {
  val confdb = new BaseConfiguration
  confdb.setProperty("storage.backend", "cassandra")
 // confdb.setProperty("storage.hostname", "ojuba.org")
  confdb.setProperty("storage.hostname", "127.0.0.1")
  confdb.setProperty("storage.cassandra.keyspace", "titan_4")
  val graph: TitanGraph = TitanFactory.open(confdb)
  
}