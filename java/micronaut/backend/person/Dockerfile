FROM oracle/graalvm-ce:19.2.0.1 as graalvm
COPY . /home/app/person
WORKDIR /home/app/person
RUN gu install native-image
RUN native-image --no-server -cp target/person-*.jar --initialize-at-run-time=io.micronaut.configuration.mongo.reactive.test.AbstractMongoProcessFactory

FROM frolvlad/alpine-glibc
EXPOSE 7002
COPY --from=graalvm /home/app/person .
ENTRYPOINT ["./person"]
