#!/bin/bash

docker build -t hello-world .

docker run -d -p 80:8080 hello-world
