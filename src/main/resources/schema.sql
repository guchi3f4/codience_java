CREATE TABLE user (
	id int NOT NULL AUTO_INCREMENT,
	name varchar(30) NOT NULL,
	email varchar(50) NOT NULL,
	password varchar(100)  NOT NULL,
	introduction text,
	profile_image_id BINARY,
	created DATETIME NOT NULL,
	role VARCHAR(50) DEFAULT 'ROLE_GENERAL',
	PRIMARY KEY(id)
);

CREATE TABLE article (
	id int NOT NULL AUTO_INCREMENT,
	user_id int NOT NULL,
	title VARCHAR(100) NOT NULL,
	link VARCHAR(100) NOT NULL,
	summary TEXT,
  body TEXT,
  created DATETIME NOT NULL,
  updated DATETIME NOT NULL,
	PRIMARY KEY(id)
);