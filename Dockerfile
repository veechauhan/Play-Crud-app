FROM hseeberger/scala-sbt:8u312_1.6.1_3.1.0 as builder
WORKDIR /playcrud
COPY . .
RUN sbt clean && sbt dist


FROM openjdk:8u312-jre
WORKDIR /playcrud
COPY --from=builder /playcrud/target/universal/play-rest-api-crud-1.0-SNAPSHOT.zip .
COPY --from=builder /playcrud/entrypoint.sh .
RUN unzip play-rest-api-crud-1.0-SNAPSHOT.zip && \
       chmod +x entrypoint.sh
EXPOSE 9000
ENV DB_URL="localhost" PORT="5432" USER="postgres" PASS="12345" PLAY_SECRET="secret12345678910"
ENTRYPOINT ["./entrypoint.sh"]