INSERT INTO item (item_id, item_name, item_price) VALUES (1, 'Vino', 100);
INSERT INTO item (item_id, item_name, item_price) VALUES (2, 'Coffee', 30);
INSERT INTO item (item_id, item_name, item_price) VALUES (3, 'Tea', 10);
INSERT INTO item (item_id, item_name, item_price) VALUES (4, 'Juice', 15);
INSERT INTO item (item_id, item_name, item_price) VALUES (5, 'Beer', 20);
INSERT INTO item (item_id, item_name, item_price) VALUES (6, 'Whiskey', 50);
INSERT INTO item (item_id, item_name, item_price) VALUES (7, 'Water', 5);


INSERT INTO ordertable (order_id, order_name, order_price, order_date) VALUES (1, 'Table #1', 40,'2020-04-15');
INSERT INTO ordertable (order_id, order_name, order_price, order_date) VALUES (2, 'Reserved', 210, '2020-04-13');
INSERT INTO ordertable (order_id, order_name, order_price, order_date) VALUES (3, 'Garden', 10, '2020-04-18');



INSERT INTO position ( position_order_id, position_name, position_price, position_count) VALUES (1, 'Tea', 10, 1);
INSERT INTO position ( position_order_id, position_name, position_price, position_count) VALUES (1, 'Coffee', 15, 1);
INSERT INTO position ( position_order_id, position_name, position_price, position_count) VALUES (1, 'Coffee', 15, 1);
INSERT INTO position ( position_order_id, position_name, position_price, position_count) VALUES (2, 'Tea', 10, 1);
INSERT INTO position ( position_order_id, position_name, position_price, position_count) VALUES (2, 'Vino', 100, 1);
INSERT INTO position ( position_order_id, position_name, position_price, position_count) VALUES (2, 'Vino', 100, 1);
INSERT INTO position ( position_order_id, position_name, position_price, position_count) VALUES (3, 'Tea', 10, 1);
