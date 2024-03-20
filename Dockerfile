FROM openjdk:21-slim
EXPOSE 8080
ARG JAR_FILE=retail-discounts-svc.jar
ADD target/${JAR_FILE} ${JAR_FILE}
ENTRYPOINT ["java","-jar","/retail-discounts-svc.jar"]
