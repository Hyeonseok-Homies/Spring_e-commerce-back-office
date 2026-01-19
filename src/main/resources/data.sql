-- -------------------------------------------------------
-- 1. CUSTOMERS
-- -------------------------------------------------------
INSERT IGNORE INTO customers (Id, Name, Email, Password, Phone_Number, Created_At, Updated_At, Status)
VALUES (1, '테스트고객1', 'customer@test.com', 'pass123', '010-2222-2222', NOW(), NOW(), 'ACTIVE');

INSERT IGNORE INTO customers (Id, Name, Email, Password, Phone_Number, Created_At, Updated_At, Status)
VALUES (2, '테스트고객2', 'minji@example.com', 'pass456', '010-3333-3333', NOW(), NOW(), 'ACTIVE');

INSERT IGNORE INTO customers (Id, Name, Email, Password, Phone_Number, Created_At, Updated_At, Status)
VALUES (3, '테스트고객3', 'kangin@soccer.com', 'pass789', '010-4444-4444', NOW(), NOW(), 'ACTIVE');

INSERT IGNORE INTO customers (Id, Name, Email, Password, Phone_Number, Created_At, Updated_At, Status)
VALUES (4, '테스트고객4', 'hacker@security.com', 'pass000', '010-5555-5555', NOW(), NOW(), 'ACTIVE');

-- -------------------------------------------------------
-- 2. PRODUCTS
-- -------------------------------------------------------
INSERT IGNORE INTO products (Id, Name, Category, Price, Stock, Status, Created_At, Updated_At, Created_By_Admin_Id)
VALUES (1, '헤드셋', 'ELECTRONICS', 100000, 50, 'ONSALE', NOW(), NOW(), 1);

INSERT IGNORE INTO products (Id, Name, Category, Price, Stock, Status, Created_At, Updated_At, Created_By_Admin_Id)
VALUES (2, '맥북 에어 M3', 'ELECTRONICS', 1590000, 30, 'ONSALE', NOW(), NOW(), 1);

INSERT IGNORE INTO products (Id, Name, Category, Price, Stock, Status, Created_At, Updated_At, Created_By_Admin_Id)
VALUES (3, '게이밍 의자', 'FURNITURE', 250000, 100, 'ONSALE', NOW(), NOW(), 1);

-- -------------------------------------------------------
-- 3. ORDERS
-- -------------------------------------------------------
INSERT IGNORE INTO orders (id, order_no, price, quantity, status, created_by_customer_id, created_by_product_id, created_at, updated_at)
VALUES (1, 'ORD-2026-999', 1590000, 1, 'DELIVERED', 1, 2, NOW(), NOW());

INSERT IGNORE INTO orders (id, order_no, price, quantity, status, created_by_customer_id, created_by_product_id, created_at, updated_at)
VALUES (2, 'ORD-2026-159', 250000, 1, 'READY', 4, 3, NOW(), NOW());

-- -------------------------------------------------------
-- 4. REVIEW
-- -------------------------------------------------------
INSERT IGNORE INTO review (grade, review_content, by_order_no, created_by_customer_id, created_by_product_id, created_at, updated_at)
VALUES (5, 'Test1.', 1, 1, 2, NOW(), NOW());

INSERT IGNORE INTO review (grade, review_content, by_order_no, created_by_customer_id, created_by_product_id, created_at, updated_at)
VALUES (4, 'Test2.', 1, 2, 2, NOW(), NOW());

INSERT IGNORE INTO review (grade, review_content, by_order_no, created_by_customer_id, created_by_product_id, created_at, updated_at)
VALUES (3, 'Test3.', 1, 3, 2, NOW(), NOW());