create table users (
  id        char(16) primary key,
  password char(16) not null,
  name      char(16) not null
);

create table boards (
  idx       integer primary key,
  title     char(100) not null,
  context  char(2000) not null,
  writer   char(16) not null
);

insert into users values('admin', '1234', 'admin');

insert into boards values(1, 'title', 'context', 'admin');
insert into boards values(2, 'test title', 'context', 'admin');
insert into boards values(3, 'test title two', 'context', 'admin');
insert into boards values(4, 'this is test title', 'context', 'admin');

drop table boards;
drop table users;