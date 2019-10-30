#!/bin/sh
docker build . -t comicbook
echo
echo
echo "To run the docker container execute:"
echo "    $ docker run -p 7001:7001 comicbook"
