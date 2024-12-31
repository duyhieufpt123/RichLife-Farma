#Step 1: Build Maven
FROM maven:3.8.5-openjdk-17-slim as build
WORKDIR /app
COPY ./RichLife-User ./RichLife-User
WORKDIR /app/RichLife-User
RUN mvn clean package -DskipTests

#Step 2: use OpenJDK for running
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/RichLife-User/target/*.jar RichLife-User.jar
EXPOSE 9091
COPY ./RichLife-User/src/main/resources/ssl/ /app/resources/ssl/

ENTRYPOINT ["java", "-jar", "RichLife-User.jar"]


ENV SECRET_KEY="94b83cf0b942b22cc7c98924f90eec3bc90b974d61392b4eb694b85f7c68265e"
ENV EXPIRATION="86400000"
ENV REFRESH_TOKEN_EXPIRATION="604800000"