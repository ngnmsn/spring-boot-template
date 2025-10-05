# Spring Boot クリーンアーキテクチャ テンプレート

このプロジェクトは、Robert C. Martinのクリーンアーキテクチャを実装したSpring Bootテンプレートです。適切なレイヤー分離と、ArchUnitテストによる厳格な依存関係ルールの強制を実現しています。

## 技術スタック

- **Java**: 21
- **Spring Boot**: 3.5.5
- **データベース**: MySQL 9.x
- **O/Rマッパー**: jOOQ
- **テンプレートエンジン**: Thymeleaf
- **セキュリティ**: Spring Security
- **マイグレーション**: Flyway
- **ビルドツール**: Gradle

## クイックスタート

### 前提条件

- Java 21以上
- MySQL 9.x
- Gradle

### セットアップ

```bash
# プロジェクトのビルド
./gradlew build

# データベースマイグレーション
./gradlew flywayMigrate

# jOOQクラスの生成
./gradlew generateJooq

# アプリケーションの起動
./gradlew bootRun
```

### アクセス情報

- **アプリケーションURL**: http://localhost:8080/samples
- **ログイン認証情報**:
  - メールアドレス: `test@example.com`
  - パスワード: `password`

## アーキテクチャ

### レイヤー構造

```
com.ngnmsn.template/
├── domain/              # ドメイン層（コアビジネスロジック）
│   ├── model/          # エンティティ、値オブジェクト
│   ├── service/        # ドメインサービス
│   ├── repository/     # リポジトリポート（インターフェース）
│   └── exception/      # ドメイン例外
├── application/        # アプリケーション層（ユースケース）
│   ├── service/        # アプリケーションサービス（オーケストレーション）
│   ├── command/        # コマンドオブジェクト
│   ├── query/          # クエリオブジェクト
│   └── response/       # レスポンスオブジェクト
├── infrastructure/     # インフラストラクチャ層（技術的実装）
│   ├── repository/     # リポジトリアダプター（jOOQ実装）
│   ├── config/         # フレームワーク設定
│   └── adapter/        # 外部システムアダプター
└── presentation/       # プレゼンテーション層（ユーザーインターフェース）
    ├── web/            # Thymeleafコントローラー
    ├── api/            # REST APIコントローラー
    ├── form/           # フォームオブジェクト
    └── request/        # APIリクエストオブジェクト
```

### 依存関係ルール

ArchUnitテストにより強制される依存関係ルール:

- **Domain層**: 他のレイヤーに依存しない（純粋なJavaのみ）
- **Application層**: Domain層のみに依存
- **Infrastructure層**: DomainとApplication層に依存可能
- **Presentation層**: Application層のみに依存

### 命名規則

- ドメインサービス: `*DomainService`
- アプリケーションサービス: `*ApplicationService`
- リポジトリポート: `*RepositoryPort`（domain層のインターフェース）
- リポジトリアダプター: `*RepositoryAdapter`（infrastructure層の実装）
- コントローラー: `*Controller`
- 例外: `*Exception`

### アノテーション使用方法

- アプリケーションサービス: `@Service` + `@Transactional`
- ドメインサービス: アノテーションなし（純粋なJava）
- リポジトリ実装: `@Repository`
- Webコントローラー: `@Controller`
- REST APIコントローラー: `@RestController`

## テスト

### テスト実行

```bash
# 単体テスト（E2Eテストを除く）
./gradlew test

# アーキテクチャ準拠テスト
./gradlew archTest

# E2Eテスト（Selenium）
./gradlew e2eTest

# 全テスト実行（CI用）
./gradlew ciTest

# 特定のテストのみ実行
./gradlew test --tests "ClassName.methodName"

# パフォーマンステスト
./gradlew test --tests "SamplePerformanceTest"
```

### カバレッジレポート

```bash
# カバレッジレポート生成
./gradlew jacocoTestReport
```

レポートは `build/reports/jacoco/test/html/index.html` に生成されます。

## データベース管理

### データベース接続情報

- **URL**: `jdbc:mysql://localhost:3306/template`
- **ユーザー名**: `root`
- **パスワード**: `root`

### Flyway操作

```bash
# マイグレーション実行
./gradlew flywayMigrate

# マイグレーション状態確認
./gradlew flywayInfo

# データベースクリーン（警告：破壊的操作）
./gradlew flywayClean
```

### jOOQクラス生成

データベーススキーマを変更した後:

1. `src/main/resources/db/migration/` にFlywayマイグレーションファイルを作成
2. `./gradlew flywayMigrate` を実行
3. `./gradlew generateJooq` でjOOQクラスを再生成

## コード品質レポート

```bash
# カバレッジレポート生成
./gradlew jacocoTestReport

# 全品質レポート生成
./gradlew qualityReport

# 依存関係分析
./gradlew dependencyReport

# コードメトリクスレポート
./gradlew codeMetricsReport
```

## 重要な設計原則

- **依存性逆転の原則**: Domain層がインターフェース（ポート）を定義し、Infrastructure層が実装（アダプター）を提供
- **ビジネスロジックの配置**: ビジネスロジックはドメインサービスに、アプリケーションサービスにはユースケースのオーケストレーションのみ
- **フレームワーク非依存**: Domain層はフレームワークに依存せず、`java.util.*`、`java.time.*`のみ使用可能
- **アーキテクチャ検証**: ArchUnitテストにより依存関係ルール違反を検出

## ライセンス

MIT License

## 開発者向け情報

詳細な開発ガイドは [CLAUDE.md](./CLAUDE.md) を参照してください。
