-- DROP SCHEMA mtbs;

CREATE SCHEMA IF NOT EXISTS mtbs AUTHORIZATION postgres;


-- mtbs.theater definition

-- Drop table

-- DROP TABLE mtbs.theater;

CREATE TABLE IF NOT EXISTS mtbs.theater (
	id int8 NOT NULL,
	"name" varchar NULL,
	contact_number varchar NULL,
	address varchar NULL,
	pin_code varchar NULL,
	CONSTRAINT theater_pk PRIMARY KEY (id)
);


-- Drop table

-- DROP TABLE mtbs.branch;

CREATE TABLE IF NOT EXISTS mtbs.branch (
	id int8 NOT NULL,
	"name" varchar NULL,
	theater_id int8 NULL,
	contact_number varchar NULL,
	address varchar NULL,
	pin_code varchar NULL,
	CONSTRAINT branch_pk PRIMARY KEY (id),
	CONSTRAINT branch_fk FOREIGN KEY (id) REFERENCES mtbs.theater(id)
);


-- mtbs.screens definition

-- Drop table

-- DROP TABLE mtbs.screens;

CREATE TABLE IF NOT EXISTS mtbs.screens (
	id int8 NOT NULL,
	"name" varchar NOT NULL,
	branch_id int8 NULL,
	capacity int8 NULL,
	"size" varchar NULL,
	status varchar NULL,
	CONSTRAINT screens_pk PRIMARY KEY (id),
	CONSTRAINT screens_un UNIQUE (name),
	CONSTRAINT screens_fk FOREIGN KEY (branch_id) REFERENCES mtbs.branch(id)
);



-- mtbs.movies definition

-- Drop table

-- DROP TABLE mtbs.movies;

CREATE TABLE IF NOT EXISTS mtbs.movies (
	id int8 NOT NULL,
	"name" varchar NOT NULL,
	running_time_hour float4 NOT NULL,
	"language" varchar NOT NULL,
	CONSTRAINT movies_pk PRIMARY KEY (id),
	CONSTRAINT movies_un UNIQUE (name, language)
);


-- mtbs.screens definition

-- Drop table

-- DROP TABLE mtbs.screens;

CREATE TABLE IF NOT EXISTS mtbs.screens (
	id int8 NOT NULL,
	"name" varchar NOT NULL,
	address varchar NOT NULL,
	CONSTRAINT screens_pk PRIMARY KEY (id),
	CONSTRAINT screens_un UNIQUE (name)
);


-- mtbs."user" definition

-- Drop table

-- DROP TABLE mtbs."user";

CREATE TABLE IF NOT EXISTS mtbs."user" (
	id int8 NOT NULL,
	first_name varchar NOT NULL,
	last_name varchar,
	email varchar,
	active_status bool NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (id),
	CONSTRAINT user_un UNIQUE (email)
);


-- mtbs.seat definition

-- Drop table

-- DROP TABLE mtbs.seat;

CREATE TABLE IF NOT EXISTS mtbs.seat (
	id int8 NOT NULL,
	"number" int4 NOT NULL,
	screens_id int8 NOT NULL,
	"row" int4 NOT NULL,
	CONSTRAINT pk_screens_seats_id PRIMARY KEY (id),
	CONSTRAINT fk_screens_seats_screens FOREIGN KEY (screens_id) REFERENCES mtbs.screens(id)
);


-- mtbs."show" definition

-- Drop table

-- DROP TABLE mtbs."show";

CREATE TABLE IF NOT EXISTS mtbs."show" (
	id int8 NOT NULL,
	movie_id int8 NOT NULL,
	screens_id int8 NOT NULL,
	start_time timestamp(0) NOT NULL,
	end_time timestamp(0) NOT NULL,
	CONSTRAINT movie_show_pk PRIMARY KEY (id),
	CONSTRAINT movie_show_un UNIQUE (screens_id, movie_id, start_time, end_time),
	CONSTRAINT movie_show_fk FOREIGN KEY (screens_id) REFERENCES mtbs.screens(id),
	CONSTRAINT movie_show_fk_1 FOREIGN KEY (movie_id) REFERENCES mtbs.movies(id)
);


-- mtbs.shows_seat definition

-- Drop table

-- DROP TABLE mtbs.shows_seat;

CREATE TABLE IF NOT EXISTS mtbs.shows_seat (
	id int8 NOT NULL,
	show_id int8 NOT NULL,
	price numeric(7,2) NOT NULL,
	seat_id int8 NOT NULL,
	CONSTRAINT seat_pk PRIMARY KEY (id),
	CONSTRAINT unq_show_seat UNIQUE (show_id, seat_id),
	CONSTRAINT fk_seat_show FOREIGN KEY (show_id) REFERENCES mtbs.show(id),
	CONSTRAINT fk_show_seat_id FOREIGN KEY (seat_id) REFERENCES mtbs.seat(id)
);


-- mtbs.blocked_seat definition

-- Drop table

-- DROP TABLE mtbs.blocked_seat;

CREATE TABLE IF NOT EXISTS mtbs.blocked_seat (
	id int8 NOT NULL,
	user_id int8 NOT NULL,
	show_seat_id int8 NOT NULL,
	blocked_time timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT blocked_seat_pk PRIMARY KEY (id),
	CONSTRAINT unq_blocked_seats UNIQUE (user_id, show_seat_id),
	CONSTRAINT fk_blocked_seats_seat FOREIGN KEY (show_seat_id) REFERENCES mtbs.shows_seat(id),
	CONSTRAINT fk_blocked_seats_user FOREIGN KEY (user_id) REFERENCES mtbs."user"(id)
);


-- mtbs.booked_seat definition

-- Drop table

-- DROP TABLE mtbs.booked_seat;

CREATE TABLE IF NOT EXISTS mtbs.booked_seat (
	id int8 NOT NULL,
	user_id int8 NOT NULL,
	show_seat_id int8 NOT NULL,
	booked_time timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT booking_pk PRIMARY KEY (id),
	CONSTRAINT unq_reserved_seats UNIQUE (user_id, show_seat_id),
	CONSTRAINT fk_reserved_seats_seat FOREIGN KEY (show_seat_id) REFERENCES mtbs.shows_seat(id),
	CONSTRAINT reserved_seats_fk FOREIGN KEY (user_id) REFERENCES mtbs."user"(id)
);

-- mtbs.seat_availability source

CREATE OR REPLACE VIEW mtbs.seat_availability
AS SELECT s.id,
    s.show_id,
    s.price,
    s.seat_id,
        CASE
            WHEN bl.id IS NOT NULL OR bk.id IS NOT NULL THEN 'false'::text
            ELSE 'true'::text
        END AS available
   FROM mtbs.shows_seat s
     LEFT JOIN mtbs.blocked_seat bl ON bl.show_seat_id = s.id
     LEFT JOIN mtbs.booked_seat bk ON bk.show_seat_id = s.id;
    
-- mtbs.hibernate_sequence definition


-- Remove blocked timedout records

CREATE OR REPLACE FUNCTION mtbs.remove_timedout_records()
 RETURNS integer
AS 'BEGIN
		DELETE FROM mtbs.blocked_seat WHERE blocked_time < now() - INTERVAL ''2 minutes'';
		return 1;
	END;'
LANGUAGE plpgsql;

-- DROP SEQUENCE mtbs.hibernate_sequence;

CREATE SEQUENCE IF NOT EXISTS mtbs.hibernate_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;    
