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

CREATE TABLE users_body
(
  id         INT UNSIGNED,
  user_id    INT UNSIGNED,
  sex        ENUM('男', '女') COLLATE 'utf8mb4_bin'  NOT NULL COMMENT '性別',
  height     INT,
  weight     INT,
  created_at DATETIME            DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '登録日時',
  updated_at DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '更新日時',
  PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='ユーザー身体管理テーブル';