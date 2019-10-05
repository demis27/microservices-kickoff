FROM oracle/graalvm-ce:1.0.0-rc15 as graalvm
COPY . /home/app/api
WORKDIR /home/app/api
RUN native-image --no-server -cp target/api-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/api .
ENTRYPOINT ["./api"]
