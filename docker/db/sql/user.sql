drop table if exists auth_user; -- auth_userテーブルがすでに存在すれば削除する

create table auth_user (
    user_name varchar(32) primary key,
    user_pass varchar(32)
);
