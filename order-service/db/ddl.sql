create table logistical_order(
	id bigserial primary key,
	external_customer_id bigint not null,
	external_shop_id bigint,
	order_identifier varchar(50) not null unique,
	total_amount numeric not null,
	logic_status varchar(40) not null,
	order_completion_date timestamptz,
	order_delivery_date timestamptz,
	delivery_address varchar(200) not null,
	created timestamptz not null,
	modified timestamptz not null
);

create table logistical_order_line (
	id bigserial primary key,
	logistical_order_id bigint references logistical_order(id) not null,
	product_sku varchar(50) not null,
	quantity int4,
	price numeric
);

create table payment(
	id bigserial primary key,
	logistical_order_id bigint references logistical_order(id) not null,
	external_payment_id bigint not null,
	transaction_id varchar(50) not null,
	status varchar(40) not null,
	created timestamptz not null,
	modified timestamptz not null
);
