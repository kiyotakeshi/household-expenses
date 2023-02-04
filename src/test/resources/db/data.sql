delete from expenses;
delete from members;
delete from categories;

INSERT INTO categories (id, name, rank)
VALUES (1, '食費', 1),
       (2, '消耗品', 2);

INSERT INTO members (id, family_id, name, birthday)
VALUES (1, null, '豆', '2022-11-22'),
       (2, null, '豆2', '2022-11-23');

INSERT INTO expenses (id, category_id, member_id, name, price, memo, date, repeatable_month, repeatable_count)
VALUES (1, 1, 1, '粉ミルク', 500, '200gの缶のもの', '2022-11-23', 1, 1),
       (2, 2, 1, 'おしゃぶり', 300, null, '2022-11-24', 0, 0),
       (3, 1, 2, 'おやつ', 150, null, '2022-11-23', 0, 0);

INSERT INTO users (email, password)
VALUES
    -- $ ./bin/spring encodepassword 1qazxsw2
    ('user1@example.com', '$2a$10$OnVJL2f7jVyirYRYs/sIUe0T5yyzpRz5jqNBtF77rP2njA0tGzHOi'),
    -- $ ./bin/spring encodepassword 2wsxzaq1
    ('admin@example.com', '$2a$10$KQmRCEZWRXSZpY3PqJT6GuuZYOi/TFUFxItmby8I8TZPCgTio4eh');

INSERT INTO roles (name)
VALUES ('USER'),
       ('ADMIN');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 1),
       (2, 2);