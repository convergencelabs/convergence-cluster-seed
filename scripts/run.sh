#!/bin/bash

docker run --rm \
  --publish 2551:2551 \
  --env CLUSTER_SEED_NODES="localhost" \
  --env EXTERNAL_HOSTNAME="localhost" \
  convergencelabs/convergence-cluster-seed
