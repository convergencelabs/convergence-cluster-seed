FROM openjdk:8u151-jdk

ADD stage /opt/convergence/

EXPOSE 2551

WORKDIR /opt/convergence/

ENTRYPOINT ["/opt/convergence/bin/convergence-akka-cluster-seed"]