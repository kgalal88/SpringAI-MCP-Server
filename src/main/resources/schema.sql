CREATE TABLE user_activity (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50),
    score INT,
    created_date TIMESTAMP
);

CREATE TABLE users (
    user_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50),
    age INT
);