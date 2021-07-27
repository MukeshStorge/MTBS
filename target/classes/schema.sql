-- DROP SCHEMA movie_booking;

CREATE SCHEMA IF NOT EXISTS movie_booking AUTHORIZATION postgres;

-- movie_booking.booking_request definition

-- Drop table

-- DROP TABLE movie_booking.booking_request;

CREATE TABLE IF NOT EXISTS movie_booking.booking_request (
	correlation_id varchar NOT NULL,
	processed bool NOT NULL DEFAULT false,
	"data" varchar NOT NULL,
	request_time_millisec int8 NOT NULL DEFAULT (date_part('epoch'::text, now()) * 1000::double precision),
	CONSTRAINT request_log_pk PRIMARY KEY (correlation_id)
);


-- movie_booking.movie definition

-- Drop table

-- DROP TABLE movie_booking.movie;

CREATE TABLE IF NOT EXISTS movie_booking.movie (
	id int8 NOT NULL,
	"name" varchar NOT NULL,
	running_time_hour float4 NOT NULL,
	"language" varchar NOT NULL,
	CONSTRAINT movie_pk PRIMARY KEY (id),
	CONSTRAINT movie_un UNIQUE (name, language)
);


-- movie_booking.movie_hall definition

-- Drop table

-- DROP TABLE movie_booking.movie_hall;

CREATE TABLE IF NOT EXISTS movie_booking.movie_hall (
	id int8 NOT NULL,
	"name" varchar NOT NULL,
	address varchar NOT NULL,
	CONSTRAINT movie_hall_pk PRIMARY KEY (id),
	CONSTRAINT movie_hall_un UNIQUE (name)
);


-- movie_booking."user" definition

-- Drop table

-- DROP TABLE movie_booking."user";

CREATE TABLE IF NOT EXISTS movie_booking."user" (
	id int8 NOT NULL,
	first_name varchar NOT NULL,
	last_name varchar,
	email varchar,
	active_status bool NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (id),
	CONSTRAINT user_un UNIQUE (email)
);


-- movie_booking.seat definition

-- Drop table

-- DROP TABLE movie_booking.seat;

CREATE TABLE IF NOT EXISTS movie_booking.seat (
	id int8 NOT NULL,
	"number" int4 NOT NULL,
	movie_hall_id int8 NOT NULL,
	"row" int4 NOT NULL,
	CONSTRAINT pk_movie_hall_seats_id PRIMARY KEY (id),
	CONSTRAINT fk_movie_hall_seats_movie_hall FOREIGN KEY (movie_hall_id) REFERENCES movie_booking.movie_hall(id)
);


-- movie_booking."show" definition

-- Drop table

-- DROP TABLE movie_booking."show";

CREATE TABLE IF NOT EXISTS movie_booking."show" (
	id int8 NOT NULL,
	movie_id int8 NOT NULL,
	movie_hall_id int8 NOT NULL,
	start_time timestamp(0) NOT NULL,
	end_time timestamp(0) NOT NULL,
	CONSTRAINT movie_show_pk PRIMARY KEY (id),
	CONSTRAINT movie_show_un UNIQUE (movie_hall_id, movie_id, start_time, end_time),
	CONSTRAINT movie_show_fk FOREIGN KEY (movie_hall_id) REFERENCES movie_booking.movie_hall(id),
	CONSTRAINT movie_show_fk_1 FOREIGN KEY (movie_id) REFERENCES movie_booking.movie(id)
);


-- movie_booking.show_seat definition

-- Drop table

-- DROP TABLE movie_booking.show_seat;

CREATE TABLE IF NOT EXISTS movie_booking.show_seat (
	id int8 NOT NULL,
	show_id int8 NOT NULL,
	price numeric(7,2) NOT NULL,
	seat_id int8 NOT NULL,
	CONSTRAINT seat_pk PRIMARY KEY (id),
	CONSTRAINT unq_show_seat UNIQUE (show_id, seat_id),
	CONSTRAINT fk_seat_show FOREIGN KEY (show_id) REFERENCES movie_booking.show(id),
	CONSTRAINT fk_show_seat_id FOREIGN KEY (seat_id) REFERENCES movie_booking.seat(id)
);


