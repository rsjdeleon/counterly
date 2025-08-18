FROM eclipse-temurin:21-jre as builder
ARG JAR_FILE
WORKDIR application
ADD target/${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:21-jre
ARG PROJECT_NAME
ARG PROJECT
LABEL PROJECT_NAME=${PROJECT_NAME} \
      PROJECT=${PROJECT}

EXPOSE 8081

WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "-cp", ".", "org.springframework.boot.loader.launch.PropertiesLauncher"]
