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
create sequence if not exists users_id_seq start with 1;
create sequence if not exists roles_id_seq start with 1;
create sequence if not exists users_roles_id_seq start with 1;

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

create table if not exists users
(
    id       integer      not null default nextval('users_id_seq'),
    email    varchar(255) not null,
    password varchar(255) not null,
    primary key (id)
    );

create table if not exists roles
(
    id   integer      not null default nextval('roles_id_seq'),
    name varchar(255) not null,
    primary key (id)
    );

create table if not exists users_roles
(
    id      integer not null default nextval('users_roles_id_seq'),
    user_id integer references users (id),
    role_id integer references roles (id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id),
    primary key (id)
    );
