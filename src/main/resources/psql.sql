-- CREATE DATABASE production_flow;

DROP TABLE IF EXISTS equipment, equipment_instance, stage, item_for_production, commodity_item,
    techno_map, stage_techno_map, input_commodity_items, input_item_for_production;
------------------------------------------------------------------------------------

CREATE TABLE equipment
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);
CREATE TABLE equipment_instance
(
    id            SERIAL PRIMARY KEY,
    serial_number TEXT NOT NULL,
    equipment_id  INT REFERENCES equipment (id)
);
CREATE TABLE stage
(
    id                       SERIAL PRIMARY KEY,
    name                     TEXT           NOT NULL,
    index_in_technomap       INT            NOT NULL CHECK (index_in_technomap >= 0),
    time_spent_in_seconds    BIGINT         NOT NULL CHECK (time_spent_in_seconds >= 0),
    money_expenses_in_rubles DECIMAL(19, 2) NOT NULL CHECK (money_expenses_in_rubles >= 0),
    workshop_map_id          INT            NOT NULL CHECK (workshop_map_id >= 0),
    create_time              TIMESTAMPTZ    NOT NULL,
    creater_id               int            NOT NULL CHECK (creater_id >= 0),
    equipment_instance_id    INT REFERENCES equipment_instance (id)
);

CREATE TABLE item_for_production
(
    id            SERIAL PRIMARY KEY,
    name          TEXT NOT NULL,
    created_stage INT REFERENCES stage (id)
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
CREATE TABLE stage_techno_map
(
    id            SERIAL PRIMARY KEY,
    stage_id      INT REFERENCES stage (id)      NOT NULL,
    techno_map_id INT REFERENCES techno_map (id) NOT NULL
);
CREATE TABLE input_commodity_items
(
    id                SERIAL PRIMARY KEY,
    commodity_item_id INT REFERENCES commodity_item (id) NOT NULL,
    stage_id          INT REFERENCES stage (id)          NOT NULL
);
CREATE TABLE input_item_for_production
(
    id                     SERIAL PRIMARY KEY,
    item_for_production_id INT REFERENCES item_for_production (id) NOT NULL,
    stage_id               INT REFERENCES stage (id)               NOT NULL
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
INSERT INTO equipment_instance (serial_number, equipment_id)
VALUES ('1D243FD2314FV3', 1),
       ('45GH365J865J6H', 1),
       ('G32YUG234TBGI5', 3),
       ('SDF9834HF92851', 4);
INSERT INTO stage (name, index_in_technomap, time_spent_in_seconds, money_expenses_in_rubles, workshop_map_id,
                   create_time, creater_id, equipment_instance_id)
VALUES ('Нарезка деталей', 1, 5600, 30000.20, 1, now(), 1, 1),
       ('Смазывание деталей', 1, 3600, 500, 1, now(), 1, 4);
INSERT INTO input_commodity_items (commodity_item_id, stage_id)
VALUES (3, 1),
       (5, 2);
INSERT INTO stage_techno_map (stage_id, techno_map_id)
VALUES (1, 1),
       (2, 1);
INSERT INTO item_for_production (name, created_stage)
VALUES ('Детали', 1);
INSERT INTO input_item_for_production (item_for_production_id, stage_id)
VALUES (1, 1);


-- В Mongo: WorkShopMap
-- {
--     "_id": ObjectId("file_id"),
--     "filename": "scheme.png",
--     "content_type": "image/png",
--     "image_data": { "$binary": "<base64 encoded image data>", "$type": "00" },
--     "equipments": [
--         {
--             "equipment_instance_id": 1,
--             "coords": [
--                 { "x": 100, "y": 200 },
--                 { "x": 100, "y": 200 },
--                 { "x": 100, "y": 200 }
--             ]
--         },
--         {
--             "equipment_instance_id": 2,
--             "coords": [
--                 { "x": 100, "y": 200 }
--             ]
--         }
--     ]
-- }