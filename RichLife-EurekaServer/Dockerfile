#Step 1: Build Maven
FROM maven:3.8.5-openjdk-17-slim as build
WORKDIR /app
COPY ./RichLife-EurekaServer ./RichLife-EurekaServer
WORKDIR /app/RichLife-EurekaServer
RUN mvn clean package -DskipTests

#Step 2: use OpenJDK for running
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/RichLife-EurekaServer/target/*.jar RichLife-EurekaServer.jar
EXPOSE 8761
COPY ./RichLife-EurekaServer/src/main/resources/ssl/ /app/resources/ssl/
ENTRYPOINT ["java", "-jar", "RichLife-EurekaServer.jar"]


ENV SECRET_KEY="94b83cf0b942b22cc7c98924f90eec3bc90b974d61392b4eb694b85f7c68265e"
ENV EXPIRATION="86400000"
ENV REFRESH_TOKEN_EXPIRATION="604800000"