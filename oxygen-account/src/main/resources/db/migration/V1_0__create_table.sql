CREATE TABLE USER (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    registration_date DATE NOT NULL,
    password VARCHAR(50) NOT NULL
);