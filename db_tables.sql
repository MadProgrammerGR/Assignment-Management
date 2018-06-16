CREATE TABLE professor (
	id serial primary key,
	username varchar(32) unique not null,
	password varchar(128) not null,
	first_name varchar(40) not null,
	last_name varchar(40) not null
);

CREATE TABLE assignment (
	id serial primary key,
	title varchar(50) not null,
	max_grade int not null,
    max_group_size int,
    description bytea,
    ext varchar(5)
);

CREATE TABLE a_owner (
	p_id int references professor(id),
    a_id int references assignment(id)
);

CREATE TABLE student (
	id serial primary key,
	username varchar(32) unique not null,
	password varchar(128) not null,
	first_name varchar(40) not null,
	last_name varchar(40) not null
);

CREATE TABLE student_group (
	id serial primary key,
    s_id int references student(id)
);

CREATE TABLE a_groups (
	a_id int references assignment(id),
    g_id int references student_group(id),
    grade int
);
