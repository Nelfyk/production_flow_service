-- CREATE DATABASE production_flow;

DROP TABLE IF EXISTS equipment, equipment_instance, stages, item_for_production, commodity_item,
    techno_map, stage_techno_map, input_commodity_items, input_item_for_production;
------------------------------------------------------------------------------------

CREATE TABLE equipment
(
    id   SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);
CREATE TABLE equipment_instance
(
    id           SERIAL PRIMARY KEY,
    name         TEXT NOT NULL,
    equipment_id INT REFERENCES equipment (id)
);
CREATE TABLE stages
(
    id                       SERIAL PRIMARY KEY,
    name                     TEXT        NOT NULL,
    index_in_technomap       INT         NOT NULL,
    time_spent_in_seconds    BIGINT      NOT NULL,
    money_expenses_in_rubles DECIMAL(2)  NOT NULL,
    workshop_map_id          INT         NOT NULL,
    create_time              TIMESTAMPTZ NOT NULL,
    creater_id               int         NOT NULL,
    equipment_instance_id    INT REFERENCES equipment_instance (id)
);

CREATE TABLE item_for_production
(
    id            SERIAL PRIMARY KEY,
    name          TEXT NOT NULL,
    created_stage INT REFERENCES stages (id)
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
    stage_id      INT REFERENCES stages (id)      NOT NULL,
    techno_map_id INT REFERENCES techno_map (id) NOT NULL,
    PRIMARY KEY (stage_id, techno_map_id)
);
CREATE TABLE input_commodity_items
(
    commodity_item_id INT REFERENCES commodity_item (id) NOT NULL,
    stage_id          INT REFERENCES stages (id)         NOT NULL,
    PRIMARY KEY (commodity_item_id, stage_id)
);
CREATE TABLE input_item_for_production
(
    item_for_production_id INT REFERENCES item_for_production (id) NOT NULL,
    stage_id               INT REFERENCES stages (id)              NOT NULL,
    PRIMARY KEY (item_for_production_id, stage_id)
);

------------------------------------------------------------------------------------


INSERT INTO commodity_item (name)
VALUES ('Доски'),
       ('Гвозди'),
       ('Лист металла'),
       ('Полипропилен'),
       ('Масло');
INSERT INTO techno_map (name, commodity_item_id)
VALUES ('Создание фасада здания', 1),
       ('Создание бутылки', 4),
       ('Создание упаковки', 4);

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
