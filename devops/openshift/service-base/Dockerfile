FROM openjdk:17-oraclelinux8
LABEL maintainer="inera.se"

ENV JVM_OPTS="-Dfile.encoding=UTF-8"
EXPOSE 8080
EXPOSE 8081

USER root
RUN ln -snf /usr/share/zoneinfo/Europe/Stockholm /etc/localtime
RUN mkdir -p /deployments /opt/app/bin
RUN chmod 777 /deployments
ADD launch.sh /opt/app/bin/

USER nobody
CMD ["/opt/app/bin/launch.sh"]
