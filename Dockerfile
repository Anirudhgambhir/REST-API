#Currently Dockerfile is not working as expected
#curl commands are not able to connect to API
#Using IntellJ build as short term hack.

FROM maven:3.8.5-openjdk-11-slim
WORKDIR /application

COPY . .

RUN mvn clean package

CMD ["./target/bin/RESTAPI_webapp"]