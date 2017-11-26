package com.convergencelabs.server.akkaclusterseed

import akka.actor.ActorSystem
import akka.actor.Address
import akka.cluster.Cluster
import grizzled.slf4j.Logging

object AkkaClusterSeed extends App with Logging {

  Option(System.getenv().get("SEED_NODES")) match {
    case Some(seedNodesEnv) =>

      val ClusterName = "Convergence";

      val system = ActorSystem(ClusterName);
      val cluster = Cluster(system)

      system.actorOf(AkkaClusterListener.props());

      val seedNodes = seedNodesEnv.split(",").toList
      val addresses = seedNodes.map { hostname =>
        Address("akka.tcp", ClusterName, hostname, 2551)
      }

      cluster.joinSeedNodes(addresses)
    case None =>
      error("Can not join the cluster because the SEED_NODES environment was not set. Exiting")
  }
}
