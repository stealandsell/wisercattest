DROP TABLE IF EXISTS country_of_origin;
CREATE TABLE country_of_origin (
                                   id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                   name VARCHAR(255) NOT NULL
);

INSERT INTO country_of_origin (name) VALUES
                                         ('Estonia'),
                                         ('Latvia'),
                                         ('Lithuania'),
                                         ('Finland'),
                                         ('Sweden'),
                                         ('Norway');

