docker compose down
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker image rm bondsearchtool-service --force
./gradlew clean build
docker compose up -d