---- sudo -u postges -i  or  su postgres
---- createuser --pwprompt DBuser
---- createdb --owner=DBuser DBassignments
---- exit
---- psql -h 127.0.0.1 -d DBassignments -U DBuser

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

CREATE DOMAIN bytea_5mb AS bytea CHECK(length(value) <= 5000000);

CREATE TABLE assignments (
	id serial primary key,
	title varchar(50) not null,
	filename varchar(50),
	file bytea_5mb,
	professor_id int references professors(id) on delete cascade,
	max_grade int not null,
	max_group_size int
	--create_date date,
	--due_date date,
);

CREATE TABLE assignment_groups (
	assignment_id int references assignments(id) on delete cascade,
	group_id int,
	filename varchar(50),
	file bytea_5mb,
	grade numeric(4,2) default -1
);

------user uploading limits----------
CREATE OR REPLACE FUNCTION check_rows_count_limit() RETURNS TRIGGER AS $body$
DECLARE
	__cnt integer;
BEGIN
	EXECUTE 'select count(' || TG_ARGV[0] || ') from ' || TG_TABLE_NAME || ' where ' || TG_ARGV[0] || '=($1).' || TG_ARGV[0] || ' group by '||TG_ARGV[0] USING NEW into __cnt;
	IF __cnt >= TG_ARGV[1]::integer THEN
		RAISE EXCEPTION 'You reached your limit (%)', TG_ARGV[1];
	END IF;
	RETURN NEW;
END;
$body$
LANGUAGE plpgsql;

CREATE TRIGGER tr_check_group_member_limit
BEFORE INSERT ON group_members
FOR EACH ROW EXECUTE PROCEDURE check_rows_count_limit('student_id', 5);

CREATE TRIGGER tr_check_assignment_groups_limit
BEFORE INSERT ON assignment_groups
FOR EACH ROW EXECUTE PROCEDURE check_rows_count_limit('group_id', 5);

CREATE TRIGGER tr_check_assignment_limit
BEFORE INSERT ON assignments
FOR EACH ROW EXECUTE PROCEDURE check_rows_count_limit('professor_id', 8);
--------------------------------------

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

--INSERT INTO assignment_groups (assignment_id, group_id, grade) VALUES (1, 1, 8);
--INSERT INTO assignment_groups (assignment_id, group_id, grade) VALUES (2, 2, 10);
