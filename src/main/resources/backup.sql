--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2
-- Dumped by pg_dump version 17.2

-- Started on 2025-06-16 19:00:07

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 230 (class 1259 OID 17117)
-- Name: application; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.application (
    id_application integer NOT NULL,
    id_entrant integer,
    id_faculty integer,
    priority integer,
    application_status character varying(50)
);


ALTER TABLE public.application OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 17116)
-- Name: application_id_application_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.application_id_application_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.application_id_application_seq OWNER TO postgres;

--
-- TOC entry 4885 (class 0 OID 0)
-- Dependencies: 229
-- Name: application_id_application_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.application_id_application_seq OWNED BY public.application.id_application;


--
-- TOC entry 220 (class 1259 OID 17060)
-- Name: documents; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.documents (
    id_document integer NOT NULL,
    id_entrant integer,
    document_type character varying(100),
    path_to_scan text,
    upload_date date
);


ALTER TABLE public.documents OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 17059)
-- Name: documents_id_document_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.documents_id_document_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.documents_id_document_seq OWNER TO postgres;

--
-- TOC entry 4886 (class 0 OID 0)
-- Dependencies: 219
-- Name: documents_id_document_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.documents_id_document_seq OWNED BY public.documents.id_document;


--
-- TOC entry 218 (class 1259 OID 17053)
-- Name: entrant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.entrant (
    id_entrant integer NOT NULL,
    series_and_number character varying(50),
    name character varying(100),
    last_name character varying(100),
    surname character varying(100),
    gold_medal boolean,
    year_of_admission integer,
    final_score integer
);


ALTER TABLE public.entrant OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 17134)
-- Name: entrant_achievements; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.entrant_achievements (
    id_docs integer NOT NULL,
    id_entrant integer,
    name_achievements character varying(255),
    extra_points boolean
);


ALTER TABLE public.entrant_achievements OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 17133)
-- Name: entrant_achievements_id_docs_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.entrant_achievements_id_docs_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.entrant_achievements_id_docs_seq OWNER TO postgres;

--
-- TOC entry 4887 (class 0 OID 0)
-- Dependencies: 231
-- Name: entrant_achievements_id_docs_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.entrant_achievements_id_docs_seq OWNED BY public.entrant_achievements.id_docs;


--
-- TOC entry 217 (class 1259 OID 17052)
-- Name: entrant_id_entrant_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.entrant_id_entrant_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.entrant_id_entrant_seq OWNER TO postgres;

--
-- TOC entry 4888 (class 0 OID 0)
-- Dependencies: 217
-- Name: entrant_id_entrant_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.entrant_id_entrant_seq OWNED BY public.entrant.id_entrant;


--
-- TOC entry 222 (class 1259 OID 17074)
-- Name: exam; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.exam (
    id_exam integer NOT NULL,
    exam_name character varying(100),
    max_score integer
);


ALTER TABLE public.exam OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 17073)
-- Name: exam_id_exam_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.exam_id_exam_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.exam_id_exam_seq OWNER TO postgres;

--
-- TOC entry 4889 (class 0 OID 0)
-- Dependencies: 221
-- Name: exam_id_exam_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.exam_id_exam_seq OWNED BY public.exam.id_exam;


--
-- TOC entry 224 (class 1259 OID 17081)
-- Name: exam_results; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.exam_results (
    id_result integer NOT NULL,
    id_entrant integer,
    id_exam integer,
    score integer
);


ALTER TABLE public.exam_results OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 17080)
-- Name: exam_results_id_result_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.exam_results_id_result_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.exam_results_id_result_seq OWNER TO postgres;

--
-- TOC entry 4890 (class 0 OID 0)
-- Dependencies: 223
-- Name: exam_results_id_result_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.exam_results_id_result_seq OWNED BY public.exam_results.id_result;


--
-- TOC entry 228 (class 1259 OID 17105)
-- Name: faculty; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.faculty (
    id_faculty integer NOT NULL,
    name character varying(100),
    id_speciality integer,
    number_of_budget_places integer,
    number_of_paid_places integer,
    number_of_special_places integer
);


ALTER TABLE public.faculty OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 17104)
-- Name: faculty_id_faculty_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.faculty_id_faculty_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.faculty_id_faculty_seq OWNER TO postgres;

--
-- TOC entry 4891 (class 0 OID 0)
-- Dependencies: 227
-- Name: faculty_id_faculty_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.faculty_id_faculty_seq OWNED BY public.faculty.id_faculty;


