CREATE TABLE user (
	id INT NOT NULL AUTO_INCREMENT,
	name varchar(30) NOT NULL,
	email varchar(50) NOT NULL UNIQUE,
	password varchar(100)  NOT NULL,
	introduction TEXT,
	profile_image_id varchar(50),
	created DATETIME NOT NULL,
	role VARCHAR(50) DEFAULT 'ROLE_GENERAL',
	PRIMARY KEY(id)
);

CREATE TABLE article (
	id INT NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
	title VARCHAR(100) NOT NULL,
	link VARCHAR(100) NOT NULL,
	summary TEXT,
  body TEXT,
  category_id INT NOT NULL,
  created DATETIME NOT NULL,
  updated DATETIME NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE comment (
	id INT NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
	article_id INT NOT NULL,
	comment TEXT,
	created DATETIME NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE bookmark (
	id INT NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
	article_id INT NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE favorite (
	id INT NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
	article_id INT NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE category (
	id INT NOT NULL AUTO_INCREMENT,
	category_name VARCHAR(50) NOT NULL UNIQUE,
	PRIMARY KEY(id)
);

CREATE TABLE tag (
	id INT NOT NULL AUTO_INCREMENT,
	tag_name VARCHAR(50) NOT NULL UNIQUE,
	PRIMARY KEY(id)
);

CREATE TABLE article_tag (
	id INT NOT NULL AUTO_INCREMENT,
	tag_id INT NOT NULL,
	article_id INT NOT NULL,
	PRIMARY KEY(id)
);