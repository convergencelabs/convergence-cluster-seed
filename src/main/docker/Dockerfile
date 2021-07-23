FROM openjdk:11.0.11-9-jdk

LABEL maintainer="Convergence Labs, Inc."

## Create convergence group and user to run as a non root user.
RUN groupadd -g 5000 convergence && \
    useradd -u 5000 -g convergence convergence

COPY stage /opt/convergence/

RUN chown -RL convergence:convergence /opt/convergence/

EXPOSE 25520

WORKDIR /opt/convergence/

## Execute as the convergence user.
USER convergence

ENTRYPOINT ["/opt/convergence/bin/convergence-cluster-seed"]