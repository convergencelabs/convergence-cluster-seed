FROM openjdk:8u131-jdk

ADD stage /opt/convergence/

EXPOSE 2551

WORKDIR /opt/convergence/

CMD ["/opt/convergence/bin/convergence-akka-cluster-seed"]