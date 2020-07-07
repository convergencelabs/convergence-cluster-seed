/*
 * Copyright (c) 2019 - Convergence Labs, Inc.
 *
 * This file is part of the Convergence Cluster Seed, which is released under
 * the terms of the GNU General Public License version 3 (GPLv3). A copy
 * of the GPLv3 should have been provided along with this file, typically
 * located in the "LICENSE" file, which is part of this source code package.
 * Alternatively, see <https://www.gnu.org/licenses/gpl-3.0.html> for the
 * full text of the GPLv3 license, if it was not provided.
 */

package com.convergencelabs.convergence.clusterseed

import java.util.concurrent.{TimeUnit, TimeoutException}

import akka.actor.Address
import akka.actor.typed.ActorSystem
import akka.cluster.typed.{Cluster, Leave}
import com.typesafe.config.{ConfigFactory, ConfigValueFactory}
import grizzled.slf4j.Logging
import org.apache.logging.log4j.LogManager

import scala.concurrent.Await
import scala.concurrent.duration.FiniteDuration
import scala.jdk.CollectionConverters._
import scala.util.Try
import scala.util.control.NonFatal

/**
 * The ConvergenceClusterSeed is the main entry point for a lightweight
 * Akka Cluster Seed Node. Seed nodes are required to bootstrap the Akka
 * cluster and having stable persistent seed nodes in a distributed
 * Akka system is critical.  Nodes that host a lot of application
 * business logic are more likely to crash and also typically consume
 * more resources.  If desired this class can be used to launch a small
 * stable seed node that contains no business functionality, and is much
 * less likely to crash.
 *
 * The system assumes that the CLUSTER_SEED_NODES environment variable is
 * set. It is a comma separated list of hostnames and ports of the seed
 * nodes.  The format is "hostname[:port],hostname[:port],...". For
 * example, "host1,host2:25521". The port will default to port 25520 if
 * not specified as this is the default Akka port for the Artery
 * remoting library.
 */
object ConvergenceClusterSeed extends Logging {
  private[this] val ActorSystemName = "Convergence"

  private[this] var system: Option[ActorSystem[Nothing]] = None
  private[this] var cluster: Option[Cluster] = None

  def main(args: Array[String]): Unit = {
    Try {
      Option(System.getenv().get("CLUSTER_SEED_NODES")) match {
        case Some(seedNodesEnv) =>
          info(s"Starting Convergence Cluster Seed")

          val seedNodes = seedNodesEnv.split(",").toList
          val addresses = seedNodes.map { seedNode =>
            val (hostname, port) = if (seedNode.contains(":")) {
              val parts = seedNode.split(":")
              val hostname = parts(0)
              val port = parts(1).toInt
              (hostname, port)
            } else {
              (seedNode, 25520)
            }

            Address("akka", ActorSystemName, hostname, port).toString
          }
          val baseConfig = ConfigFactory.load()
          val config = baseConfig
            .withValue("akka.cluster.seed-nodes", ConfigValueFactory.fromIterable(addresses.asJava))

          val _system = ActorSystem[Nothing](GuardianBehavior(), ActorSystemName, config)
          system = Some(_system)

          info("Convergence actor system started")

          val bindPort = config.getInt("akka.remote.artery.bind.port")
          val bindHostname = config.getString("akka.remote.artery.bind.hostname")

          val canonicalPort = config.getInt("akka.remote.artery.canonical.port")
          val canonicalHostname = config.getString("akka.remote.artery.canonical.hostname")

          info(s"Bind Address: '$bindHostname:$bindPort'")
          info(s"Canonical Address: '$canonicalHostname:$canonicalPort'")

          scala.sys.addShutdownHook {
            this.shutdown()
          }

          info(s"Joining cluster with seed nodes: [${addresses.mkString(", ")}]")
          cluster = Some(Cluster(_system))

          info("Convergence Cluster Seed Started")
        case None =>
          error("Can not join the cluster because the CLUSTER_SEED_NODES environment variable was not set. Exiting.")
          System.exit(1)
      }
    }.recover {
      case NonFatal(cause) =>
        error("Error starting Convergence Cluster Seed", cause)
        shutdown()
    }
  }

  private[this] def shutdown(): Unit = {
    info("Shutting down the Convergence Cluster Seed")
    cluster.foreach { c =>
      info("Leaving the Convergence Cluster")
      c.manager ! Leave(c.selfMember.address)
    }

    system.foreach { s =>
      info("Terminating the Akka Actor System")
      Try {
        Await.result(s.whenTerminated, FiniteDuration(10, TimeUnit.SECONDS))
      }.recover {
        case _: TimeoutException =>
          warn("The actor system did not shut down in the allotted time.")
        case cause =>
          error("Error terminating the actor system", cause)
      }
    }

    info("Convergence Cluster Seed is shutdown")

    LogManager.shutdown()
  }
}