--
-- TOC entry 234 (class 1259 OID 17146)
-- Name: order_of_enrollment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_of_enrollment (
    id_order integer NOT NULL,
    id_entrant integer,
    id_faculty integer,
    is_enrolled boolean,
    date date,
    price character varying(255),
    status character varying(100)
);


ALTER TABLE public.order_of_enrollment OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 17145)
-- Name: order_of_enrollment_id_order_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.order_of_enrollment_id_order_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.order_of_enrollment_id_order_seq OWNER TO postgres;

--
-- TOC entry 4892 (class 0 OID 0)
-- Dependencies: 233
-- Name: order_of_enrollment_id_order_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.order_of_enrollment_id_order_seq OWNED BY public.order_of_enrollment.id_order;


--
-- TOC entry 226 (class 1259 OID 17098)
-- Name: speciality; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.speciality (
    id_speciality integer NOT NULL,
    name_of_speciality character varying(100),
    score integer
);


ALTER TABLE public.speciality OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 17097)
-- Name: speciality_id_speciality_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.speciality_id_speciality_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.speciality_id_speciality_seq OWNER TO postgres;

--
-- TOC entry 4893 (class 0 OID 0)
-- Dependencies: 225
-- Name: speciality_id_speciality_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.speciality_id_speciality_seq OWNED BY public.speciality.id_speciality;


--
-- TOC entry 4687 (class 2604 OID 17120)
-- Name: application id_application; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application ALTER COLUMN id_application SET DEFAULT nextval('public.application_id_application_seq'::regclass);


--
-- TOC entry 4682 (class 2604 OID 17063)
-- Name: documents id_document; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents ALTER COLUMN id_document SET DEFAULT nextval('public.documents_id_document_seq'::regclass);


--
-- TOC entry 4681 (class 2604 OID 17056)
-- Name: entrant id_entrant; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.entrant ALTER COLUMN id_entrant SET DEFAULT nextval('public.entrant_id_entrant_seq'::regclass);


--
-- TOC entry 4688 (class 2604 OID 17137)
-- Name: entrant_achievements id_docs; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.entrant_achievements ALTER COLUMN id_docs SET DEFAULT nextval('public.entrant_achievements_id_docs_seq'::regclass);


--
-- TOC entry 4683 (class 2604 OID 17077)
-- Name: exam id_exam; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exam ALTER COLUMN id_exam SET DEFAULT nextval('public.exam_id_exam_seq'::regclass);


--
-- TOC entry 4684 (class 2604 OID 17084)
-- Name: exam_results id_result; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exam_results ALTER COLUMN id_result SET DEFAULT nextval('public.exam_results_id_result_seq'::regclass);


--
-- TOC entry 4686 (class 2604 OID 17108)
-- Name: faculty id_faculty; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.faculty ALTER COLUMN id_faculty SET DEFAULT nextval('public.faculty_id_faculty_seq'::regclass);


--
-- TOC entry 4689 (class 2604 OID 17149)
-- Name: order_of_enrollment id_order; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_of_enrollment ALTER COLUMN id_order SET DEFAULT nextval('public.order_of_enrollment_id_order_seq'::regclass);


--
-- TOC entry 4685 (class 2604 OID 17101)
-- Name: speciality id_speciality; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.speciality ALTER COLUMN id_speciality SET DEFAULT nextval('public.speciality_id_speciality_seq'::regclass);


--
-- TOC entry 4875 (class 0 OID 17117)
-- Dependencies: 230
-- Data for Name: application; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.application (id_application, id_entrant, id_faculty, priority, application_status) FROM stdin;
1	1	1	1	в обработке
2	2	1	2	в обработке
3	3	1	2	в обработке
4	4	2	3	принята
7	7	3	2	отклонена
8	8	3	3	в обработке
9	9	3	2	принята
10	10	4	2	отклонена
11	11	4	3	принята
12	12	4	2	отклонена
16	16	6	2	подана
17	17	6	3	в обработке
18	18	6	1	принята
19	19	7	3	принята
20	20	7	2	отклонена
21	21	7	2	отклонена
22	22	8	2	подана
23	23	8	1	принята
24	24	8	2	принята
25	25	9	2	подана
26	1	9	3	отклонена
27	2	9	2	отклонена
28	3	10	3	отклонена
29	4	10	1	отклонена
32	7	11	2	отклонена
33	8	11	1	в обработке
34	9	12	3	отклонена
35	10	12	1	подана
36	11	12	2	отклонена
37	12	13	1	принята
38	13	13	1	в обработке
39	14	13	2	в обработке
40	15	14	2	подана
41	16	14	1	подана
42	17	14	2	принята
43	18	15	2	в обработке
44	19	15	3	подана
45	20	15	3	принята
46	21	16	3	отклонена
47	22	16	2	в обработке
48	23	16	3	в обработке
49	24	17	3	подана
50	25	17	3	подана
51	3	17	3	подана
52	4	18	3	принята
53	15	18	2	в обработке
54	19	18	2	отклонена
55	4	19	3	принята
56	9	19	2	в обработке
57	8	19	1	в обработке
58	10	20	3	отклонена
59	14	20	3	принята
60	1	20	3	подана
\.


