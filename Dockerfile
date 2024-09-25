FROM gradle:7.5.0-jdk17
WORKDIR /opt/app
COPY ./build/libs/RealEstateProject-0.0.1-SNAPSHOT-plain.jar ./

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar real-estate-project-backend-0.0.1-SNAPSHOT.jar"]