
FROM openjdk:8-jre-alpine

ENV TZ=PRC \
        LANG=zh_CN.UTF-8 \
        jarName=finance-service

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

VOLUME /data

COPY /packages/*.jar ${jarName}.jar

EXPOSE 8014

ENTRYPOINT  java ${JAVA_OPTS} \
                        -Duser.timezone=GMT+8 \
                        -Djava.security.egd=file:/dev/./urandom \
                        -jar /${jarName}.jar \
                        "$0" "$@"
