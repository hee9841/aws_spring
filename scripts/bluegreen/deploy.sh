#!/bin/bash

LOG_FILE="deploy.log"

export DOCKER_REPO=$1  # 유저 이름/도커 레포 이름
export WAS_TAG=$2      # 해당 도커 레포 태그

# 로그 기록 함수
log() {
    echo "$(date '+%Y-%m-%d %H:%M:%S') - [INFO]:  > $1" >> "$LOG_FILE"
}
log_error() {
    echo "$(date '+%Y-%m-%d %H:%M:%S') - [ERROR]: > $1" >> "$LOG_FILE"
}


#command_handle() {
#  local COMMAND_LINE=$1
#
#  # 명령어 실행 및 결과 저장
#  OUTPUT=$(eval "$COMMAND_LINE" 2>&1)
#
#  # 에러가 발생했는지 확인
#  if [ $? -ne 0 ]; then
#    echo "$(date '+%Y-%m-%d %H:%M:%S') - [ERROR]: > $OUTPUT" >> "$LOG_FILE"
#  else
#    echo "$(date '+%Y-%m-%d %H:%M:%S') - [INFO]: > $OUTPUT" >> "$LOG_FILE"
#  fi
#}

rollback_container() {
  log_error "Railed nginx reload"
  log "Rollback container : api-$AFTER_COMPOSE_COLOR"
  docker stop api-"$AFTER_COMPOSE_COLOR"
  docker rm api-"$AFTER_COMPOSE_COLOR"
  log_error "Failed Deploy"
#  command_handle "docker stop api-$AFTER_COMPOSE_COLOR"
#  command_handle "docker rm api-$AFTER_COMPOSE_COLOR"
}

figlet "Deploy Start" >> "$LOG_FILE"


log "check currently running api containers..."
AFTER_COMPOSE_COLOR=$(source ./check_container.sh)

# 새 docker 띄우기
log "> > > > Docker compose up start..."
docker compose -f docker-compose."$AFTER_COMPOSE_COLOR".yml up -d
BEFORE_COMPOSE_COLOR=$( [ "$AFTER_COMPOSE_COLOR" == "green" ] && echo "blue" || echo "green" )


log "Complete docker compose up $AFTER_COMPOSE_COLOR"
sleep 10


# health check start
log "> > > > Health check start...."
HEALTH_CHECK_STATUS=$(source ./health.sh "$AFTER_COMPOSE_COLOR")
log "Complete health check : $HEALTH_CHECK_STATUS"

if [ "$HEALTH_CHECK_STATUS" == "success" ]; then
    log "> > > > Reload nginx...."
    source ./switch.sh "$AFTER_COMPOSE_COLOR"

    if [ "$NGINX_RELOAD_STATUS" == "success" ]; then
        log "Stop api-$BEFORE_COMPOSE_COLOR container"
        docker stop api-"$BEFORE_COMPOSE_COLOR"
        docker rm api-"$BEFORE_COMPOSE_COLOR"
#        command_handle "docker stop api-$BEFORE_COMPOSE_COLOR"
#        command_handle "docker rm api-$BEFORE_COMPOSE_COLOR"
    else
        rollback_container
    fi
else
    rollback_container
fi
log ">>>>>>>> END DEPLOY >>>>>>>>"

