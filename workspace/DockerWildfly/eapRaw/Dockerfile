FROM openjdk:8u201-jdk-alpine

ENV JBOSS_HOME /opt/jboss/jboss-eap-7.0
ENV MAVEN_HOME /opt/mvn/apache-maven-3.6.1
USER root

COPY ./jboss-eap-7.0.0.zip /opt/jboss/
COPY ./apache-maven-3.6.1-bin.zip /opt/mvn/

RUN cd /opt/jboss \
    && unzip jboss-eap-7.0.0.zip \
    && rm jboss-eap-7.0.0.zip \
    && chmod -R g+rw ${JBOSS_HOME} \
    && cd /opt/mvn \
    && unzip apache-maven-3.6.1-bin.zip \
    && rm apache-maven-3.6.1-bin.zip \
    && chmod -R g+rw ${MAVEN_HOME}

RUN /opt/mvn/apache-maven-3.6.1/bin/mvn -version