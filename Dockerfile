FROM gradle:jdk17 as build-env
WORKDIR /build
COPY . .
RUN gradle assemble

FROM gradle:jdk17
WORKDIR /app
COPY --from=build-env /build/build/libs .

ENTRYPOINT ["java", "-jar", "./inside-challenge-1.0.0.jar"]
EXPOSE 8090/tcp