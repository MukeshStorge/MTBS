--theater

INSERT INTO mtbs.theater
(id, "name", contact_number, address, pin_code)
VALUES(1, 'Sathyam Cinemas Main', '8765432190', 'Chennai', '6000987') ON CONFLICT DO NOTHING;


--branch

INSERT INTO mtbs.branch
(id, "name", theater_id, contact_number, address, pin_code)
VALUES(1, 'Sathyam North', 1, '8927177662', 'Chennai', '600977') ON CONFLICT DO NOTHING;


-- Users

INSERT INTO mtbs."user"
(id, first_name, last_name, email, active_status)
VALUES(1, 'Mukesh', 'Storge', 'mukesh@gmail.com', true) ON CONFLICT DO NOTHING;
INSERT INTO mtbs."user"
(id, first_name, last_name, email, active_status)
VALUES(2, 'Ram', 'Kanna', 'ram@gmail.com', true) ON CONFLICT DO NOTHING;
INSERT INTO mtbs."user"
(id, first_name, last_name, email, active_status)
VALUES(3, 'Krishna', 'Dev', 'krishna@gmail.com', true) ON CONFLICT DO NOTHING;

--Movies

INSERT INTO mtbs.movies
(id, "name", running_time_hour, "language")
VALUES(1, 'Vertigo', 1.5, 'English') ON CONFLICT DO NOTHING;
INSERT INTO mtbs.movies
(id, "name", running_time_hour, "language")
VALUES(2, 'The Innocents', 2.0, 'English') ON CONFLICT DO NOTHING;
INSERT INTO mtbs.movies
(id, "name", running_time_hour, "language")
VALUES(3, 'The Deer Hunter', 1.5, 'English') ON CONFLICT DO NOTHING;
INSERT INTO mtbs.movies
(id, "name", running_time_hour, "language")
VALUES(5, 'Inside Out', 2.5, 'English') ON CONFLICT DO NOTHING;
INSERT INTO mtbs.movies
(id, "name", running_time_hour, "language")
VALUES(4, 'Soul', 3.0, 'English') ON CONFLICT DO NOTHING;

--Screens

INSERT INTO mtbs.screens
(id, "name", branch_id, capacity, "size", status)
VALUES(1, 'Screen1', 1, 120, '70MM', 'active') ON CONFLICT DO NOTHING;
INSERT INTO mtbs.screens
(id, "name", branch_id, capacity, "size", status)
VALUES(2, 'Screen2', 1, 120, '70MM', 'active') ON CONFLICT DO NOTHING;
INSERT INTO mtbs.screens
(id, "name", branch_id, capacity, "size", status)
VALUES(3, 'Screen3', 1, 120, '70MM', 'active') ON CONFLICT DO NOTHING;
INSERT INTO mtbs.screens
(id, "name", branch_id, capacity, "size", status)
VALUES(4, 'Screen4', 1, 120, '70MM', 'active') ON CONFLICT DO NOTHING;
INSERT INTO mtbs.screens
(id, "name", branch_id, capacity, "size", status)
VALUES(5, 'Screen5', 1, 120, '70MM', 'active') ON CONFLICT DO NOTHING;
INSERT INTO mtbs.screens
(id, "name", branch_id, capacity, "size", status)
VALUES(6, 'Screen6', 1, 120, '70MM', 'active') ON CONFLICT DO NOTHING;
INSERT INTO mtbs.screens
(id, "name", branch_id, capacity, "size", status)
VALUES(7, 'Screen7', 1, 120, '70MM', 'active') ON CONFLICT DO NOTHING;
INSERT INTO mtbs.screens
(id, "name", branch_id, capacity, "size", status)
VALUES(8, 'Screen8', 1, 120, '70MM', 'active') ON CONFLICT DO NOTHING;
INSERT INTO mtbs.screens
(id, "name", branch_id, capacity, "size", status)
VALUES(9, 'Screen9', 1, 120, '70MM', 'active') ON CONFLICT DO NOTHING;
INSERT INTO mtbs.screens
(id, "name", branch_id, capacity, "size", status)
VALUES(10, 'Screen10', 1, 180, '90MM', 'active') ON CONFLICT DO NOTHING;


-- show

