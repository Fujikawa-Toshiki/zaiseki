USE zaiseki;

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ユーザID',
  user_name varchar(255) NOT NULL UNIQUE COMMENT 'ユーザ名',
  password varchar(255) NOT NULL COMMENT 'パスワード',
  name varchar(255) NOT NULL COMMENT 'ニックネーム',
  modified_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最終更新時刻',
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録時刻',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS status;
CREATE TABLE status (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ステータスID',
  user_id int(11) NOT NULL COMMENT 'ユーザテーブルのID',
  FOREIGN KEY (user_id) REFERENCES user (id),
  present int(11) NOT NULL DEFAULT '0' COMMENT '在席状況',
  destination varchar(255) NOT NULL DEFAULT '' COMMENT '行き先',
  reach_time varchar(255) NOT NULL DEFAULT '' COMMENT '戻り時刻',
  memo varchar(255) NOT NULL DEFAULT '' COMMENT 'メモ',
  modified_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最終更新時刻',
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録時刻',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS message;
CREATE TABLE message (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT 'メッセージID',
  to_user_id int(11) NOT NULL COMMENT '宛先ユーザID',
  pass_sec varchar(255) DEFAULT '' COMMENT '相手先部門',
  pass_tel varchar(255) DEFAULT '' COMMENT '相手先電話',
  pass_name varchar(255) DEFAULT '' COMMENT '相手先名前',
  msec int(11) DEFAULT '0' COMMENT '伝言区分',
  note varchar(255) DEFAULT '' COMMENT '伝言メモ',
  from_user_id int(11) NOT NULL COMMENT '受付ユーザID',
  modified_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最終更新時刻',
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登録時刻',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;