INSERT INTO profile (name, surname, username, password, status, visible, created_date, photo_id)
VALUES ('Admin', 'Adminov', 'admin',
        '$2a$12$KshfF8S7E7fREsA9bO0Ryeu9N6uC8P4M1SgK1Q8b9zVpaR3n6Fbe2',
        'ACTIVE', true, NOW(), NULL);

-- 1. PUBLISHER PROFILI
-- Paroli ochiq holatda: 'publisher123'
INSERT INTO profile (name, surname, username, password, status, visible, created_date, photo_id)
VALUES ('Asilbek', 'Eshonov', '+998991234567',
        '$2a$12$Z0m0F7O6R0E5fD8sA9bO0OuxO8B1R4M2SgK1Q8b9zVpaR3n6Fbe2P',
        'ACTIVE', true, NOW(), NULL);

INSERT INTO profile_role(profile_id, role)
VALUES((SELECT id FROM profile WHERE username = 'publisher'), 'ROLE_PUBLISHER');


-- 2. MODERATOR PROFILI
-- Paroli ochiq holatda: 'moderator123'
INSERT INTO profile (name, surname, username, password, status, visible, created_date, photo_id)
VALUES ('Sardor', 'Karimov', '+998909876543',
        '$2a$12$Y1n1G8P7S1F6gE9tB0cO1PvyP9C2S5N3ThL2R9c0wWqbS4o7Gcf3Q',
        'ACTIVE', true, NOW(), NULL);

INSERT INTO profile_role(profile_id, role)
VALUES((SELECT id FROM profile WHERE username = 'moderator'), 'ROLE_MODERATOR');

INSERT INTO profile_role(profile_id, role) VALUES(1, 'ROLE_ADMIN');
INSERT INTO profile_role(profile_id, role) VALUES(2, 'ROLE_PUBLISHER');
INSERT INTO profile_role(profile_id, role) VALUES(3, 'ROLE_MODERATOR');

