create table member(
    id int not null auto_increment,
    name varchar(32) not null,
    primary key(id)
) engine=InnoDB default charset=utf8;