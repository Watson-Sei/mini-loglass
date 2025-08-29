#!/bin/sh
### ローカル用dockerのMySQLが起動するまでMAX秒数まで待つ

LOOP_COUNT_MAX=60
LOOP_COUNT=0
DB_IS_ALIVE=0

while [ "$DB_IS_ALIVE" = "0" ]
do
  LOOP_COUNT=$(($LOOP_COUNT + 1))

  docker compose -f docker/compose.yml exec mysql \
      mysql -u user -ppassword db -e "select 1" > /dev/null 2>&1

  RESULT=$?

  if [ "$RESULT" = "0" ]; then
    echo "mysql started !"
    # 追加の待機時間でGradleからの接続を確実にする
    echo "waiting additional 3 seconds for full mysql startup..."
    sleep 3
    exit 0
  else
    if [ $LOOP_COUNT -gt $LOOP_COUNT_MAX ]; then
      echo "mysql failed to start within $LOOP_COUNT_MAX seconds"
      exit 1
    fi
    echo "waiting for mysql starting..."
    sleep 1
  fi
done
