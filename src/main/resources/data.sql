INSERT INTO user.admins (
    adminId, Name, Email, Password, Phone_Number,
    Role, Status, Created_At, Updated_At, Approved_At
)
VALUES (
           1, 'Admin', 'admin@example.com', 'hashed_password_here', '010-1234-5678',
           'SUPER', 'ACTIVE', NOW(), NOW(), NOW()
       );