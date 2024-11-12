#!/bin/bash

docker tag hello-world:latest walmet/hello-world:hello-world

docker push walmet/hello-world:hello-world
