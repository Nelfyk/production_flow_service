-- CREATE DATABASE production_flow;

DROP TABLE IF EXISTS equipment, serial_numbers, commodity_item, techno_map, techno_map_stages,
    item_for_production, stage_techno_map, input_commodity_items, input_item_for_production;
------------------------------------------------------------------------------------

CREATE TABLE equipment
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);
CREATE TABLE serial_numbers
(
    id            SERIAL PRIMARY KEY,
    serial_number TEXT NOT NULL,
    equipment_id  INT REFERENCES equipment (id)
);
CREATE TABLE commodity_item
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);
CREATE TABLE techno_map
(
    id                SERIAL PRIMARY KEY,
    name              TEXT NOT NULL,
    commodity_item_id INT REFERENCES commodity_item (id)
);
CREATE TABLE techno_map_stages
(
    id                       SERIAL PRIMARY KEY,
    name                     TEXT           NOT NULL,
    index_in_techno_map      INT            NOT NULL CHECK (index_in_techno_map >= 0),
    time_spent_in_seconds    BIGINT         NOT NULL CHECK (time_spent_in_seconds >= 0),
    money_expenses_in_rubles DECIMAL(19, 2) NOT NULL CHECK (money_expenses_in_rubles >= 0),
    workshop_map_id          TEXT           NOT NULL CHECK (char_length(workshop_map_id) > 0),
    create_time              TIMESTAMPTZ    NOT NULL,
    creator_id               int            NOT NULL CHECK (creator_id >= 0),
    techno_map_id            INT REFERENCES techno_map (id),
    serial_numbers_id        INT REFERENCES serial_numbers (id)
);

CREATE TABLE item_for_production
(
    id                           SERIAL PRIMARY KEY,
    name                         TEXT NOT NULL,
    created_techno_map_stages_id INT REFERENCES techno_map_stages (id)
);
CREATE TABLE input_commodity_items
(
    id                   SERIAL PRIMARY KEY,
    commodity_item_id    INT REFERENCES commodity_item (id)    NOT NULL,
    techno_map_stages_id INT REFERENCES techno_map_stages (id) NOT NULL
);
CREATE TABLE input_item_for_production
(
    id                     SERIAL PRIMARY KEY,
    item_for_production_id INT REFERENCES item_for_production (id) NOT NULL,
    techno_map_stages_id   INT REFERENCES techno_map_stages (id)   NOT NULL
);

------------------------------------------------------------------------------------


INSERT INTO commodity_item (name)
VALUES ('Доски'),
       ('Гвозди'),
       ('Лист металла'),
       ('Полипропилен'),
       ('Масло');
INSERT INTO techno_map (name, commodity_item_id)
VALUES ('Резка метала', 1),
       ('Создание бутылки', 4),
       ('Создание упаковки', 4);

INSERT INTO equipment (name)
VALUES ('Лазерный станок'),
       ('Плазменный станок'),
       ('Межгалактический станок'),
       ('Смазочный станок');
INSERT INTO serial_numbers (serial_number, equipment_id)
VALUES ('1D243FD2314FV3', 1),
       ('45GH365J865J6H', 1),
       ('G32YUG234TBGI5', 3),
       ('SDF9834HF92851', 4);
INSERT INTO techno_map_stages (name, index_in_techno_map, time_spent_in_seconds, money_expenses_in_rubles,
                               workshop_map_id, create_time, creator_id, techno_map_id, serial_numbers_id)
VALUES ('Нарезка деталей', 1, 5600, 30000.20, 1, now(), 1, 1, 1),
       ('Смазывание деталей', 1, 3600, 500, 1, now(), 1, 1, 4);
INSERT INTO input_commodity_items (commodity_item_id, techno_map_stages_id)
VALUES (3, 1),
       (5, 2);
-- INSERT INTO stage_techno_map (techno_map_stages_id, techno_map_id)
-- VALUES (1, 1),
--        (2, 1);
INSERT INTO item_for_production (name, created_techno_map_stages_id)
VALUES ('Детали', 1);
INSERT INTO input_item_for_production (item_for_production_id, techno_map_stages_id)
VALUES (1, 1);


-- В Mongo: work_shop_map
-- {
--     "filename": "Pasha.jpg",
--     "filename_id": "64e5c4876eefd46d53d6427a",
--     "equipmentEntities": [
--         {
--             "serial_numbers_id": 1,
--             "coords": [
--                 {"x": 100, "y": 200}
--             ]
--         },
--         {
--             "serial_numbers_id": 2,
--             "coords": [
--                 {"x": 100, "y": 200}
--             ]
--         }
--     ]
-- }
-- mongofiles -d item_for_production put Pasha.jpg
