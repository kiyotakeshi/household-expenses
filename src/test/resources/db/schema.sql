-- drop table bookmarks;
-- drop table expenses;
-- drop table categories;
-- drop table members;
-- drop table families;

-- drop sequence expense_id_seq;
-- drop sequence category_id_seq;
-- drop sequence bookmark_id_seq;
-- drop sequence member_id_seq;
-- drop sequence family_id_seq;

create sequence if not exists expense_id_seq start with 1;
create sequence if not exists category_id_seq start with 1;
create sequence if not exists bookmark_id_seq start with 1;
create sequence if not exists member_id_seq start with 1;
create sequence if not exists family_id_seq start with 1;

create table if not exists categories
(
    id   integer not null default nextval('category_id_seq') primary key,
    name varchar(255),
    rank integer
    );

create table if not exists families
(
    id   integer not null default nextval('family_id_seq') primary key,
    name varchar(255)
    );

create table if not exists members
(
    id        integer      not null default nextval('member_id_seq') primary key,
    family_id integer,
    name      varchar(255) not null,
    birthday  date         not null,

    constraint fk_6a592267
    foreign key (family_id) references families (id)
    );

create table if not exists expenses
(
    id               integer not null default nextval('expense_id_seq') primary key,
    category_id      integer not null,
    member_id        integer not null,
    name             varchar(510),
    price            integer not null,
    memo             varchar(510),
    -- not keyword
    -- select count(*) from pg_get_keywords() where word = 'date';
    date             date    not null,
    repeatable_month integer,
    repeatable_count integer,

    constraint fk_cd0468a2
    foreign key (category_id) references categories (id),
    constraint fk_c2e35b3e
    foreign key (member_id) references members (id)
    );

create table if not exists bookmarks
(
    id         integer not null default nextval('bookmark_id_seq') primary key,
    expense_id integer not null,

    constraint fk_98385b03
    foreign key (expense_id) references expenses (id)
    );
