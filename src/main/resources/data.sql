INSERT INTO user(name, email, password, profile_image_id, created, role) VALUES
('高橋', 'a@a', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', 'user_icon1.jpg', '2022-1-12 15:00:00', 'ROLE_GENERAL'),
('ダニエル', 'b@b', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', null, '2022-2-12 15:00:00', 'ROLE_ADMIN');

INSERT INTO category(category_name) VALUES ('カテゴリー1');

INSERT INTO tag(tag_name) VALUES ('タグ1'), ('タグ2');

INSERT INTO article(user_id, category_id, title, link, summary, created, updated) VALUES
(1, 1, 'hello', 'https://www.google.com/', 'おはよう', '2022-1-12 15:00:00', '2022-1-12 15:00:00'),
(1, 1, 'おはよう', 'https://www.google.com/', 'おはよう', '2022-1-12 15:00:00', '2022-1-12 15:00:00'),
(2, 1, 'こんにちは', 'https://www.google.com/', 'おはよう', '2022-2-12 15:00:00', '2022-1-12 15:00:00');

INSERT INTO article_tag(article_id, tag_id) VALUES
(1, 1),
(1, 2),
(2, 1),
(3, 2);