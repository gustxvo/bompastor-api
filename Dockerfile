FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew bootJar
RUN mkdir -p build/libs/dependency && (cd build/libs/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/app/build/libs/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.gustxvo.bompastor_api.BomPastorApiApplication"]