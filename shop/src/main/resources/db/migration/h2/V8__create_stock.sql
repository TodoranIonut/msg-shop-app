create table stock (
    product_id integer not null,
    location_id integer not null,
    quantity integer,
    primary key (location_id, product_id),
    foreign key (location_id) references location,
    foreign key (product_id) references product
)