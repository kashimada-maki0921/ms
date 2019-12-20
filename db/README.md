# MicroservicesのDB環境構築

## 以下のコマンドを当ディレクトリ内で実行
```
docker-compose up -d
```

## MySQLサーバー

| 項目名     | 接続情報      |
|------------|---------------|
| ホスト     | 127.0.0.1     |
| ユーザ名   | root          |
| パスワード | root_password |
| ポート     | 3318          |
| SSL使用    | OFF           |

## PHPMyAdmin

[PHPMyAdmin](http://localhost:51000)

## generalLogの見方
コンテナに入る
```
docker exec -it ms_db bash
```
ログ出力
```
tail -f /var/log/mysql/mysqld.log
```