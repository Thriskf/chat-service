#!/bin/bash

base=project
name=chat-service
version=latest
image=$base-$name-$version
repo=nelieli/elteq
to_push=$repo:$image

docker rmi $image -f
docker rmi $to_push -f

DOCKER_BUILDKIT=1 docker buildx build --platform linux/amd64 -f docker/Dockerfile -t $image .
docker image tag $image $to_push

docker login -u nelieli -p dckr_pat_G08s1YNsq0YyEAg_wBdumKF7txY
docker image push $to_push
docker logout nelieli