--
-- TOC entry 4865 (class 0 OID 17060)
-- Dependencies: 220
-- Data for Name: documents; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.documents (id_document, id_entrant, document_type, path_to_scan, upload_date) FROM stdin;
1	1	Фотография	/docs/430f533fe8.pdf	2025-04-10
2	2	ИНН	/docs/f46c7be1e9.pdf	2025-01-04
3	3	Фотография	/docs/347cd928eb.pdf	2025-01-24
4	4	СНИЛС	/docs/3f869912d7.pdf	2025-02-26
5	5	Фотография	/docs/495484adcb.pdf	2025-04-16
6	6	СНИЛС	/docs/d3be5964c0.pdf	2025-06-01
7	7	Фотография	/docs/a3d3ffe41c.pdf	2025-04-02
8	8	Паспорт	/docs/63e1f15034.pdf	2025-01-31
9	9	Военный билет	/docs/09a076d5f6.pdf	2025-05-07
10	10	Медицинская справка	/docs/e67d793a58.pdf	2025-05-13
11	11	Медицинская справка	/docs/49e58293d6.pdf	2025-03-19
12	12	ИНН	/docs/ba112abf05.pdf	2025-01-04
13	13	ИНН	/docs/eb9e0c09fc.pdf	2024-12-18
14	14	Медицинская справка	/docs/17a7cb1fba.pdf	2025-01-18
15	15	Медицинская справка	/docs/8496359e5e.pdf	2025-05-21
16	16	Фотография	/docs/3fd106ec63.pdf	2025-03-21
17	17	Аттестат	/docs/f4f5bc9df2.pdf	2025-05-21
18	18	Военный билет	/docs/64d1110799.pdf	2025-05-20
19	19	Паспорт	/docs/947d08450f.pdf	2024-12-21
20	20	Паспорт	/docs/7b8da5fcf2.pdf	2025-02-25
21	21	СНИЛС	/docs/3bc866678c.pdf	2024-12-29
22	22	Военный билет	/docs/afcfbf40a4.pdf	2025-03-09
23	23	Аттестат	/docs/171656aa09.pdf	2024-12-21
24	24	ИНН	/docs/f0e7601ca3.pdf	2025-03-31
25	25	ИНН	/docs/0b0968d8ec.pdf	2025-05-30
26	1	Паспорт	/docs/d5c1829f6e.pdf	2025-03-26
27	2	Фотография	/docs/d262201eb7.pdf	2025-04-03
28	3	Медицинская справка	/docs/cf55404ba6.pdf	2025-02-23
29	4	Медицинская справка	/docs/cc89c5090c.pdf	2025-04-28
30	5	Медицинская справка	/docs/08a906485c.pdf	2025-05-13
31	6	Медицинская справка	/docs/bf4df46f47.pdf	2025-04-12
32	7	ИНН	/docs/dd98b091f0.pdf	2025-04-13
33	8	Паспорт	/docs/647b2c6cb6.pdf	2025-03-28
34	9	Медицинская справка	/docs/e2c5c281b1.pdf	2025-05-31
35	10	Фотография	/docs/8aaa5d1e3d.pdf	2025-05-20
36	11	Паспорт	/docs/ac59e32e8a.pdf	2024-12-25
37	12	Паспорт	/docs/291662566c.pdf	2024-12-31
38	13	Фотография	/docs/9b13b86170.pdf	2025-04-15
39	14	Аттестат	/docs/e5d4b6f9e1.pdf	2025-02-02
40	15	Аттестат	/docs/bfdeed17c1.pdf	2025-04-21
41	16	Аттестат	/docs/9b879a4f52.pdf	2025-02-07
42	17	Медицинская справка	/docs/08e40c1484.pdf	2025-04-19
43	18	Медицинская справка	/docs/f493354c96.pdf	2025-06-13
44	19	Аттестат	/docs/71e32990d0.pdf	2024-12-21
45	20	СНИЛС	/docs/31da1535c5.pdf	2025-05-11
46	21	Паспорт	/docs/2ed2d7e258.pdf	2025-04-07
47	22	Медицинская справка	/docs/5d157d3c97.pdf	2025-03-15
48	23	Паспорт	/docs/096695b24c.pdf	2025-04-02
49	24	Военный билет	/docs/bb3c9dbf9c.pdf	2025-03-23
50	25	ИНН	/docs/d03345ec60.pdf	2025-02-28
51	1	ИНН	/docs/a27e72ae3e.pdf	2025-01-08
52	2	Аттестат	/docs/2488b95593.pdf	2025-03-11
53	3	ИНН	/docs/32b9196343.pdf	2025-03-06
54	4	Аттестат	/docs/03aa30e1fe.pdf	2025-01-08
55	5	СНИЛС	/docs/45d9cd47f6.pdf	2025-04-27
56	6	Медицинская справка	/docs/fd90395a35.pdf	2025-01-12
57	7	Военный билет	/docs/d33605e654.pdf	2025-01-21
58	8	Медицинская справка	/docs/6a4125a9c1.pdf	2025-01-29
59	9	Аттестат	/docs/fe99edfd45.pdf	2025-02-15
60	10	Фотография	/docs/ae3ccb4348.pdf	2025-04-09
61	11	ИНН	/docs/e05c26ff6b.pdf	2024-12-23
62	12	Военный билет	/docs/ab44d0d181.pdf	2025-04-18
63	13	ИНН	/docs/dc47da7e61.pdf	2025-02-03
64	14	Военный билет	/docs/76a896e09e.pdf	2025-01-07
65	15	СНИЛС	/docs/01d29f0d4c.pdf	2025-02-04
66	16	Паспорт	/docs/e4b14a8da7.pdf	2024-12-26
67	17	Аттестат	/docs/db786af907.pdf	2025-03-28
68	18	Медицинская справка	/docs/b80d3cf22c.pdf	2024-12-25
69	19	Паспорт	/docs/5eb59f1357.pdf	2025-05-24
70	20	Паспорт	/docs/d2853aa801.pdf	2025-02-06
71	21	Аттестат	/docs/1321668e0a.pdf	2025-02-03
72	22	Паспорт	/docs/2a0560e0ae.pdf	2025-01-03
73	23	ИНН	/docs/b40a8ada83.pdf	2025-03-10
74	24	СНИЛС	/docs/d5b0fa7ad9.pdf	2024-12-22
75	25	Военный билет	/docs/5281acc869.pdf	2025-05-02
76	1	Паспорт	/docs/1735abbd0a.pdf	2025-01-14
77	2	ИНН	/docs/4ab74b37f0.pdf	2025-03-15
78	3	Медицинская справка	/docs/9eb36e4fde.pdf	2025-03-08
79	4	Фотография	/docs/3b3d7e773c.pdf	2024-12-27
80	5	Аттестат	/docs/184c913f43.pdf	2025-03-23
81	6	Фотография	/docs/9e5d6e37e4.pdf	2025-06-12
82	7	Паспорт	/docs/f804f12792.pdf	2024-12-22
83	8	Военный билет	/docs/0f5be4ec02.pdf	2025-03-04
84	9	Аттестат	/docs/9093166908.pdf	2025-05-15
85	10	ИНН	/docs/41567e48f3.pdf	2025-02-07
86	11	Аттестат	/docs/040850b1f2.pdf	2024-12-22
87	12	СНИЛС	/docs/144a5a1a4f.pdf	2025-01-02
88	13	Медицинская справка	/docs/18b8d5d11d.pdf	2025-06-07
89	14	СНИЛС	/docs/6c0e48b1ae.pdf	2025-02-13
90	15	Паспорт	/docs/7acf8ec006.pdf	2025-02-18
91	16	Медицинская справка	/docs/fb4c1435c6.pdf	2025-06-15
92	17	Фотография	/docs/c6f44cf926.pdf	2025-05-19
93	18	ИНН	/docs/66eec1f311.pdf	2025-04-08
94	19	Паспорт	/docs/b1ad710e7d.pdf	2025-03-03
95	20	Медицинская справка	/docs/3a717cedae.pdf	2024-12-27
96	21	Паспорт	/docs/08c57ffd04.pdf	2025-01-24
97	22	Фотография	/docs/2ad1cd3ff2.pdf	2025-01-19
98	23	СНИЛС	/docs/57a38e333b.pdf	2025-04-27
99	24	Военный билет	/docs/3c5e453123.pdf	2025-03-16
100	25	СНИЛС	/docs/d3fb466f49.pdf	2025-06-04
\.


