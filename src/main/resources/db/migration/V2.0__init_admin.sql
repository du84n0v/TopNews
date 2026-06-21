INSERT INTO profile (name, surname, username, password, status, visible, created_date, photo_id)
VALUES ('Admin', 'Adminov', 'admin',
        '$2a$12$KshfF8S7E7fREsA9bO0Ryeu9N6uC8P4M1SgK1Q8b9zVpaR3n6Fbe2',
        'ACTIVE', true, NOW(), NULL);

INSERT INTO profile_role(profile_id, role)
VALUES(1, 'ROLE_ADMIN');