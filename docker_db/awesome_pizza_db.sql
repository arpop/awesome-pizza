create database awesome_pizza;
use awesome_pizza;

create table pizza (
    id bigint not null auto_increment,
    name varchar(30) not null unique,
    ingredients varchar(200) not null,
    deleted tinyint default 0,
    primary key (id)
);

create table orders (
    id bigint not null auto_increment,
    placement_time datetime not null,
    order_status varchar(11) not null,
    table_number int,
    primary key (id)
);

create table pizza_orders (
    id bigint not null auto_increment,
    pizza_id bigint not null,
    order_id bigint not null,
    note varchar(200),
    primary key (id),
    foreign key (pizza_id) references pizza(id),
    foreign key (order_id) references orders(id)
);

insert into pizza (name, ingredients)
values ('Margherita', 'Pomodoro, mozzarella, origano'),
       ('Prosciutto e funghi', 'Pomodoro, mozzarella, cotto. funghi'),
       ('Diavola', 'Pomodoro, mozzarella, salame piccante');

insert into orders (placement_time, order_status, table_number)
values ('2023-10-15 19:40:10', 'PLACED', 17),
       ('2023-10-15 19:22:10', 'PLACED', 12);

insert into pizza_orders (pizza_id, order_id, note)
values (3, 1, 'bianca, ben cotta'),
       (2, 2, NULL);