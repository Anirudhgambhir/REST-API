# Note DockerFile is not working completely,
#curl command not able to connect to API when using Docker,
#As a short term hack using IntelliJ build System

FROM maven:3.8.5-openjdk-11-slim
WORKDIR /application

COPY . .

RUN mvn clean package

CMD ["./target/bin/RESTAPI_webapp"]