--
-- TOC entry 4863 (class 0 OID 17053)
-- Dependencies: 218
-- Data for Name: entrant; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.entrant (id_entrant, series_and_number, name, last_name, surname, gold_medal, year_of_admission, final_score) FROM stdin;
1	7971319886	Матвей	Алексеев	Артемович	f	2024	95
3	1248461636	Даниил	Кузнецов	Олегович	t	2024	94
5	9514155835	Сергей	Алексеев	Сергеевич	t	2024	63
6	8044531102	Егор	Волков	Алексеевич	t	2024	98
7	1517815284	Илья	Морозов	Дмитриевич	f	2024	92
8	6750209194	Иван	Морозов	Игоревич	f	2024	97
9	7915032766	Максим	Лебедев	Олегович	t	2024	89
10	6954919669	Кирилл	Морозов	Романович	t	2024	80
11	9664113137	Никита	Кузнецов	Александрович	f	2024	73
12	5175258664	Михаил	Смирнов	Алексеевич	f	2024	81
13	5338483092	Андрей	Волков	Игоревич	t	2024	90
14	6799153555	Матвей	Волков	Андреевич	f	2024	71
15	5044301830	Иван	Васильев	Алексеевич	f	2024	95
16	1206318833	Максим	Кузнецов	Игоревич	t	2024	96
17	1223549184	Иван	Волков	Игоревич	f	2024	76
18	5638592797	Дмитрий	Алексеев	Артемович	f	2024	77
19	4001187059	Никита	Иванов	Максимович	t	2024	90
20	2751735436	Никита	Михайлов	Романович	t	2024	64
21	4703692575	Кирилл	Фёдоров	Романович	f	2024	84
22	6962193191	Дмитрий	Фёдоров	Дмитриевич	f	2024	77
23	2206298916	Дмитрий	Морозов	Максимович	f	2024	72
24	5635693189	Кирилл	Попов	Максимович	t	2024	78
25	9429196795	Матвей	Михайлов	Александрович	t	2024	98
2	4596120123	Матвей	Иванов	Максимович	f	2024	62
4	3035540634	Артём	Петров	Олегович	f	2024	71
\.


