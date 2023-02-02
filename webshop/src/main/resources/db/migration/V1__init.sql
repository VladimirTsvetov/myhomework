create table products(
    id bigserial    primary key,
    title           varchar(255),
    price           int,
    created_at      timestamp default current_timestamp,
    updated_at      timestamp default current_timestamp
);


create table cart(
    id bigserial primary key,
    product_id bigint not null references products (id)
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
    ('John','$2y$10$Ihn69y6avtAKqHbEYyXqQeydVsFLgL2zr0la/weaeZxbcGNTS1ghi'),
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

create table orders(
    id                  bigserial primary key,
    user_id             bigint not null references users (id),
    total_price         int not null,
    address             varchar(255),
    phone               varchar(255),
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp

);

create table order_items(
    id                  bigserial primary key,
    product_id          bigint not null references products (id),
    order_id            bigint  references orders (id),
    quantity            int not null,
    price_per_product   int not null,
    price               int not null,
    created_at          timestamp default current_timestamp,
    updated_at          timestamp default current_timestamp
);
















