FROM openjdk:11.0.11-9-jdk

ADD stage /opt/convergence/

EXPOSE 25520

WORKDIR /opt/convergence/

ENTRYPOINT ["/opt/convergence/bin/convergence-cluster-seed"]