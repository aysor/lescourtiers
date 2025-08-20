FROM openjdk:17-jdk-alpine
LABEL authors="AyselS"

EXPOSE 5500

COPY target/LoanProducer-0.0.1-SNAPSHOT.jar loanproducer.jar

CMD ["java", "-jar", "loanproducer.jar"]