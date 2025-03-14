--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2
-- Dumped by pg_dump version 17.2

-- Started on 2025-03-14 23:44:11

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 4900 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 81971)
-- Name: operations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.operations (
    oper_id uuid,
    user_id uuid,
    oper_date timestamp without time zone,
    oper_type integer,
    oper_amount integer
);


ALTER TABLE public.operations OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 81966)
-- Name: user_balance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_balance (
    user_id uuid NOT NULL,
    balance integer
);


ALTER TABLE public.user_balance OWNER TO postgres;

--
-- TOC entry 4894 (class 0 OID 81971)
-- Dependencies: 218
-- Data for Name: operations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.operations (oper_id, user_id, oper_date, oper_type, oper_amount) FROM stdin;
7362954e-59da-495e-ab9b-7f996124bc85	38fabb96-0fc0-4ce8-b2bb-c6c90e957063	2025-03-12 23:05:48.198743	0	1000
05344a6c-27e3-4253-9c1a-aa41fd6c95ef	38fabb96-0fc0-4ce8-b2bb-c6c90e957063	2025-03-12 23:09:11.640129	0	1000
f690da51-5424-49e1-b55b-a66945c549d4	38fabb96-0fc0-4ce8-b2bb-c6c90e957063	2025-03-12 23:09:41.413256	0	1001
36705881-aa14-4eaa-9e54-d035a5decca7	38fabb96-0fc0-4ce8-b2bb-c6c90e957063	2025-03-13 23:34:06.47753	0	10
8c91d306-a350-4432-896e-54afc9000950	38fabb96-0fc0-4ce8-b2bb-c6c90e957063	2025-03-13 23:35:36.845239	0	1000
a074247b-cb08-44b7-b035-d07e31d142bc	38fabb96-0fc0-4ce8-b2bb-c6c90e957063	2025-03-13 23:36:23.575424	0	10
764d9d02-d3f7-4c06-bc32-f8f8d9100f0b	38fabb96-0fc0-4ce8-b2bb-c6c90e957063	2025-03-13 23:41:47.395456	0	10
2ec751f5-e724-4564-b893-269599f3b5da	38fabb96-0fc0-4ce8-b2bb-c6c90e957063	2025-03-13 23:41:57.178089	0	1011
\.


--
-- TOC entry 4893 (class 0 OID 81966)
-- Dependencies: 217
-- Data for Name: user_balance; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_balance (user_id, balance) FROM stdin;
38fabb96-0fc0-4ce8-b2bb-c6c90e957063	92000
\.


--
-- TOC entry 4746 (class 2606 OID 81970)
-- Name: user_balance user_balance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_balance
    ADD CONSTRAINT user_balance_pkey PRIMARY KEY (user_id);


--
-- TOC entry 4747 (class 2606 OID 81974)
-- Name: operations operations_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.operations
    ADD CONSTRAINT operations_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.user_balance(user_id);


-- Completed on 2025-03-14 23:44:11

--
-- PostgreSQL database dump complete
--

