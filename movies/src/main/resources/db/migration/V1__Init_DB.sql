CREATE TABLE movie (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       released_year INT NOT NULL,
                       rating DOUBLE NOT NULL
);