#!/bin/bash
set -e  # exit immediately on any error

cd backend
mvn package
cd ..

docker compose build
docker compose up
