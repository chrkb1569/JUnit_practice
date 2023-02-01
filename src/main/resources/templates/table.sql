drop table if exists Book;
create table Book (
                      id bigint not null auto_increment,
                      author varchar(10) not null,
                      content longtext not null,
                      title varchar(50) not null,
                      primary key (id)
);