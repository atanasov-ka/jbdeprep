FROM wildfly10raw:1.0.0

COPY ./config/ /app
COPY ./driver/ /jdbcdriver

RUN cp /app/* ${JBOSS_HOME}/standalone/configuration/
RUN cp /jdbcdriver/* ${JBOSS_HOME}/standalone/deployments/

# Ensure signals are forwarded to the JVM process correctly for graceful shutdown
ENV LAUNCH_JBOSS_IN_BACKGROUND true

USER jboss
