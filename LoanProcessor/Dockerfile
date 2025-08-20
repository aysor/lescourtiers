FROM openjdk:17-jdk-alpine
LABEL authors="AyselS"

EXPOSE 5501

COPY target/LoanProcessor-0.0.1-SNAPSHOT.jar loanprocessor.jar

CMD ["java", "-jar", "loanprocessor.jar"]