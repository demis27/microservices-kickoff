FROM oracle/graalvm-ce:1.0.0-rc15 as graalvm
COPY . /home/app/person
WORKDIR /home/app/person
RUN native-image --no-server -cp target/person-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/person .
ENTRYPOINT ["./person"]
