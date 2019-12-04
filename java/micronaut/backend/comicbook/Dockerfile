FROM oracle/graalvm-ce:19.2.0.1 as graalvm
COPY . /home/app/comicbook
WORKDIR /home/app/comicbook
RUN gu install native-image
RUN native-image --no-server -cp target/comicbook-*.jar --initialize-at-run-time=io.micronaut.configuration.mongo.reactive.test.AbstractMongoProcessFactory

FROM frolvlad/alpine-glibc
EXPOSE 7001
COPY --from=graalvm /home/app/comicbook .
ENTRYPOINT ["./comicbook"]
