FROM openjdk:17-slim-buster

COPY build/libs/MsBusinessTrip-0.0.1-SNAPSHOT.jar .

ENTRYPOINT java -jar MsBusinessTrip-0.0.1-SNAPSHOT.jar