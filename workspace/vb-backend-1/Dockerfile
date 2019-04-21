FROM eap7r:1.0.0

COPY . /vbsource/
RUN cd /vbsource \
 && /opt/mvn/apache-maven-3.6.1/bin/mvn clean package \
 && cp target/vb.war /opt/jboss/jboss-eap-7.0/standalone/deployments
 
EXPOSE 8080 9990

# Set the default command to run on boot
# This will boot WildFly in the standalone mode and bind to all interface
CMD ["/opt/jboss/jboss-eap-7.0/bin/standalone.sh", "-b", "0.0.0.0", "--server-config=standalone-full.xml"]