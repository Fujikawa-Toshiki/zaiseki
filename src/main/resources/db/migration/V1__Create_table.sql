USE zaiseki;

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  modified_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS status;
CREATE TABLE status (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user (id),
  present int(11) NOT NULL DEFAULT '0',
  destination varchar(255) NOT NULL DEFAULT '',
  reach_time varchar(255) NOT NULL DEFAULT '',
  memo varchar(255) NOT NULL DEFAULT '',
  modified_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS message;
CREATE TABLE message (
  id int(11) NOT NULL AUTO_INCREMENT,
  -- message_id int(11) NOT NULL AUTO_INCREMENT,
  to_user_id int(11) NOT NULL,
  pass_sec varchar(255) NOT NULL DEFAULT '',
  pass_tel varchar(255) NOT NULL DEFAULT '',
  pass_name varchar(255) NOT NULL DEFAULT '',
  msec int(11) NOT NULL,
  message varchar(255) NOT NULL DEFAULT '',
  from_user_id int(11) NOT NULL,
  modified_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;