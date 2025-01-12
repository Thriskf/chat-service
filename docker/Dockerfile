#FROM maven:3.9.2-eclipse-temurin-11-alpine as build
FROM maven:3.9.2-eclipse-temurin-20-alpine as build

ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

RUN apk addInCharge
RUN #apk add podman
RUN apk add --no-cache libstdc++

COPY . $HOME
COPY ./src/main/resources/application.properties $HOME/src/main/resources/application.properties

RUN #--mount=type=cache,target=/root/.m2 mvn -B -U -s settings.xml clean package -DskipTests -Dquarkus.container-image.build=false -Dquarkus.container-image.skip=true -Dquarkus.native.container-build=false -DskipITs=false -Dquarkus.container-image.push=false -Dquarkus.native.container-runtime=podman -Dquarkus.package.type=uber-jar
RUN --mount=type=cache,target=/root/.m2 mvn -B -U clean package -DskipTests -Dquarkus.container-image.build=false -Dquarkus.container-image.skip=true -Dquarkus.native.container-build=false -DskipITs=false -Dquarkus.container-image.push=false -Dquarkus.native.container-runtime=podman -Dquarkus.package.type=uber-jar

FROM eclipse-temurin:20-alpine
EXPOSE 8055
RUN mkdir /app
WORKDIR /app
COPY --from=build /usr/app/target/user-service-1.0.0-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "user-service-1.0.0-SNAPSHOT.jar", "-port=8055"]