--
-- TOC entry 4877 (class 0 OID 17134)
-- Dependencies: 232
-- Data for Name: entrant_achievements; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.entrant_achievements (id_docs, id_entrant, name_achievements, extra_points) FROM stdin;
1	1	Спортивный разряд	t
2	2	Научная публикация	t
3	3	Художественные достижения	t
4	4	Художественные достижения	t
5	5	Художественные достижения	t
6	6	Волонтерство	t
7	7	Спортивный разряд	f
8	8	Спортивный разряд	t
9	9	Знак ГТО	f
10	10	Олимпиада по информатике	t
11	11	Знак ГТО	t
12	12	Олимпиада по математике	f
13	13	Художественные достижения	t
14	14	Научная публикация	t
15	15	Знак ГТО	t
16	16	Волонтерство	t
17	17	Знак ГТО	t
18	18	ГТО	t
19	19	Художественные достижения	f
20	20	Волонтерство	t
21	21	Олимпиада по информатике	t
22	22	Спортивный разряд	t
23	23	Волонтерство	f
24	24	Художественные достижения	t
25	25	ГТО	t
26	1	Олимпиада по математике	f
27	2	Олимпиада по математике	f
28	3	ГТО	t
29	4	Спортивный разряд	t
30	5	Художественные достижения	t
31	6	Спортивный разряд	f
32	7	Олимпиада по математике	t
33	8	Олимпиада по информатике	t
34	9	Художественные достижения	t
35	10	Знак ГТО	f
36	11	Волонтерство	t
37	12	Художественные достижения	t
38	13	Олимпиада по информатике	f
39	14	Художественные достижения	t
40	15	Спортивный разряд	t
41	16	Олимпиада по информатике	t
42	17	Знак ГТО	f
43	18	Художественные достижения	f
44	19	Знак ГТО	t
45	20	Олимпиада по математике	t
46	21	Художественные достижения	t
47	22	Волонтерство	f
48	23	ГТО	t
49	24	Научная публикация	t
50	25	Олимпиада по информатике	f
51	1	Олимпиада по математике	t
52	2	Олимпиада по информатике	f
53	3	ГТО	f
54	4	ГТО	t
55	5	Научная публикация	t
56	6	Художественные достижения	f
57	7	Олимпиада по информатике	f
58	8	ГТО	t
59	9	Художественные достижения	t
60	10	Олимпиада по информатике	t
61	11	ГТО	t
62	12	ГТО	t
63	13	Спортивный разряд	f
64	14	Волонтерство	f
65	15	Научная публикация	t
66	16	Олимпиада по математике	t
67	17	Спортивный разряд	t
68	18	Знак ГТО	f
69	19	Волонтерство	t
70	20	Спортивный разряд	t
71	21	Волонтерство	t
72	22	Спортивный разряд	t
73	23	Художественные достижения	t
74	24	ГТО	f
75	25	Волонтерство	f
\.


