create table shop(
	id bigserial primary key,
	external_shop_name varchar(50) not null unique,
	created timestamptz not null
);

create table product(
	id bigserial primary key,
	shop_id bigint references shop(id) not null,
	product_sku varchar(50) not null unique,
	created timestamptz not null,
	modified timestamptz not null,
	price numeric not null,
	description varchar(200),
	image_url varchar(200) not null,
	is_visible boolean not null
);

create table logistical_order(
	id bigserial primary key,
	order_identifier varchar(100) not null,
	logic_status varchar(50) not null,
	created timestamptz not null,
	completion_date timestamptz
);

create table product_order(
    id bigserial primary key,
	product_id bigint references product(id) not null,
	logistical_order_id bigint references logistical_order(id) not null,
	quantity integer not null
);

