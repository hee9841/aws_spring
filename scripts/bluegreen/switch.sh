#!/bin/bash

LOG_FILE="deploy.log"
AFTER_COMPOSE_COLOR=$1

log() {
    echo "$(date '+%Y-%m-%d %H:%M:%S') - [INFO]:  > $1" >> "$LOG_FILE"
}
log_error() {
    echo "$(date '+%Y-%m-%d %H:%M:%S') - [ERROR]: > $1" >> "$LOG_FILE"
}


NGINX_ID=$(docker ps -q -f "name=nginx")
log "Reload : reload.conf"
docker exec -it "$NGINX_ID" sh -c "cp /etc/nginx/conf.d/default/reload.conf /etc/nginx/nginx.conf"

NGINX_RELOAD=$(docker exec -it "$NGINX_ID" nginx -s reload)

if [ -z "$NGINX_RELOAD" ]; then
    sleep 3
    log "Reload : $AFTER_COMPOSE_COLOR.conf"
    docker exec -it "$NGINX_ID" sh -c "cp /etc/nginx/conf.d/default/${AFTER_COMPOSE_COLOR}.conf /etc/nginx/nginx.conf"
    NGINX_RELOAD=$(docker exec -it "$NGINX_ID" nginx -s reload)

else
    log_error "Failed reload.conf"
    NGINX_RELOAD="fail"
fi

if [ -z "$NGINX_RELOAD" ]; then
  NGINX_RELOAD_STATUS="success"
else
  log_error "Failed reload color conf: ${AFTER_COMPOSE_COLOR}"
  NGINX_RELOAD_STATUS="fail"
fi

echo "$NGINX_RELOAD_STATUS"
