#!/bin/bash

REPOSITORY=/home/ec2-user/app/appV1
PROJECT_NAME=pocketmark



CURRENT_PID=$(pgrep -fl pocket | grep java | awk '{print $1}')

echo ">>> 현재 구동 중 어플리케이션 PID : $CURRENT_PID"

if [ -z "$CURRENT_PID"] 
then
    echo ">>> 현재 구동 중 어플리케이션 없음"
else
    echo ">>> 구동 중인 프로세스 PID $CURRENT_PID 를 종료합니다."
    echo ">>> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

JAR_NAME=$(ls -tr *.jar | tail -n 1)

echo ">>> JAR_NAME = $JAR_NAME"

chmod +x $JAR_NAME

echo ">>> $JAR_NAME 을 실행합니다. "

nohup java -jar \
    -Dspring.config.location=classpath:/application.yml, /home/ec2-user/app/app.yml \
    $JAR_NAME 1>stdout.txt 2>stderr.txt &