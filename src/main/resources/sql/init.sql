create table roles (
  id int primary key,
  name varchar(50) unique not null
);

create table users (
  id int primary key,
  email varchar(50) unique not null,
  password varchar(200) not null,
  role_id int not null
);

create table marker (
  id int primary key,
  text varchar(500) not null,
  user_id int,
  FOREIGN KEY (user_id) REFERENCES users (id)
);