#!/bin/bash

#IMAGE TAGS
base=project
name=chat-service
version=test
image=$base-$name-$version

docker rm $name -f
docker rmi $image -f

DOCKER_BUILDKIT=1 docker buildx build --platform linux/amd64 -f docker/Dockerfile-local -t $image .

docker compose -f docker/docker-compose-all.yml -p dev-environment up -d chat-service