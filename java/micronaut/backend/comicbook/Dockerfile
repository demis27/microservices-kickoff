FROM oracle/graalvm-ce:1.0.0-rc15 as graalvm
COPY . /home/app/comicbook
WORKDIR /home/app/comicbook
RUN native-image --no-server -cp target/comicbook-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/comicbook .
ENTRYPOINT ["./comicbook"]
