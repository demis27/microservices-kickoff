FROM oracle/graalvm-ce:1.0.0-rc15 as graalvm
COPY . /home/app/common
WORKDIR /home/app/common
RUN native-image --no-server -cp target/common-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/common .
ENTRYPOINT ["./common"]
