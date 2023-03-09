create table order_detail (
    order_id integer not null,
    product_id integer not null,
    quantity integer,
    primary key (order_id, product_id),
    foreign key (order_id) references orders,
    foreign key (product_id) references product
)