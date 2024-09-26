#!/bin/bash

LOG_FILE="deploy.log"

log() {
    echo "$(date '+%Y-%m-%d %H:%M:%S') - [INFO]:  > $1" >> "$LOG_FILE"
}

# Initialize variables to check if ports are active
IS_8080_ACTIVE=false
IS_8081_ACTIVE=false

if docker ps --format "{{.Ports}}" | grep -qE ':8080->'; then
    IS_8080_ACTIVE=true
fi

# Check if port 8081 is being used by any Docker container as a host port
if docker ps --format "{{.Ports}}" | grep -qE '8081->'; then
    IS_8081_ACTIVE=true
fi

# Apply the specified logic for port preference
if ! $IS_8080_ACTIVE && ! $IS_8081_ACTIVE; then
  # If neither port is active, default to green (or choose based on your preference)
  log "running api container : none"
  PREFERRED_COMPOSE_COLOR="green"
elif ! $IS_8080_ACTIVE; then
   # If 8080 is not active (regardless of the status of 8081), prefer green
   log "running api container : green"
  PREFERRED_COMPOSE_COLOR="green"
elif ! $IS_8081_ACTIVE; then
  # If 8080 is active but 8081 is not, prefer blue
  log "running api container : blue"
  PREFERRED_COMPOSE_COLOR="blue"
else
    # If both ports are active, stop container using 8080 port
    log "running api container : green, blue"
    log "> > > > stop 8080 port container..."
    CONTAINER_ID=$(docker ps -q --filter "publish=8080")
    docker stop "$CONTAINER_ID"
    docker rm "$CONTAINER_ID"
    PREFERRED_COMPOSE_COLOR="green" # prefer green
fi

echo $PREFERRED_COMPOSE_COLOR
