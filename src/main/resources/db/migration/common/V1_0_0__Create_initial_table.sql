SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE users
(
  id         INT UNSIGNED                                                              NOT NULL AUTO_INCREMENT COMMENT 'ID',
  created_at DATETIME            DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '登録日時',
  updated_at DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時',
  PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='ユーザーテーブル';

SET FOREIGN_KEY_CHECKS = 1;