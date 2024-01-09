INSERT INTO departments (Department)
SELECT * FROM (SELECT 'Computer Science') AS tmp
WHERE NOT EXISTS (
    SELECT Department FROM departments WHERE Department = 'Computer Science'
) LIMIT 1;
INSERT INTO departments (Department)
SELECT * FROM (SELECT 'Mathematics') AS tmp
WHERE NOT EXISTS (
    SELECT Department FROM departments WHERE Department = 'Mathematics'
) LIMIT 1;
INSERT INTO departments (Department)
SELECT * FROM (SELECT 'Physics') AS tmp
WHERE NOT EXISTS (
    SELECT Department FROM departments WHERE Department = 'Physics'
) LIMIT 1;

INSERT INTO buildings (building)
SELECT * FROM (SELECT 'ATS') AS tmp
WHERE NOT EXISTS (
    SELECT building FROM buildings WHERE building = 'ATS'
) LIMIT 1;
INSERT INTO buildings (building)
SELECT * FROM (SELECT 'Eisenburg') AS tmp
WHERE NOT EXISTS (
    SELECT building FROM buildings WHERE building = 'Eisenburg'
) LIMIT 1;
INSERT INTO buildings (building)
SELECT * FROM (SELECT 'Student Center') AS tmp
WHERE NOT EXISTS (
    SELECT building FROM buildings WHERE building = 'Student Center'
) LIMIT 1;
INSERT INTO buildings (building)
SELECT * FROM (SELECT 'SPOTTS') AS tmp
WHERE NOT EXISTS (
    SELECT building FROM buildings WHERE building = 'SPOTTS'
) LIMIT 1;

INSERT INTO rooms (room_numbers)
Select * FROM (SELECT '232') AS tmp
WHERE NOT EXISTS(
SELECT room_numbers FROM rooms WHERE room_numbers = '232'
) LIMIT 1;
INSERT INTO rooms (room_numbers)
SELECT * FROM (SELECT '130') AS tmp
WHERE NOT EXISTS (
    SELECT room_numbers FROM rooms WHERE room_numbers = '130'
) LIMIT 1;
INSERT INTO rooms (room_numbers)
SELECT * FROM (SELECT '132') AS tmp
WHERE NOT EXISTS (
    SELECT room_numbers FROM rooms WHERE room_numbers = '132'
) LIMIT 1;
INSERT INTO rooms (room_numbers)
SELECT * FROM (SELECT '234') AS tmp
WHERE NOT EXISTS (
    SELECT room_numbers FROM rooms WHERE room_numbers = '234'
) LIMIT 1;

INSERT INTO roles (name)
SELECT * FROM (SELECT 'User') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM roles WHERE name = 'User'
) LIMIT 1;
INSERT INTO roles (name)
SELECT * FROM (SELECT 'Admin') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM roles WHERE name = 'Admin'
) LIMIT 1;
INSERT INTO roles (name)
SELECT * FROM (SELECT 'Tech') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM roles WHERE name = 'Tech'
) LIMIT 1;
INSERT INTO roles (name)
SELECT * FROM (SELECT 'Manager') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM roles WHERE name = 'Manager'
) LIMIT 1;