--
-- TOC entry 4867 (class 0 OID 17074)
-- Dependencies: 222
-- Data for Name: exam; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.exam (id_exam, exam_name, max_score) FROM stdin;
1	Математика	100
2	Информатика	100
3	Русский язык	100
4	Физика	100
5	Обществознание	100
6	Иностранный язык	100
7	Биология	100
8	Химия	100
9	История	100
10	Литература	100
\.


--
-- TOC entry 4869 (class 0 OID 17081)
-- Dependencies: 224
-- Data for Name: exam_results; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.exam_results (id_result, id_entrant, id_exam, score) FROM stdin;
1	1	1	67
2	4	1	60
3	5	1	66
4	7	1	70
5	8	1	98
6	9	1	87
7	11	1	89
8	13	1	79
9	16	1	82
10	17	1	95
11	18	1	75
12	19	1	90
13	21	1	82
14	22	1	66
15	24	1	60
16	25	1	60
17	1	2	73
18	2	2	79
19	3	2	72
20	4	2	100
21	5	2	69
22	7	2	79
23	8	2	100
24	9	2	90
25	12	2	98
26	13	2	70
27	15	2	91
28	20	2	95
29	21	2	62
30	22	2	89
31	23	2	74
32	5	3	93
33	6	3	68
34	7	3	78
35	8	3	77
36	9	3	60
37	10	3	85
38	11	3	66
39	12	3	94
40	13	3	64
41	14	3	97
42	16	3	86
43	19	3	95
44	21	3	93
45	22	3	64
46	23	3	66
47	25	3	90
48	3	4	90
49	4	4	68
50	6	4	90
51	7	4	73
52	8	4	61
53	9	4	60
54	10	4	100
55	13	4	76
56	15	4	88
57	16	4	92
58	17	4	65
59	19	4	64
60	20	4	67
61	21	4	90
62	22	4	62
63	23	4	70
64	24	4	71
65	25	4	68
66	2	5	98
67	3	5	96
68	5	5	87
69	6	5	84
70	8	5	74
71	10	5	99
72	13	5	91
73	14	5	79
74	16	5	88
75	18	5	63
76	19	5	64
77	20	5	62
78	22	5	63
79	23	5	84
80	24	5	91
81	1	6	86
82	4	6	83
83	5	6	81
84	8	6	74
85	10	6	76
86	13	6	62
87	15	6	77
88	17	6	89
89	18	6	87
90	19	6	64
91	20	6	75
92	21	6	85
93	23	6	78
94	24	6	61
95	25	6	98
96	2	7	63
97	5	7	78
98	9	7	88
99	10	7	60
100	11	7	91
101	12	7	61
102	13	7	77
103	15	7	86
104	18	7	85
105	19	7	93
106	20	7	93
107	21	7	62
108	23	7	87
109	1	8	88
110	2	8	62
111	3	8	66
112	4	8	71
113	6	8	79
114	7	8	90
115	9	8	86
116	10	8	84
117	13	8	74
118	16	8	61
119	18	8	66
120	19	8	97
121	20	8	86
122	21	8	76
123	22	8	68
124	23	8	67
125	25	8	82
126	1	9	72
127	3	9	65
128	7	9	77
129	11	9	92
130	12	9	69
131	13	9	91
132	14	9	69
133	15	9	70
134	16	9	73
135	17	9	77
136	18	9	69
137	20	9	90
138	1	10	84
139	2	10	69
140	3	10	96
141	5	10	73
142	6	10	82
143	8	10	78
144	11	10	97
145	12	10	97
146	13	10	66
147	14	10	77
148	16	10	85
149	18	10	85
150	20	10	100
151	22	10	64
152	25	10	84
\.


--
-- TOC entry 4873 (class 0 OID 17105)
-- Dependencies: 228
-- Data for Name: faculty; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.faculty (id_faculty, name, id_speciality, number_of_budget_places, number_of_paid_places, number_of_special_places) FROM stdin;
2	Факультет инженерный	2	31	41	5
1	Факультет экономический	1	25	33	5
3	Факультет информационных технологий	3	33	45	5
4	Факультет информационных технологий	4	25	49	4
6	Факультет экономический	6	16	34	2
7	Факультет робототехники	7	34	33	2
8	Факультет биомедицинский	8	24	22	2
9	Факультет информационных технологий	9	21	45	3
10	Факультет компьютерных наук	10	24	22	2
11	Факультет информационных технологий	11	15	42	1
12	Факультет робототехники	12	32	21	4
13	Факультет информационных технологий	13	33	29	2
14	Факультет экономический	14	25	36	4
15	Факультет экономический	15	19	31	1
16	Факультет компьютерных наук	16	27	22	4
17	Факультет информационных технологий	17	20	28	1
18	Факультет биомедицинский	18	19	47	5
19	Факультет робототехники	19	15	23	2
20	Факультет инженерный	20	24	26	2
\.


