drop table if exists auth_user;
drop table if exists authorities;

create table auth_user
(
    user_name varchar(255) primary key,
    user_pass varchar(255) not null
);

create table authorities
(
    user_name  varchar(255) primary key,
    user_roles varchar(255) not null,
    foreign key (user_name) references auth_user (user_name)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

insert into auth_user
values ('exampleadmin', 'exampleadmin'),
       ('exampleuser', 'exampleuser');
insert into authorities
values ('exampleadmin', 'ROLE_ADMIN'),
       ('exampleuser', 'ROLE_USER');