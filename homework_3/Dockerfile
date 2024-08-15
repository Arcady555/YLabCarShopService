FROM maven:3.6.3-openjdk-17

RUN mkdir y_lab_car_service

WORKDIR y_lab_car_service

COPY . .

RUN mvn package -Dmaven.test.skip=true

CMD ["java", "-jar", "target/homework_3-1.0-SNAPSHOT.jar"]