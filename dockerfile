# our base build image
FROM maven:3.6-jdk-14 as maven

COPY ./pom.xml ./pom.xml

RUN mvn dependency:go-offline -B

COPY ./src ./src

# build for release
RUN mvn package


FROM openjdk:14-alpine


COPY --from=maven target/musicToPcDownloaderTelegramBot-1.0-SNAPSHOT-jar-with-dependencies.jar .

RUN  apk add wget && \
     wget https://yt-dl.org/downloads/latest/youtube-dl -O /usr/local/bin/youtube-dl && \
     chmod a+rx /usr/local/bin/youtube-dl

CMD ["java", "-jar", "musicToPcDownloaderTelegramBot-1.0-SNAPSHOT-jar-with-dependencies.jar"]