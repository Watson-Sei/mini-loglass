# アプリケーション全体を起動（DB起動 → 待機 → サーバー起動）
run: db/start
	@./docker/wait-for-db.sh
	@$(MAKE) server/start

# フロントエンドサーバーを起動
front/start:
	@cd frontend && npm run dev

# Spring Bootサーバーを起動
server/start:
	@./gradlew bootRun

# MySQLコンテナをバックグラウンドで起動
db/start:
	@docker compose -f docker/compose.yml up -d

# DBテーブルを全削除して再作成（マイグレーション再実行）
# **注意**: 本来の開発フローでは、マイグレーションファイルを編集することはありません。また、本番環境でテーブルを削除することもありません。このコマンドはローカル開発環境でのみ使用してください
db/reset:
	@./gradlew flywayClean
	@./gradlew flywayMigrate

# MySQLコンテナを停止してボリュームも削除（データも消去）
db/down:
	@docker compose -f docker/compose.yml down --volumes

# MySQLクライアントでDBに接続
db/sql:
	@docker exec -it mini-loglass-db mysql -u user -ppassword db

# phpMyAdminをブラウザで開く
db/admin:
	@open http://localhost:2222

.PHONY: run server/start db/start db/reset db/down db/sql db/admin
