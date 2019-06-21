CREATE DATABASE finalproject;

use finalproject;

create table users
(
  id       int auto_increment
    primary key,
  role     varchar(45) not null,
  fullname varchar(45) not null,
  username varchar(45) not null,
  email    varchar(45) not null,
  password varchar(255) not null,
  phone    varchar(45) not null,
  id_code  varchar(8)  not null,
  constraint email_UNIQUE
  unique (email),
  constraint login_UNIQUE
  unique (username),
  constraint phone_UNIQUE
  unique (phone),
  constraint users_id_code_uindex
  unique (id_code)
);

create table tax_return
(
  tax_return_id       int auto_increment
    primary key,
  user_id             int         not null,
  inspector_id        int         not null,
  category_id         varchar(45) not null,
  date                datetime    not null,
  wage                double      not null,
  military_collection double      not null,
  income_tax          double      not null,
  constraint inspector_id
  foreign key (inspector_id) references users (id),
  constraint user_id
  foreign key (user_id) references users (id)
);

create table action_report
(
  report_id     int auto_increment
    primary key,
  action        varchar(45)  not null,
  message       varchar(300) null,
  date          datetime     not null,
  tax_return_id int          not null,
  constraint tax_return_id
  foreign key (tax_return_id) references tax_return (tax_return_id)
);

create index tax_return_id_idx
  on action_report (tax_return_id);

create table change_inspector_report
(
  id                    int auto_increment
    primary key,
  user_id               int          not null,
  previous_inspector_id int          not null,
  new_inspector_id      int          null,
  message               varchar(300) null,
  status                varchar(45)  not null,
  date                  datetime     not null,
  constraint change_inspector_user_id
  foreign key (user_id) references tax_return (user_id)
);

create index change_inspector_user_id_idx
  on change_inspector_report (user_id);

create table history
(
  tax_return_id int          not null,
  user_id       int          not null,
  action        varchar(45)  not null,
  message       varchar(300) null,
  date          datetime     not null,
  history_id    int auto_increment
    primary key,
  constraint history_tax_return_id
  foreign key (tax_return_id) references tax_return (tax_return_id),
  constraint history_user_id
  foreign key (user_id) references tax_return (user_id)
);

create index tax_return_id_idx
  on history (tax_return_id);

create index user_id_idx
  on history (user_id);

create index inspector_id_idx
  on tax_return (inspector_id);

create index user_id_idx
  on tax_return (user_id);

INSERT INTO users (id, role, fullname, username, email, password, phone, id_code)
VALUES (DEFAULT,
        'ADMIN',
        'admin',
        'admin',
        'admin@gmail.com',
        '$2a$04$Upjk0iO1PDvwtwEcXWY9SOiDmsBhgRTYnSOnndpur31JoIn5vSTKC',
        '+3800000000000',
        '00000001');
INSERT INTO users (id, role, fullname, username, email, password, phone, id_code) VALUES (DEFAULT, 'INSPECTOR', 'Інспектор №1', 'inspector0', 'inspector0@gmail.com', '$2a$04$Upjk0iO1PDvwtwEcXWY9SOiDmsBhgRTYnSOnndpur31JoIn5vSTKC', '+3800000000001', '00000002');
INSERT INTO users (id, role, fullname, username, email, password, phone, id_code) VALUES (DEFAULT, 'INSPECTOR', 'Інспектор №2', 'inspector1', 'inspector1@gmail.com', '$2a$04$Upjk0iO1PDvwtwEcXWY9SOiDmsBhgRTYnSOnndpur31JoIn5vSTKC', '+3800000000002', '00000003');
INSERT INTO users (id, role, fullname, username, email, password, phone, id_code) VALUES (DEFAULT, 'INSPECTOR', 'Інспектор №3', 'inspector2', 'inspector2@gmail.com', '$2a$04$Upjk0iO1PDvwtwEcXWY9SOiDmsBhgRTYnSOnndpur31JoIn5vSTKC', '+3800000000003', '00000004');
