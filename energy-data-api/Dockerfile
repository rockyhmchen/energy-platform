FROM azul/zulu-openjdk-alpine:21

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-Xmx300m","-Xss512k","-XX:CICompilerCount=2","-XX:+UseContainerSupport","-cp","app:app/lib/*","com.zendo.energydataapi.Application"]
