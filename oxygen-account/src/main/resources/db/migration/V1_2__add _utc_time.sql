CREATE TRIGGER BeforeUserInsert BEFORE INSERT ON users
FOR EACH ROW
BEGIN
    SET NEW.registration_date = UTC_TIMESTAMP();
END;

CREATE TRIGGER BeforeUserUpdate BEFORE UPDATE ON users
FOR EACH ROW
BEGIN
    IF NEW.registration_date IS NULL THEN
        SET NEW.registration_date = UTC_TIMESTAMP();
    END IF;
END;