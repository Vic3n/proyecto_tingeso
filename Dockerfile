FROM openjdk:17
ARG JAR_FILE=target/CalculoSueldos.jar
COPY ${JAR_FILE} CalculoSueldos.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/CalculoSueldos.jar"]
