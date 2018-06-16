CREATE TYPE user_role AS ENUM ('professor', 'student');

CREATE TABLE users (
	id serial primary key,
	username varchar(32) unique not null,
	password varchar(128) not null,
	role user_role not null
);

CREATE TABLE professors (
	id int unique references users(id) on delete cascade,
	first_name varchar(40) not null,
	last_name varchar(40) not null
);

CREATE TABLE students (
	id int unique references users(id) on delete cascade,
	first_name varchar(40) not null,
	last_name varchar(40) not null
);

CREATE TABLE group_members (
	group_id serial,
	student_id int references students(id) on delete cascade
);

CREATE TABLE assignments (
	id serial primary key,
	title varchar(50) not null,
	description varchar(200),
	professor_id int references professors(id) on delete cascade,
	max_grade int not null,
	max_group_size int
	--create_date date,
	--due_date date,
);

CREATE TABLE assignment_groups (
	assignment_id int references assignments(id) on delete cascade,
	group_id int,
	file bytea,
	grade int
);


-----testing
INSERT INTO users (username, password, role) VALUES
('p01102', 'tawa', 'student'),
('p23201', 'tawa', 'student'),
('p99101', 'tswa', 'student'),
('drllll', 'stps', 'professor'),
('testAM', 'pplp', 'professor');

INSERT INTO professors (id, first_name, last_name) VALUES
(4, 'Test', 'Professors'),
(5, 'Tset', 'Prof');

INSERT INTO students (id, first_name, last_name) VALUES
(1, 'Stud', 'Ent'),
(2, 'LLLL', 'lllll'),
(3, 'terte', 'papsda');

INSERT INTO group_members (group_id, student_id) VALUES (1, 1), (1, 3);
INSERT INTO group_members (group_id, student_id) VALUES (2, 2);


INSERT INTO assignments (title, professor_id, max_grade) VALUES ('Texn Log', 4, 10);
INSERT INTO assignments (title, professor_id, max_grade) VALUES ('Vaseis ded', 5, 5);

INSERT INTO assignment_groups (assignment_id, group_id, grade) VALUES (1, 1, 8);
INSERT INTO assignment_groups (assignment_id, group_id, grade) VALUES (2, 2, 10);
