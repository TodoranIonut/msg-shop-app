create table location (
    id integer generated by default as identity,
    name varchar(255),
    country_address varchar(255),
    city_address varchar(255),
    province_address varchar(255),
    street_address varchar(255),
    primary key (id)
)