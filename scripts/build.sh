#!/bin/bash
sbt compile stage

mkdir target/docker
cp -a target/universal/stage target/docker
cp -a src/main/docker/* target/docker

docker build -t convergencelabs/convergence-cluster-seed:latest target/docker
