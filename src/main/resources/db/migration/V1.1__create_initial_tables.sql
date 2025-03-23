CREATE TABLE country
(
    id   bigserial primary key,
    name text not null,
    code text not null
);

CREATE TABLE city
(
    id      bigserial primary key,
    name    text not null,
    country bigint references country
);

CREATE TABLE person
(
    id            bigserial primary key,
    uuid          uuid   not null unique,
    first_name    text   not null,
    last_name     text   not null,
    birthday      date   not null,
    email         text   not null,
    phone         text   not null,
    original_from bigint not null references city,
    currently_in  bigint references city
);

CREATE TABLE interests
(
    id     bigserial primary key,
    name   text   not null,
    person bigint not null references person
);

CREATE TABLE languages
(
    id   bigserial primary key,
    name text not null
);

CREATE TABLE person_speaks_language
(
    id       bigserial primary key,
    person   bigint not null references person,
    language bigint not null references languages
);

CREATE TABLE company
(
    id              bigserial primary key,
    name            text   not null,
    branch          text   not null,
    city            bigint not null references city,
    employee_amount bigint not null
);

CREATE TABLE career
(
    id              bigserial primary key,
    name            text   not null,
    start_date      date   not null,
    end_date        date,
    job_description text   not null,
    tasks           text   not null,
    person          bigint not null references person,
    company         bigint not null references company
);

CREATE TABLE education
(
    id             bigserial primary key,
    school_name    text not null,
    start_date     date not null,
    end_date       date,
    degree         text not null,
    description    text not null,
    education_type text not null
);
