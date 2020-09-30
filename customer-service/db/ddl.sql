create table credit_card(
	id bigserial primary key,
	bank_provider varchar(30) not null,
	card_number varchar not null,
	expiration_date varchar(10) not null,
	cvs_code integer not null
);

create table address(
	id bigserial primary key,
	city varchar(20) not null,
	street varchar(30) not null,
	house_number int not null,
	phone_number varchar(30) not null
);

create table customer(
	id bigserial primary key,
	name varchar(30) not null,
	surname varchar(50) not null,
	login varchar(30) not null unique,
	password varchar(30) not null,
	is_active boolean,
	credit_card_id bigint references credit_card(id) not null,
	address_id bigint references address(id) not null
);


