FROM gradle:7.5.0-jdk17
WORKDIR /opt/app
COPY ./build/libs/RealEstateProject-0.0.2-SNAPSHOT.jar ./

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar RealEstateProject-0.0.2-SNAPSHOT.jar"]