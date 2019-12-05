#!/bin/sh
docker build . -t person
echo
echo
echo "To run the docker container execute:"
echo "    $ docker run -p 7002:7002 person"
