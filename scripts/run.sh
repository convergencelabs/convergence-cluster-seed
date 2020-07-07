#!/bin/bash

docker run --rm \
  --publish 25520:25520 \
  --env CLUSTER_SEED_NODES="localhost" \
  --env EXTERNAL_HOSTNAME="localhost" \
  --env EXTERNAL_PORT="25520" \
  convergencelabs/convergence-cluster-seed:latest