--
-- TOC entry 4879 (class 0 OID 17146)
-- Dependencies: 234
-- Data for Name: order_of_enrollment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.order_of_enrollment (id_order, id_entrant, id_faculty, is_enrolled, date, price, status) FROM stdin;
1	1	3	t	2025-05-04	228134	отозвано
2	2	3	t	2025-05-08	\N	в резерве
3	3	3	f	2025-05-01	\N	в резерве
4	4	3	t	2025-05-04	218691	в резерве
5	5	3	t	2025-05-26	\N	в резерве
6	6	3	f	2025-05-02	\N	отозвано
7	7	3	f	2025-05-27	253274	не зачислен
8	8	3	f	2025-04-22	232099	не зачислен
9	9	3	f	2025-06-10	155178	зачислен
10	10	3	t	2025-04-26	184077	в резерве
11	11	3	t	2025-05-31	\N	в резерве
12	12	3	t	2025-06-05	125722	в резерве
13	13	3	t	2025-06-06	157807	не зачислен
14	14	3	t	2025-05-31	\N	зачислен
15	15	3	t	2025-04-29	250117	в резерве
16	16	3	t	2025-06-09	316836	зачислен
17	17	3	t	2025-05-17	\N	зачислен
18	18	3	t	2025-05-30	215253	зачислен
19	19	3	f	2025-05-18	159860	зачислен
20	20	3	f	2025-06-11	\N	в резерве
21	21	3	t	2025-04-29	165717	отозвано
22	22	3	f	2025-05-18	\N	не зачислен
23	23	3	t	2025-05-15	195248	отозвано
24	24	3	t	2025-06-05	139674	отозвано
25	25	3	f	2025-06-09	\N	зачислен
26	1	1	f	2025-06-19	0	в обработке
27	2	2	t	2025-06-12	0	в обработке
\.


--
-- TOC entry 4871 (class 0 OID 17098)
-- Dependencies: 226
-- Data for Name: speciality; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.speciality (id_speciality, name_of_speciality, score) FROM stdin;
1	Информационные системы	85
2	Прикладная информатика	82
3	Программная инженерия	88
4	Бизнес-информатика	80
5	Информационная безопасность	87
6	Искусственный интеллект	90
7	Робототехника	84
9	Управление данными	81
10	Веб-технологии	79
11	Мобильная разработка	86
12	Киберфизические системы	89
13	Технологии блокчейн	91
14	Компьютерный дизайн	78
15	Нейротехнологии	92
17	Технологии VR/AR	77
18	Интернет вещей	84
19	Системная аналитика	83
20	Цифровой маркетинг	76
8	Биоинформатика	270
16	Квантовые вычисления	275
21	Филосовские мысли	260
\.


--
-- TOC entry 4894 (class 0 OID 0)
-- Dependencies: 229
-- Name: application_id_application_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.application_id_application_seq', 60, true);


--
-- TOC entry 4895 (class 0 OID 0)
-- Dependencies: 219
-- Name: documents_id_document_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.documents_id_document_seq', 100, true);


--
-- TOC entry 4896 (class 0 OID 0)
-- Dependencies: 231
-- Name: entrant_achievements_id_docs_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.entrant_achievements_id_docs_seq', 75, true);


--
-- TOC entry 4897 (class 0 OID 0)
-- Dependencies: 217
-- Name: entrant_id_entrant_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.entrant_id_entrant_seq', 25, true);


--
-- TOC entry 4898 (class 0 OID 0)
-- Dependencies: 221
-- Name: exam_id_exam_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.exam_id_exam_seq', 10, true);


--
-- TOC entry 4899 (class 0 OID 0)
-- Dependencies: 223
-- Name: exam_results_id_result_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.exam_results_id_result_seq', 152, true);


--
-- TOC entry 4900 (class 0 OID 0)
-- Dependencies: 227
-- Name: faculty_id_faculty_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.faculty_id_faculty_seq', 20, true);


--
-- TOC entry 4901 (class 0 OID 0)
-- Dependencies: 233
-- Name: order_of_enrollment_id_order_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_of_enrollment_id_order_seq', 27, true);


