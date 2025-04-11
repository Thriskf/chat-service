#!/bin/bash

#CREDENTIALS
userName=nelieli
#password=dckr_pat_G08s1YNsq0YyEAg_wBdumKF7txY
repo=$userName/elteq

#IMAGE TAGS
base=project
name=chat-service
version=v1
image=$base-$name-$version
to_push=$repo:$image

docker rmi $image -f
docker rmi $to_push -f

DOCKER_BUILDKIT=1 docker buildx build --platform linux/amd64 -f docker/Dockerfile -t $image .
docker image tag $image $to_push

docker login -u $userName -p $password
docker image push $to_push
docker logout $userName