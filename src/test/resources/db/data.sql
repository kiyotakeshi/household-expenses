delete from expenses;
delete from members;
delete from categories;
delete from users_roles;
delete from roles;
delete from users;

insert into categories (id, name, rank)
values (1, '食費', 1),
       -- (nextval('category_id_seq'), '消耗品', 2);
       (2, '消耗品', 2);

insert into users (id, email, password)
values
    -- $ ./bin/spring encodepassword 1qazxsw2
    (1, 'user1@example.com', '$2a$10$dvgQJieIslNPqBAkaZdtmurS3GamCpj0y6C58y2qbCq6gWEulxpOS'),
    -- $ ./bin/spring encodepassword 2wsxzaq1
    (2, 'admin@example.com', '$2a$10$KQmRCEZWRXSZpY3PqJT6GuuZYOi/TFUFxItmby8I8TZPCgTio4eh');

insert into roles (id, name)
values (1, 'USER'),
       (2, 'ADMIN');

insert into users_roles (id, user_id, role_id)
values (1, 1, 1),
       (2, 2, 1),
       (3, 2, 2);

insert into members (id, family_id, user_id, name, birthday)
values (1, null, 1,'豆', '2022-11-22'),
       (2, null, 1,'豆2', '2024-11-23'),
       (3, null, 2,'baby1', '2023-02-05');

insert into expenses (id, category_id, member_id, name, price, memo, date, repeatable_month, repeatable_count)
values (1, 1, 1, '粉ミルク', 500, '200gの缶のもの', '2022-11-23', 1, 1),
       (2, 1, 1, 'おやつ', 200, null, '2022-11-23', 0, 0),
       (3, 2, 3, 'おしゃぶり', 300, null, '2022-11-22', 0, 0);