INSERT INTO mtbs."show"
(id, movie_id, screens_id, start_time, end_time)
VALUES(1000, 5, 9, '2008-09-24 13:36:27.000', '2008-09-24 09:40:23.000') ON CONFLICT DO NOTHING;
INSERT INTO mtbs."show"
(id, movie_id, screens_id, start_time, end_time)
VALUES(1001, 1, 4, '2008-07-24 20:11:55.000', '2008-11-28 04:05:35.000') ON CONFLICT DO NOTHING;
INSERT INTO mtbs."show"
(id, movie_id, screens_id, start_time, end_time)
VALUES(1002, 3, 3, '2008-01-26 23:35:37.000', '2008-12-02 05:59:01.000') ON CONFLICT DO NOTHING;
INSERT INTO mtbs."show"
(id, movie_id, screens_id, start_time, end_time)
VALUES(1003, 3, 1, '2008-10-21 17:19:15.000', '2008-10-23 17:23:44.000') ON CONFLICT DO NOTHING;
INSERT INTO mtbs."show"
(id, movie_id, screens_id, start_time, end_time)
VALUES(1004, 2, 6, '2008-01-25 13:34:54.000', '2008-12-02 05:02:42.000') ON CONFLICT DO NOTHING;
INSERT INTO mtbs."show"
(id, movie_id, screens_id, start_time, end_time)
VALUES(1005, 1, 10, '2008-07-31 23:06:32.000', '2008-03-20 17:03:57.000') ON CONFLICT DO NOTHING;
INSERT INTO mtbs."show"
(id, movie_id, screens_id, start_time, end_time)
VALUES(1006, 2, 3, '2008-10-08 03:29:26.000', '2008-09-05 17:13:48.000') ON CONFLICT DO NOTHING;
INSERT INTO mtbs."show"
(id, movie_id, screens_id, start_time, end_time)
VALUES(1007, 5, 1, '2008-07-29 12:04:55.000', '2008-04-14 02:36:03.000') ON CONFLICT DO NOTHING;
INSERT INTO mtbs."show"
(id, movie_id, screens_id, start_time, end_time)
VALUES(1008, 4, 10, '2008-03-24 04:54:26.000', '2008-01-29 16:33:28.000') ON CONFLICT DO NOTHING;
INSERT INTO mtbs."show"
(id, movie_id, screens_id, start_time, end_time)
VALUES(1009, 5, 3, '2008-11-07 22:51:48.000', '2008-07-01 09:32:49.000') ON CONFLICT DO NOTHING;

--Seats

INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(1, 1, 1, 1) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(2, 2, 1, 1) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(3, 3, 1, 1) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(4, 4, 1, 1) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(5, 5, 1, 1) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(6, 6, 1, 2) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(7, 7, 1, 2) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(8, 8, 1, 2) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(9, 9, 1, 2) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(10, 10, 1, 2) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(11, 1, 2, 1) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(12, 2, 2, 1) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(13, 3, 2, 1) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(14, 4, 2, 1) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(15, 5, 2, 1) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(16, 6, 2, 2) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(17, 7, 2, 2) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.seat
(id, "number", screens_id, "row")
VALUES(18, 8, 2, 2) ON CONFLICT DO NOTHING;

-- ShowSeats

INSERT INTO mtbs.shows_seat
(id, show_id, price, seat_id)
VALUES(1, 1000, 120.00, 1) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.shows_seat
(id, show_id, price, seat_id)
VALUES(2, 1000, 120.00, 2) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.shows_seat
(id, show_id, price, seat_id)
VALUES(3, 1000, 120.00, 3) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.shows_seat
(id, show_id, price, seat_id)
VALUES(4, 1000, 120.00, 4) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.shows_seat
(id, show_id, price, seat_id)
VALUES(5, 1000, 120.00, 5) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.shows_seat
(id, show_id, price, seat_id)
VALUES(6, 1001, 150.00, 1) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.shows_seat
(id, show_id, price, seat_id)
VALUES(7, 1001, 150.00, 2) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.shows_seat
(id, show_id, price, seat_id)
VALUES(8, 1001, 150.00, 3) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.shows_seat
(id, show_id, price, seat_id)
VALUES(9, 1001, 150.00, 4) ON CONFLICT DO NOTHING;
INSERT INTO mtbs.shows_seat
(id, show_id, price, seat_id)
VALUES(10, 1001, 150.00, 5) ON CONFLICT DO NOTHING;




