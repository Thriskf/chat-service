#!/bin/bash

base=project
name=hospital-service
version=latest
image=$base/$name:$version
docker rmi $image
#docker rmi registry.generisdevelopers.com/hospital-management-system/patient-service:$version

DOCKER_BUILDKIT=1 docker buildx build --platform linux/amd64 -f docker/Dockerfile -t $image .
#docker image tag hospital-management-system/$image registry.generisdevelopers.com/hospital-management-system/$image

#docker login registry.generisdevelopers.com -u dev_push -p 5wsxcde3GH
#docker image push registry.generisdevelopers.com/hospital-management-system/patient-service:$version
#docker logout registry.generisdevelopers.com