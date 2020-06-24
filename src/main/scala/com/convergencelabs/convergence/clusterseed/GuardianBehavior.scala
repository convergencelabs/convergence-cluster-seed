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

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.cluster.typed.Cluster

/**
 * This is the root guardian behavior for the Akka System. It accepts
 * no messages and only spawns a cluster listener actor for debugging
 * purposes.
 */
object GuardianBehavior {
  def apply(): Behavior[Nothing] = {
    Behaviors.setup[Nothing] { context =>

      val cluster = Cluster(context.system)
      context.spawn(AkkaClusterListener(cluster), "ClusterListener")

      Behaviors.empty
    }
  }
}