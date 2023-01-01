create table products(
    id bigserial primary key,
    title varchar(255),
    price int
);

insert into products (title, price) values
    ('Whisky',3000),
    ('Brandy',1850),
    ('Cola',120);

create table users(
    id          bigserial primary key,
    username    varchar(255) not null,
    password    varchar(255) not null
);

insert into users(username,password) values
    ('John','$2a$12$28zpf6w.gUvItsy96H7PfOwzQwXuiDN9cxsbe2UfP/gBNCt3AZze2'),
    ('Jack','$2a$12$CARphZ35IECBzwxrWTJ9depXFoOKPwaMXJ1hukt/oP.SC1n61YZFK'),
    ('Jill','$2a$12$81NUdvfekvTnnA9drJtol.u40A2NNRgOCEMj3Zsf1SmBiiUQNrGEa');

create table roles(
    id      bigserial primary key,
    name    varchar(50) not null
);

insert into roles (name) values
    ('ROLE_USER'),
    ('ROLE_ADMIN'),
    ('ROLE_MANAGER');

create table users_roles(
    user_id bigint not null references users (id),
    role_id bigint not null references roles (id),
    primary key(user_id,role_id)
);

insert into users_roles (user_id,role_id) values
    (1,1),
    (2,2),
    (3,3);











