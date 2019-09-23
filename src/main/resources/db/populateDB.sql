DELETE
FROM user_roles;
DELETE
FROM users;
-- DELETE
-- FROM MENUS;
DELETE
FROM ITEMS;
-- DELETE
-- FROM ITEMS;
-- DELETE
-- FROM RESTAURANTS;
DELETE
FROM RESTAURANTS;

ALTER SEQUENCE global_seq RESTART WITH 100000;
-- ALTER SEQUENCE RESTAURANT_SEQ RESTART WITH 100000;
-- ALTER SEQUENCE ITEM_SEQ RESTART WITH 100000;
-- ALTER SEQUENCE MENU_SEQ RESTART WITH 100000;
-- ALTER SEQUENCE ITEM_TEST_SEQ RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001),
       ('ROLE_USER', 100001);

-- INSERT INTO RESTAURANTS (NAME)
-- VALUES ('TOKYO-CITY'), /* 100000 */
--        ('KETCH-UP'), /* 100001 */
--        ('ХАЧАПУРИ И ВИНО'), /* 100002 */
--        ('KWAKINN'); /* 100003 */

INSERT INTO RESTAURANTS (NAME)
VALUES ('TOKYO-CITY'), /* 100002 */
       ('KETCH-UP'), /* 100003 */
       ('ХАЧАПУРИ И ВИНО'), /* 100004 */
       ('KWAKINN'); /* 100005 */

-- INSERT INTO ITEMS (NAME, PRICE)
-- VALUES ('beer', 250), /* 100000 */
--        ('soup', 230), /* 100001 */
--        ('salad', 160), /* 100002 */
--        ('pizza', 450), /* 100003 */
--        ('burger', 390), /* 100004 */
--        ('pasta', 300), /* 100005 */
--        ('dessert', 150), /* 100006 */
--        ('beef', 450); /* 100007 */
--
-- INSERT INTO MENUS (rest_id, item_id, date_time)
-- VALUES (100000, 100000, '2019-09-30 10:00:00'),
--        (100000, 100003, '2019-09-30 10:00:00'),
--        (100000, 100006, '2019-09-30 10:00:00'),
--        (100001, 100004, '2019-09-30 10:00:00'),
--        (100001, 100002, '2015-09-30 10:00:00'),
--        (100001, 100000, '2019-09-30 10:00:00'),
--        (100002, 100003, '2019-09-30 10:00:00'),
--        (100002, 100000, '2019-09-30 10:00:00'),
--        (100002, 100005, '2019-09-30 10:00:00');

INSERT INTO ITEMS (rest_id, name, price, date_time)
VALUES (100002, 'beer', 150, '2019-09-20 10:00:00'),
       (100002, 'wok', 200, '2019-09-20 10:00:00'),
       (100003, 'beer', 300, '2019-09-20 10:00:00'),
       (100002, 'beer', 150, '2019-09-21 10:00:00'),
       (100003, 'salad', 200, '2015-09-21 10:00:00');




