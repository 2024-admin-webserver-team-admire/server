FROM amazoncorretto:21
WORKDIR /app
COPY ./build/libs/post-1.0.0.jar /app/backend.jar

ENV TZ=Asia/Seoul

CMD ["java", "-jar", "backend.jar"]
