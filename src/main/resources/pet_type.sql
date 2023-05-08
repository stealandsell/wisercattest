DROP TABLE IF EXISTS pet_type;
CREATE TABLE pet_type (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL
);

INSERT INTO pet_type (name) VALUES
                                ('Cat'),
                                ('Dog'),
                                ('Horse'),
                                ('Rabbit'),
                                ('Parrot');