-- movie_booking.blocked_seat definition

-- Drop table

-- DROP TABLE movie_booking.blocked_seat;

CREATE TABLE IF NOT EXISTS movie_booking.blocked_seat (
	id int8 NOT NULL,
	user_id int8 NOT NULL,
	show_seat_id int8 NOT NULL,
	blocked_time timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT blocked_seat_pk PRIMARY KEY (id),
	CONSTRAINT unq_blocked_seats UNIQUE (user_id, show_seat_id),
	CONSTRAINT fk_blocked_seats_seat FOREIGN KEY (show_seat_id) REFERENCES movie_booking.show_seat(id),
	CONSTRAINT fk_blocked_seats_user FOREIGN KEY (user_id) REFERENCES movie_booking."user"(id)
);


-- movie_booking.booked_seat definition

-- Drop table

-- DROP TABLE movie_booking.booked_seat;

CREATE TABLE IF NOT EXISTS movie_booking.booked_seat (
	id int8 NOT NULL,
	user_id int8 NOT NULL,
	show_seat_id int8 NOT NULL,
	booked_time timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT booking_pk PRIMARY KEY (id),
	CONSTRAINT unq_reserved_seats UNIQUE (user_id, show_seat_id),
	CONSTRAINT fk_reserved_seats_seat FOREIGN KEY (show_seat_id) REFERENCES movie_booking.show_seat(id),
	CONSTRAINT reserved_seats_fk FOREIGN KEY (user_id) REFERENCES movie_booking."user"(id)
);

-- movie_booking.seat_availability source

CREATE OR REPLACE VIEW movie_booking.seat_availability
AS SELECT s.id,
    s.show_id,
    s.price,
    s.seat_id,
        CASE
            WHEN bl.id IS NOT NULL OR bk.id IS NOT NULL THEN 'false'::text
            ELSE 'true'::text
        END AS available
   FROM movie_booking.show_seat s
     LEFT JOIN movie_booking.blocked_seat bl ON bl.show_seat_id = s.id
     LEFT JOIN movie_booking.booked_seat bk ON bk.show_seat_id = s.id;
    
-- movie_booking.hibernate_sequence definition

-- DROP SEQUENCE movie_booking.hibernate_sequence;

CREATE SEQUENCE IF NOT EXISTS movie_booking.hibernate_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;    

-- Remove timedout records

CREATE OR REPLACE FUNCTION movie_booking.remove_timedout_records()
 RETURNS integer
AS 'BEGIN
		DELETE FROM movie_booking.blocked_seat WHERE blocked_time < now() - INTERVAL ''2 minutes'';
		return 1;
	END;'
LANGUAGE plpgsql;

-- Valadte requests

CREATE OR REPLACE FUNCTION movie_booking.validate_concurrent_requests(corr_id character varying)
 RETURNS boolean
AS '
begin
	 return(with concurrent_requests as(
		-- select all requests reached at same time that are not processed
		select br2.correlation_id, br2.data::jsonb, br2.request_time_millisec 
		from movie_booking.booking_request br2
		where br2.request_time_millisec = (
				-- select current request time
				select br.request_time_millisec 
				from movie_booking.booking_request br 
				where br.correlation_id::varchar = corr_id
			)
		and br2.processed = false
	),
	unnested_seats as (
		-- unnest seats
		select cr.correlation_id , jsonb_array_elements(cr.data->''showSeatIds'')::int8 as "showseat" 
		from concurrent_requests cr
	),
	corr_id_having_same_seats as(
		-- select all correlation_ids having same show seats
		select correlation_id 
		from unnested_seats us where us.showSeat in (
			-- select showseats of current correlation id 
			select us2.showseat
			from unnested_seats us2
			where us2.correlation_id::varchar = corr_id
		)	
	)
	select (select cr.correlation_id
		from corr_id_having_same_seats chs 
		inner join concurrent_requests cr on cr.correlation_id = chs.correlation_id
		order by  jsonb_array_length(cr.data->''showSeatIds'') desc 
		limit 1)::varchar = corr_id);
end;'
LANGUAGE plpgsql;
