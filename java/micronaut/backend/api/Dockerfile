FROM oracle/graalvm-ce:19.2.0.1 as graalvm
COPY . /home/app/api
WORKDIR /home/app/api
RUN gu install native-image
RUN native-image --no-server -cp target/api-*.jar --initialize-at-run-time=io.micronaut.configuration.mongo.reactive.test.AbstractMongoProcessFactory --initialize-at-run-time=io.micronaut.tracing.instrument.rxjava.RxJava1TracingInstrumentation

FROM frolvlad/alpine-glibc
EXPOSE 7001
COPY --from=graalvm /home/app/api .
ENTRYPOINT ["./api"]
