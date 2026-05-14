INSERT INTO country (name, code)
VALUES ('Austria', 'AT'),
       ('Germany', 'DE'),
       ('France', 'FR');

INSERT INTO city (name, country)
VALUES ('Vienna', (SELECT id from country where code = 'AT' LIMIT 1));

INSERT INTO person (uuid, first_name, last_name, birthday, email, phone, original_from, currently_in)
VALUES (uuidv7(), 'John', 'Doe', '1980-01-01', 'some@email.xx', '0123456789',
        (SELECT id from city where name = 'Vienna' LIMIT 1), (SELECT id from city where name = 'Vienna' LIMIT 1));

INSERT INTO education (school_name, start_date, end_date, degree, description, education_type, person, city)
VALUES ('School', '1990-01-01', '2000-02-02', 'schoolist', 'Was great', 'ELEMENTARY_SCHOOL',
        (SELECT id from person LIMIT 1), (SELECT id from city LIMIT 1));