--
-- TOC entry 4902 (class 0 OID 0)
-- Dependencies: 225
-- Name: speciality_id_speciality_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.speciality_id_speciality_seq', 21, true);


--
-- TOC entry 4703 (class 2606 OID 17122)
-- Name: application application_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application
    ADD CONSTRAINT application_pkey PRIMARY KEY (id_application);


--
-- TOC entry 4693 (class 2606 OID 17067)
-- Name: documents documents_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents
    ADD CONSTRAINT documents_pkey PRIMARY KEY (id_document);


--
-- TOC entry 4705 (class 2606 OID 17139)
-- Name: entrant_achievements entrant_achievements_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.entrant_achievements
    ADD CONSTRAINT entrant_achievements_pkey PRIMARY KEY (id_docs);


--
-- TOC entry 4691 (class 2606 OID 17058)
-- Name: entrant entrant_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.entrant
    ADD CONSTRAINT entrant_pkey PRIMARY KEY (id_entrant);


--
-- TOC entry 4695 (class 2606 OID 17079)
-- Name: exam exam_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exam
    ADD CONSTRAINT exam_pkey PRIMARY KEY (id_exam);


--
-- TOC entry 4697 (class 2606 OID 17086)
-- Name: exam_results exam_results_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exam_results
    ADD CONSTRAINT exam_results_pkey PRIMARY KEY (id_result);


--
-- TOC entry 4701 (class 2606 OID 17110)
-- Name: faculty faculty_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.faculty
    ADD CONSTRAINT faculty_pkey PRIMARY KEY (id_faculty);


--
-- TOC entry 4707 (class 2606 OID 17151)
-- Name: order_of_enrollment order_of_enrollment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_of_enrollment
    ADD CONSTRAINT order_of_enrollment_pkey PRIMARY KEY (id_order);


--
-- TOC entry 4699 (class 2606 OID 17103)
-- Name: speciality speciality_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.speciality
    ADD CONSTRAINT speciality_pkey PRIMARY KEY (id_speciality);


--
-- TOC entry 4712 (class 2606 OID 17123)
-- Name: application application_id_entrant_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application
    ADD CONSTRAINT application_id_entrant_fkey FOREIGN KEY (id_entrant) REFERENCES public.entrant(id_entrant);


--
-- TOC entry 4713 (class 2606 OID 17128)
-- Name: application application_id_faculty_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.application
    ADD CONSTRAINT application_id_faculty_fkey FOREIGN KEY (id_faculty) REFERENCES public.faculty(id_faculty);


--
-- TOC entry 4708 (class 2606 OID 17068)
-- Name: documents documents_id_entrant_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.documents
    ADD CONSTRAINT documents_id_entrant_fkey FOREIGN KEY (id_entrant) REFERENCES public.entrant(id_entrant);


--
-- TOC entry 4714 (class 2606 OID 17140)
-- Name: entrant_achievements entrant_achievements_id_entrant_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.entrant_achievements
    ADD CONSTRAINT entrant_achievements_id_entrant_fkey FOREIGN KEY (id_entrant) REFERENCES public.entrant(id_entrant);


--
-- TOC entry 4709 (class 2606 OID 17087)
-- Name: exam_results exam_results_id_entrant_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exam_results
    ADD CONSTRAINT exam_results_id_entrant_fkey FOREIGN KEY (id_entrant) REFERENCES public.entrant(id_entrant);


--
-- TOC entry 4710 (class 2606 OID 17092)
-- Name: exam_results exam_results_id_exam_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.exam_results
    ADD CONSTRAINT exam_results_id_exam_fkey FOREIGN KEY (id_exam) REFERENCES public.exam(id_exam);


--
-- TOC entry 4711 (class 2606 OID 17111)
-- Name: faculty faculty_id_speciality_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.faculty
    ADD CONSTRAINT faculty_id_speciality_fkey FOREIGN KEY (id_speciality) REFERENCES public.speciality(id_speciality);


--
-- TOC entry 4715 (class 2606 OID 17152)
-- Name: order_of_enrollment order_of_enrollment_id_entrant_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_of_enrollment
    ADD CONSTRAINT order_of_enrollment_id_entrant_fkey FOREIGN KEY (id_entrant) REFERENCES public.entrant(id_entrant);


--
-- TOC entry 4716 (class 2606 OID 17157)
-- Name: order_of_enrollment order_of_enrollment_id_faculty_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_of_enrollment
    ADD CONSTRAINT order_of_enrollment_id_faculty_fkey FOREIGN KEY (id_faculty) REFERENCES public.faculty(id_faculty);


-- Completed on 2025-06-16 19:00:07

--
-- PostgreSQL database dump complete
--

