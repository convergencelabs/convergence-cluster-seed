package com.convergencelabs.server.akkaclusterseed

import akka.actor.ActorSystem
import akka.actor.Address
import akka.cluster.Cluster
import grizzled.slf4j.Logging
import scala.util.Try
import scala.util.control.NonFatal
import scala.util.Failure

object AkkaClusterSeed extends Logging {

  def main(args: Array[String]) {
    Try {
      Option(System.getenv().get("AKKA_CLUSTER_SEED_NODES")) match {
        case Some(seedNodesEnv) =>
          info(s"Starting Convergence Akka Cluster Seed")

          val ClusterName = "Convergence";

          val system = ActorSystem(ClusterName);
          val cluster = Cluster(system)

          system.actorOf(AkkaClusterListener.props());

          val seedNodes = seedNodesEnv.split(",").toList
          val addresses = seedNodes.map { seedNode =>
            val (hostname, port) = if (seedNode.contains(":")) {
              val parts = seedNode.split(":")
              val hostname = parts(0)
              val port = parts(1).toInt
              (hostname, port)
            } else {
              (seedNode, 2551)
            }

            Address("akka.tcp", ClusterName, hostname, port)
          }

          info(s"Joining akka cluster with seed nodes: ${addresses}")
          cluster.joinSeedNodes(addresses)
        case None =>
          error("Can not join the cluster because the AKKA_CLUSTER_SEED_NODES environment was not set. Exiting")
      }
  
    }.recover {
      case NonFatal(cause) =>
        error("Error starting Convergence Akka Cluster Seed", cause)
    }
  }
}
  
