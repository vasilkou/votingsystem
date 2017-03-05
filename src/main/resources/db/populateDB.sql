DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

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

INSERT INTO votes (voter, selected, restaurant_name, date) VALUES
  (100000, 100002, 'Susi Vesla', '2017-02-27'),
  (100001, 100003, 'Шоколадница', '2017-02-27'),
  (100000, 100004, 'Chaihana', '2017-02-28'),
  (100001, 100004, 'Chaihana', '2017-02-28');
