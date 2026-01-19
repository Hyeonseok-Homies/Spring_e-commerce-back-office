-- 1. admins (updated_at 확인됨)
INSERT IGNORE INTO admins (id, name, email, password, phone_number, role, status, created_at, updated_at, approved_at)
VALUES (1, '슈퍼관리자', 'admin@example.com', '$2a$04$7QTzheyj6jD3XDxvIjPW4OUb770j4j', '010-1111-1111', 'SUPER', 'ACTIVE', NOW(), NOW(), NOW());

-- 2. customers
INSERT IGNORE INTO customers (Id, Name, Email, Password, Phone_Number, Created_At, Updated_At, Status)
VALUES (1, '테스트고객', 'customer@test.com', 'pass123', '010-2222-2222', NOW(), NOW(), 'ACTIVE');

-- 3. products (외래키 명칭: Created_By_Admin_Id)
INSERT IGNORE INTO products (Id, Name, Category, Price, Stock, Status, Created_At, Updated_At, Created_By_Admin_Id)
VALUES (1, '테스트상품', 'ELECTRONICS', 100000, 50, 'ON_SALE', NOW(), NOW(), 1);

-- 4. orders (엔티티의 @JoinColumn 및 스키마 명칭 반영)
INSERT IGNORE INTO orders (
    Id, Order_No, Quantity, Total_Price, Status,
    Created_By_Admin_Id, Created_By_Customer_Id, Created_By_Product_Id,
    Created_At, Updated_At
)
VALUES (
    1, 'ORD-2026-0001', 1, 100000, 'COMPLETED',
    1, 1, 1,
    NOW(), NOW()
);