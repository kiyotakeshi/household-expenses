-- drop table bookmarks;
-- drop table expenses;
-- drop table categories;
-- drop table members;
-- drop table families;

insert into categories (name, rank)
values ( '食費', 1),
       -- (nextval('category_id_seq'), '消耗品', 2);
       ( '消耗品', 2);

insert into users (email, password)
values
    -- $ ./bin/spring encodepassword 1qazxsw2
    ('user1@example.com', '$2a$10$dvgQJieIslNPqBAkaZdtmurS3GamCpj0y6C58y2qbCq6gWEulxpOS'),
    -- $ ./bin/spring encodepassword 2wsxzaq1
    ('admin@example.com', '$2a$10$KQmRCEZWRXSZpY3PqJT6GuuZYOi/TFUFxItmby8I8TZPCgTio4eh');

insert into roles (name)
-- 大文字かを区別
-- com/example/householdExpenses/core/security/SecurityConfig.kt
values ('USER'),
       ('ADMIN');

insert into users_roles (user_id, role_id)
values (1, 1),
       (2, 1),
       (2, 2);

insert into members (family_id, user_id, name, birthday)
values (null, 1,'豆', '2022-11-22'),
       (null, 1,'豆2', '2024-11-23'),
       (null, 2,'baby1', '2023-02-05');

insert into expenses (category_id, member_id, name, price, memo, date, repeatable_month, repeatable_count)
values ( 1, 1, '粉ミルク', 500, '200gの缶のもの', '2022-11-23', 1, 1),
       ( 1, 1, 'おやつ', 200, null, '2022-11-23', 0, 0),
       ( 2, 2, 'おしゃぶり', 300, null, '2022-11-22', 0, 0),
       ( 2, 3, 'おしゃぶり', 300, null, '2023-02-07', 0, 0),
       ( 1, 3, '粉ミルク', 1100, '500gの缶のもの', '2023-02-07', 1, 1);
