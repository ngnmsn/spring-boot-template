DELETE FROM permissions;
INSERT INTO permissions (permission_name) VALUES ('sample-write');
INSERT INTO permissions (permission_name) VALUES ('sample-read');

DELETE FROM roles;
INSERT INTO roles (role_name) VALUES ('admin');
INSERT INTO roles (role_name) VALUES ('write');
INSERT INTO roles (role_name) VALUES ('read');

DELETE FROM roles_permissions;
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, 1);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (1, 2);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (2, 1);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (2, 2);
INSERT INTO roles_permissions (role_id, permission_id) VALUES (3, 2);

DELETE FROM user_groups;
INSERT INTO user_groups (display_id, role_id, user_group_name) VALUES ('001ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 1, '管理者グループ');
INSERT INTO user_groups (display_id, role_id, user_group_name) VALUES ('002ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 2, '更新グループ');
INSERT INTO user_groups (display_id, role_id, user_group_name) VALUES ('003ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 3, '参照グループ');

-- password:password
DELETE FROM users;
INSERT INTO users (display_id, user_group_id, login_id, password, user_name, mail_address) VALUES ('001ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 1,'test1@example.com', '$2a$10$g1eLVd1alJ3gW9OiqudP7eG/3k6fKWPLPvDLnRR3Z3h6/WU4SHjbm', 'テストユーザ(管理)', 'test1@example.com');
INSERT INTO users (display_id, user_group_id, login_id, password, user_name, mail_address) VALUES ('002ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 2,'test2@example.com', '$2a$10$g1eLVd1alJ3gW9OiqudP7eG/3k6fKWPLPvDLnRR3Z3h6/WU4SHjbm', 'テストユーザ(更新)', 'test2@example.com');
INSERT INTO users (display_id, user_group_id, login_id, password, user_name, mail_address) VALUES ('003ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 3,'test3@example.com', '$2a$10$g1eLVd1alJ3gW9OiqudP7eG/3k6fKWPLPvDLnRR3Z3h6/WU4SHjbm', 'テストユーザ(参照)', 'test3@example.com');

DELETE FROM samples;
INSERT INTO samples (display_id, text1, num1) VALUES ('001ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test1', 1);
INSERT INTO samples (display_id, text1, num1) VALUES ('002ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test2', 2);
INSERT INTO samples (display_id, text1, num1) VALUES ('003ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test3', 3);
INSERT INTO samples (display_id, text1, num1) VALUES ('004ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test4', 4);
INSERT INTO samples (display_id, text1, num1) VALUES ('005ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test5', 5);
INSERT INTO samples (display_id, text1, num1) VALUES ('006ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test6', 6);
INSERT INTO samples (display_id, text1, num1) VALUES ('007ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test7', 7);
INSERT INTO samples (display_id, text1, num1) VALUES ('008ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test8', 8);
INSERT INTO samples (display_id, text1, num1) VALUES ('009ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test9', 9);
INSERT INTO samples (display_id, text1, num1) VALUES ('010ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test10', 10);
INSERT INTO samples (display_id, text1, num1) VALUES ('011ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test11', 11);
INSERT INTO samples (display_id, text1, num1) VALUES ('012ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test12', 12);
INSERT INTO samples (display_id, text1, num1) VALUES ('013ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test13', 13);
INSERT INTO samples (display_id, text1, num1) VALUES ('014ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test14', 14);
INSERT INTO samples (display_id, text1, num1) VALUES ('015ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test15', 15);
INSERT INTO samples (display_id, text1, num1) VALUES ('016ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test16', 16);
INSERT INTO samples (display_id, text1, num1) VALUES ('017ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test17', 17);
INSERT INTO samples (display_id, text1, num1) VALUES ('018ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test18', 18);
INSERT INTO samples (display_id, text1, num1) VALUES ('019ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test19', 19);
INSERT INTO samples (display_id, text1, num1) VALUES ('020ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test20', 20);
INSERT INTO samples (display_id, text1, num1) VALUES ('021ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test21', 21);
INSERT INTO samples (display_id, text1, num1) VALUES ('022ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test22', 22);
INSERT INTO samples (display_id, text1, num1) VALUES ('023ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test23', 23);
INSERT INTO samples (display_id, text1, num1) VALUES ('024ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test24', 24);
INSERT INTO samples (display_id, text1, num1) VALUES ('025ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test25', 25);
INSERT INTO samples (display_id, text1, num1) VALUES ('026ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test26', 26);
INSERT INTO samples (display_id, text1, num1) VALUES ('027ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test27', 27);
INSERT INTO samples (display_id, text1, num1) VALUES ('028ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test28', 28);
INSERT INTO samples (display_id, text1, num1) VALUES ('029ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test29', 29);
INSERT INTO samples (display_id, text1, num1) VALUES ('030ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test30', 30);
INSERT INTO samples (display_id, text1, num1) VALUES ('031ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test31', 31);
INSERT INTO samples (display_id, text1, num1) VALUES ('032ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test32', 32);
INSERT INTO samples (display_id, text1, num1) VALUES ('033ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test33', 33);
INSERT INTO samples (display_id, text1, num1) VALUES ('034ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test34', 34);
INSERT INTO samples (display_id, text1, num1) VALUES ('035ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test35', 35);
INSERT INTO samples (display_id, text1, num1) VALUES ('036ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test36', 36);
INSERT INTO samples (display_id, text1, num1) VALUES ('037ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test37', 37);
INSERT INTO samples (display_id, text1, num1) VALUES ('038ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test38', 38);
INSERT INTO samples (display_id, text1, num1) VALUES ('039ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test39', 39);
INSERT INTO samples (display_id, text1, num1) VALUES ('040ABCDEFGHIJKLMNOPQRSTUVWXYZABC', 'test40', 40);
