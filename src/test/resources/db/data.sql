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
