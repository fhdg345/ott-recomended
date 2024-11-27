

create user `ott`@`localhost` identified by 'ott';
create database ott CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
grant all privileges on ott.* to `ott`@`localhost` ;



create user `ott`@`%` identified by 'ott';
create database ott CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
grant all privileges on ott.* to `ott`@`%` ;


--- 덤프 명령어

mysqldump -u ott -p ott > ott_dump.sql


mysql -u ott -p ott < ott_dump.sql
