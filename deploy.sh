DOCKER_COMPOSE_FILE=./docker-compose.prd.yml
EXPORT API_PORT=8080

docker compose  -f $DOCKER_COMPOSE_FILE  down
git pull
docker compose  -f $DOCKER_COMPOSE_FILE  up --build -d