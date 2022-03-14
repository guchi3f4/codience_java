INSERT INTO user(name, email, password, created, role) VALUES
('高橋', 'a@a', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '2022-1-12 15:00:00', 'ROLE_GENERAL'),
('ダニエル', 'b@b', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa', '2022-2-12 15:00:00', 'ROLE_ADMIN');

INSERT INTO article(user_id, title, link, summary, created, updated) VALUES
(1, 'hello', 'https://www.google.com/', 'おはよう', '2022-1-12 15:00:00', '2022-1-12 15:00:00'),
(1, 'おはよう', 'https://www.google.com/',　'おはよう', '2022-1-12 15:00:00', '2022-1-12 15:00:00'),
(2, 'こんにちは', 'https://www.google.com/', 'おはよう', '2022-2-12 15:00:00', '2022-1-12 15:00:00');