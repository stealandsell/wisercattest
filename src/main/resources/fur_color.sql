DROP TABLE IF EXISTS fur_color;
CREATE TABLE fur_color (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(255) NOT NULL
);

INSERT INTO fur_color (name) VALUES
                                 ('Black'),
                                 ('White'),
                                 ('Brown'),
                                 ('Yellow'),
                                 ('Blue');