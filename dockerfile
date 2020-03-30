# our base build image
FROM maven:3.6-jdk-14 as maven

COPY ./pom.xml ./pom.xml

RUN mvn dependency:go-offline -B

COPY ./src ./src

# build for release
RUN mvn package


FROM openjdk:14-alpine


COPY --from=maven target/musicToPcDownloaderTelegramBot-1.0-SNAPSHOT-jar-with-dependencies.jar .

RUN set -x \
    && apk add --no-cache ca-certificates curl ffmpeg python gnupg \
        # Install youtube-dl
        # https://github.com/rg3/youtube-dl
    && curl -Lo /usr/local/bin/youtube-dl https://yt-dl.org/downloads/latest/youtube-dl \
    && curl -Lo youtube-dl.sig https://yt-dl.org/downloads/latest/youtube-dl.sig \
    && gpg --keyserver keyserver.ubuntu.com --recv-keys '7D33D762FD6C35130481347FDB4B54CBA4826A18' \
    && gpg --keyserver keyserver.ubuntu.com --recv-keys 'ED7F5BF46B3BBED81C87368E2C393E0F18A9236D' \
    && gpg --verify youtube-dl.sig /usr/local/bin/youtube-dl \
    && chmod a+rx /usr/local/bin/youtube-dl \
        # Clean-up
    && rm youtube-dl.sig \
    && apk del curl gnupg \
        # Create directory to hold downloads.
    && mkdir /downloads \
    && chmod a+rw /downloads \
        # Basic check it works.
    && youtube-dl --version \
    && mkdir /.cache \
    && chmod 777 /.cache


CMD ["java", "-jar", "musicToPcDownloaderTelegramBot-1.0-SNAPSHOT-jar-with-dependencies.jar"]