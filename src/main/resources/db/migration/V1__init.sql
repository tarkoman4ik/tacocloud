CREATE TABLE IF NOT EXISTS ingredient
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    type smallint,
    CONSTRAINT ingredient_pkey PRIMARY KEY (id),
    CONSTRAINT ingredient_type_check CHECK (type >= 0 AND type <= 4)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS ingredient
    OWNER to taco;

CREATE TABLE IF NOT EXISTS taco
(
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT taco_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS taco
    OWNER to taco;

CREATE TABLE IF NOT EXISTS taco_ingredients
(
    taco bigint NOT NULL,
    ingredient character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT fk7n0qwbvk34ammp7ohv7qffeof FOREIGN KEY (taco)
        REFERENCES taco (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkf6ox63cern045dfwm4d390ker FOREIGN KEY (ingredient)
        REFERENCES ingredient (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS taco_ingredients
    OWNER to taco;

CREATE TABLE IF NOT EXISTS taco_order
(
    id bigint NOT NULL,
    cccvv character varying(255) COLLATE pg_catalog."default",
    cc_expiration character varying(255) COLLATE pg_catalog."default",
    cc_number character varying(255) COLLATE pg_catalog."default" NOT NULL,
    city character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    placed_at timestamp(6) without time zone,
    state character varying(255) COLLATE pg_catalog."default" NOT NULL,
    street character varying(255) COLLATE pg_catalog."default" NOT NULL,
    zip character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT taco_order_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS taco_order
    OWNER to taco;

CREATE TABLE IF NOT EXISTS taco_order_tacos
(
    taco_order bigint NOT NULL,
    taco bigint NOT NULL,
    CONSTRAINT fkj42x345qxs2yj95pd2ltj5fxu FOREIGN KEY (taco_order)
        REFERENCES taco_order (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fksl22ljacgajc6ym8yhhro3p4d FOREIGN KEY (taco)
        REFERENCES taco (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS taco_order_tacos
    OWNER to taco;


INSERT INTO ingredient("id","name","type")
VALUES('FLTO','Пшеничная лепешка',0),
('COTO','Кукурузная лепешка',0),
('GRBF','Говядина',1),
('CARN','Свинина',1),
('TMTO','Помидоры',2),
('LETC','Салат',2),
('CHED','Чеддер',3),
('JACK','Джек',3),
('SLSA','Сальса',4),
('SRCR','Сметана',4);