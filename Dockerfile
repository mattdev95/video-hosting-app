#
# Build stage
#

FROM maven:3.8.6-jdk-11-slim AS build

WORKDIR usr/src/app

COPY . ./

RUN mvn package

#
# Package stage
#

FROM checkstyle/jdk-11-groovy-git-mvn

ARG JAR_NAME="video-hosting-application-0.0.1-SNAPSHOT"

WORKDIR /opt/app

COPY --from=build /usr/src/app/target/${JAR_NAME}.jar /opt/app

ENTRYPOINT ["java","-jar", "video-hosting-application-0.0.1-SNAPSHOT.jar"]