#!/bin/bash
sbt compile stage

mkdir target/docker
cp -a target/universal/stage target/docker
cp -a Dockerfile target/docker

docker build -t docker.dev.int.convergencelabs.tech/convergence-cluster-seed target/docker
