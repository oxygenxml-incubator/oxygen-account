ALTER TABLE users ADD COLUMN status VARCHAR(255) NOT NULL;
ALTER TABLE users ADD COLUMN deletion_date TIMESTAMP DEFAULT NULL;

UPDATE users SET status = 'active' where status IS NULL;