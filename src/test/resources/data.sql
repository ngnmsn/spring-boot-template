-- E2Eテスト用の基本データ
INSERT INTO samples (id, display_id, text1, num1, created_at, updated_at) 
VALUES 
  (1, '001ABCDEFGHIJKLMNOPQRSTUVWXYZ1234', 'テストデータ1', 100, NOW(), NOW()),
  (2, '002ABCDEFGHIJKLMNOPQRSTUVWXYZ5678', 'テストデータ2', 200, NOW(), NOW()),
  (3, '003ABCDEFGHIJKLMNOPQRSTUVWXYZ9012', 'テストデータ3', 300, NOW(), NOW());