-- CREATE DATABASE production_flow;

DROP TABLE IF EXISTS stages, item_for_production, commodity_items, techno_maps, stage_techno_map, equipment, equipment_instance;

CREATE TABLE stages
(
    id                       SERIAL PRIMARY KEY,
    name                     TEXT        NOT NULL,
    index_in_technomap       INT         NOT NULL,
    time_spent_in_seconds    BIGINT      NOT NULL,
    money_expenses_in_rubles DECIMAL(2)  NOT NULL,
    workshop_map_id          INT         NOT NULL,
    equipment_instance_id    INT         NOT NULL,
    create_time              TIMESTAMPTZ NOT NULL,
    creater_id               int         NOT NULL
);

CREATE TABLE item_for_production
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE commodity_items
(
    id                     SERIAL PRIMARY KEY,
    name                   TEXT NOT NULL
);

CREATE TABLE techno_maps
(
    id                     SERIAL PRIMARY KEY,
    name                   TEXT NOT NULL,
    commodity_items_id INT REFERENCES commodity_items (id)
);

CREATE TABLE stage_techno_map
(
    stage_id      INT REFERENCES stages (id)      NOT NULL,
    techno_map_id INT REFERENCES techno_maps (id) NOT NULL,
    PRIMARY KEY (stage_id, techno_map_id)
);

CREATE TABLE equipment
(
    id                     SERIAL PRIMARY KEY,
    name                   TEXT NOT NULL,
    item_for_production_id INT REFERENCES item_for_production (id)
);

CREATE TABLE equipment_instance
(
    id                     SERIAL PRIMARY KEY,
    name                   TEXT NOT NULL,
    equipment_id INT REFERENCES equipment (id)
);

INSERT INTO commodity_items (name) VALUES ('Доски'), ('Гвозди'), ('Лист металла'), ('Полипропилен'), ('Масло');
INSERT INTO techno_maps (name, commodity_items_id) VALUES ('Создание фасада здания', 1), ('Создание бутылки', 4), ('Создание упаковки', 4);




SELECT * FROM techno_maps;