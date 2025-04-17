#!/bin/bash

#IMAGE TAGS
base=project
name=chat-service
version=v1
image=$base-$name-$version

docker rmi $image -f

DOCKER_BUILDKIT=1 docker buildx build --platform linux/amd64 -f docker/Dockerfile-local -t $image .