DROP TABLE IF EXISTS files;
DROP TABLE IF EXISTS users;
-- 用户表
CREATE TABLE users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       email VARCHAR(100) NOT NULL UNIQUE DEFAULT 'default@email.com'
);

-- 文件表
CREATE TABLE files (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       user_id INT NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       size VARCHAR(50) NOT NULL,
                       type VARCHAR(100) NOT NULL,
                       path VARCHAR(255) NOT NULL,
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE ai_history (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          user_id INT NOT NULL,
                          prompt TEXT NOT NULL,
                          response TEXT NOT NULL,
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (user_id) REFERENCES users(id)
);