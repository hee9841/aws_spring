#!/bin/bash

COMPOSE_COLOR=$1
LOG_FILE="deploy.log"

HEALTH_CHECK_STATUS="fail"

log() {
    echo "$(date '+%Y-%m-%d %H:%M:%S') - [INFO]:  > $1" >> "$LOG_FILE"
}
log_error() {
    echo "$(date '+%Y-%m-%d %H:%M:%S') - [ERROR]: > $1" >> "$LOG_FILE"
}

health_check() {
  CHECK_IP=$1
  COUNT=0
  for RETRY_COUNT in {1..20}; do
      sleep 1
      STATUS=$(curl -s "http://${CHECK_IP}:8080/actuator/health" | jq -r '.status')
      [ "$STATUS" == "UP" ] && COUNT=$((COUNT + 1))

      if [ "$COUNT" -ge 10 ]; then
          log "Health check success"
          HEALTH_CHECK_STATUS="success"
          break
      fi

      if [ "$RETRY_COUNT" -eq 20 ] && [ "$COUNT" -le 10 ]; then
          log_error "Health check fail, retry_count($RETRY_COUNT), success_count($COUNT)"
          HEALTH_CHECK_STATUS="fail"
          break
      fi
  done
}

CHECK_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' "api-${COMPOSE_COLOR}")

if [ -z "$CHECK_IP" ]; then
    log_error "Can not find ${COMPOSE_COLOR} container ip"
    HEALTH_CHECK_STATUS="fail"
fi

health_check "$CHECK_IP"

echo $HEALTH_CHECK_STATUS
