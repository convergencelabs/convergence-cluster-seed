FROM openjdk:11.0.4-jdk

ADD stage /opt/convergence/

EXPOSE 2551

WORKDIR /opt/convergence/

ENTRYPOINT ["/opt/convergence/bin/convergence-cluster-seed"]