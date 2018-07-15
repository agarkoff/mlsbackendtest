--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 10.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: parts; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.parts (number, name, vendor, qty, shipped, received) VALUES ('4', 'Name 12', 'Vendor 20', 8, '2018-05-29', '2018-04-28');
INSERT INTO public.parts (number, name, vendor, qty, shipped, received) VALUES ('3', 'Name 11', 'Vendor 19', 5, '2018-05-30', '2018-04-29');
INSERT INTO public.parts (number, name, vendor, qty, shipped, received) VALUES ('5', 'Name 13', 'Vendor 21', 13, '2018-05-28', '2018-04-30');
INSERT INTO public.parts (number, name, vendor, qty, shipped, received) VALUES ('1&<', 'Name 9<&', 'Vendor 17', 1, '2018-05-24', '2018-04-26');
INSERT INTO public.parts (number, name, vendor, qty, shipped, received) VALUES ('7', 'Name 15', 'Vendor 23', 34, '2018-05-26', '2018-04-25');
INSERT INTO public.parts (number, name, vendor, qty, shipped, received) VALUES ('6', 'Name 14', 'Vendor 22', 21, '2018-05-27', '2018-04-24');
INSERT INTO public.parts (number, name, vendor, qty, shipped, received) VALUES ('8', 'Name 16', 'Vendor 24', 55, '2018-05-25', '2018-04-23');
INSERT INTO public.parts (number, name, vendor, qty, shipped, received) VALUES ('2', 'РРјСЏ 10', 'Vendor 18', 3, '2018-05-31', '2018-04-22');


--
-- PostgreSQL database dump complete
--

