# 推奨コマンド一覧

## ビルド・実行
```bash
# 依存関係の解決・ビルド
./gradlew build

# アプリケーション起動
./gradlew bootRun

# jOOQコード生成（DBマイグレーション後）
./gradlew generateJooq
```

## テスト実行
```bash
# 単体テスト（E2E除く）
./gradlew test

# アーキテクチャテスト
./gradlew archTest

# E2Eテスト
./gradlew e2eTest

# 全テスト実行（CI用）
./gradlew ciTest

# パフォーマンステスト
./gradlew test --tests "SamplePerformanceTest"
```

## 品質チェック・レポート
```bash
# カバレッジレポート生成
./gradlew jacocoTestReport

# コード品質レポート生成
./gradlew qualityReport

# 依存関係分析
./gradlew dependencyReport

# コードメトリクス
./gradlew codeMetricsReport
```

## データベース管理（Flyway）
```bash
# DBマイグレーション実行
./gradlew flywayMigrate

# DBクリーン
./gradlew flywayClean

# マイグレーション情報確認
./gradlew flywayInfo
```

## システムコマンド（macOS）
```bash
# ファイル一覧
ls -la

# ディレクトリ移動
cd <path>

# ファイル検索
find . -name "*.java"

# 文字列検索
grep -r "pattern" .

# Git操作
git status
git log --oneline -10
git diff
```

## アクセス情報
- **アプリケーションURL**: http://localhost:8080/samples
- **ログインURL**: http://localhost:8080/login
- **テストユーザー**: test@example.com / password
