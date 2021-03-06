DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
-- pwd: password
VALUES ('User', 'user@yandex.ru', '$2a$10$l/0pK93oK/nSg/CL0bJsC.FjHhkLJki7JrqtbUyqiwwYeY9uMs1Me');

INSERT INTO users (name, email, password)
-- pwd: admin
VALUES ('Admin', 'admin@gmail.com', '$2a$10$VqJ4z0/59AUGaZu8x4HXiO6ZNG.aC.WJOQTzd1ilQM1GQ9HEJngJO');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_USER', 100001);

INSERT INTO restaurants (name, address, phone_number) VALUES
  ('Susi Vesla', '12 Russianova st.', '260-15-98'),                       /* id=100002 */
  ('Шоколадница', 'ул. Брестская 22', '287-26-77'),                       /* id=100003 */
  ('Chaihana', '154 Nezavisimosti Avenue', '+375 (29) 245-32-45');        /* id=100004 */

INSERT INTO dishes (name, price, restaurant_id) VALUES
  ('Sushi', 12.25, 100002),
  ('Sashimi', 25.00, 100002),
  ('Кофе', 1.50, 100003),
  ('Горячий шоколад', 1.50, 100003),
  ('Хлеб', 0.25, 100003),
  ('Kalyan', 32.50, 100004),
  ('Pahlava', 15.10, 100004);

INSERT INTO users (name, email, password) VALUES
-- pwd: qwerty666
  ('111', '111www@gmail.com', '$2a$10$Bb9dLG0ggNiaQksppM3ZWeFkSDHfJD/lIXYP7f6RGezO9rhQ1TQAO'),
-- pwd: wasd
  ('222', '222www@gmail.com', '$2a$10$hNh75kltIyZHtjiZA3B8WeyULBLmJsls51FW3tkxpBfhIYOVgT6e2');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100013);

INSERT INTO votes (voter, selected, restaurant_name, date) VALUES
  (100000, 100002, 'Susi Vesla', '2017-02-27'),
  (100001, 100003, 'Шоколадница', '2017-02-27'),
  (100000, 100004, 'Chaihana', '2017-02-28'),
  (100001, 100004, 'Chaihana', '2017-02-28'),
  (100000, 100002, 'Susi Vesla', now),
  (100001, 100003, 'Шоколадница', now),
  (100012, 100003, 'Шоколадница', now);
