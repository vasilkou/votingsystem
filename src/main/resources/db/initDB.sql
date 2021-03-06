DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq AS INTEGER START WITH 100000;

CREATE TABLE users
(
  id            INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
  name          VARCHAR(255) NOT NULL,
  email         VARCHAR(255) NOT NULL,
  password      VARCHAR(255) NOT NULL,
  registered    TIMESTAMP DEFAULT now(),
  enabled       BOOLEAN DEFAULT TRUE
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
  id            INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
  name          VARCHAR(255) NOT NULL,
  address       VARCHAR(255) NOT NULL,
  phone_number  VARCHAR(32) NOT NULL,
  CONSTRAINT name_address_idx UNIQUE (name, address)
);
CREATE INDEX restaurants_name_idx ON restaurants (name);

CREATE TABLE dishes
(
  id              INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
  name            VARCHAR(255) NOT NULL,
  price           REAL NOT NULL,
  restaurant_id   INTEGER NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE INDEX dishes_name_idx ON dishes (name);

CREATE TABLE votes
(
  id                    INTEGER GENERATED BY DEFAULT AS SEQUENCE global_seq PRIMARY KEY,
  voter                 INTEGER NOT NULL,
  selected              INTEGER NOT NULL,
  restaurant_name       VARCHAR(255) NOT NULL,
  date                  DATE DEFAULT now(),
  CONSTRAINT voter_date_idx UNIQUE (voter, date)
);
