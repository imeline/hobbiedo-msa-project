drop table if exists member cascade;
create table member
(
    id       bigint not null auto_increment,
    uuid     varchar(10),
    password varchar(255),
    login_id varchar(255),
    primary key (id)
);
INSERT INTO member (uuid, password, login_id)
VALUES ('1234', '$2a$12$FD60QZGCtVm7Q9E55mI2HOsCrui8aTxO1mbCekf0K9zrmhQN0YE8y', '1234');