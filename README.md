# Spring Boot Clean Architecture Template

## 概要
このプロジェクトはSpring Bootを使用したクリーンアーキテクチャのテンプレートです。
真のクリーンアーキテクチャの原則に従い、保守性・テスタビリティ・拡張性に優れた設計を実現しています。

## アーキテクチャ
### レイヤー構成
```
┌─────────────────────────┐
│ Presentation Layer      │ ← HTTP、UI、外部インターフェース
├─────────────────────────┤
│ Application Layer       │ ← ユースケース、オーケストレーション  
├─────────────────────────┤
│ Domain Layer           │ ← ビジネスルール、エンティティ（コア）
├─────────────────────────┤
│ Infrastructure Layer    │ ← DB、外部API、フレームワーク詳細
└─────────────────────────┘
```

### 依存関係の方向
- **Domain Layer**: 他のレイヤーに依存しない（最上位）
- **Application Layer**: Domain Layerにのみ依存
- **Infrastructure Layer**: Domain LayerとApplication Layerに依存
- **Presentation Layer**: Application Layerにのみ依存

### パッケージ構造
```
com.ngnmsn.template/
├── domain/                 # ドメイン層
│   ├── model/             # エンティティ・バリューオブジェクト
│   ├── service/           # ドメインサービス
│   ├── repository/        # リポジトリポート
│   └── exception/         # ドメイン例外
├── application/           # アプリケーション層
│   ├── service/           # アプリケーションサービス
│   ├── command/           # コマンドオブジェクト
│   ├── query/             # クエリオブジェクト
│   ├── response/          # レスポンスオブジェクト
│   └── exception/         # アプリケーション例外
├── infrastructure/       # インフラストラクチャ層
│   ├── repository/        # リポジトリ実装
│   ├── config/            # インフラ設定
│   └── external/          # 外部システム連携
└── presentation/         # プレゼンテーション層
    ├── web/              # Webコントローラ
    ├── api/              # REST APIコントローラ
    ├── form/             # フォームオブジェクト
    ├── request/          # APIリクエスト
    ├── response/         # APIレスポンス
    └── exception/        # プレゼンテーション例外処理
```

## 技術スタック
- **Java**: 21+
- **Spring Boot**: 3.x
- **Spring MVC**: Webレイヤー
- **Spring Data JPA**: データアクセス
- **jOOQ**: タイプセーフなSQL
- **MySQL**: データベース
- **Thymeleaf**: テンプレートエンジン
- **JUnit 5**: テストフレームワーク
- **ArchUnit**: アーキテクチャテスト
- **Selenium**: E2Eテスト

## セットアップ手順

### 前提条件
- Java 21以上
- MySQL 9.x
- Docker（オプション）

### 環境構築方法

#### MySQLの設定
```shell
# MySQLをインストール
brew install mysql

# mysqlを起動
mysql.server start

# rootユーザのパスワードを設定
mysql -u root
ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';

# rootユーザでログイン
mysql -uroot -p

# タイムゾーンを設定
SET GLOBAL time_zone = '+09:00';
SET time_zone = '+09:00';

# データベース作成
CREATE DATABASE template DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

#### リポジトリクローン・実行
```bash
# プロジェクトのクローン
git clone https://github.com/ngnmsn/spring-boot-template.git
cd spring-boot-template

# 依存関係の解決・ビルド
./gradlew build

# アプリケーション起動
./gradlew bootRun

# ブラウザで確認
# http://localhost:8080/samples
```

### テスト実行
```bash
# 単体テスト
./gradlew test

# アーキテクチャテスト
./gradlew archTest

# E2Eテスト
./gradlew e2eTest

# 全テスト実行
./gradlew ciTest

# パフォーマンステスト
./gradlew test --tests "SamplePerformanceTest"
```

### 品質チェック・レポート生成
```bash
# コード品質レポート生成
./gradlew qualityReport

# 依存関係分析
./gradlew dependencyReport

# カバレッジレポート
./gradlew jacocoTestReport

# コードメトリクス
./gradlew codeMetricsReport
```

## 開発ガイドライン

### 新機能追加の手順
1. **ドメインモデルの設計**: エンティティ・バリューオブジェクトを定義
2. **リポジトリポートの定義**: ドメイン層にインターフェースを作成
3. **ドメインサービス実装**: 複雑なビジネスロジックを実装
4. **アプリケーションサービス実装**: ユースケースを実装
5. **インフラストラクチャ実装**: リポジトリの具象実装を作成
6. **プレゼンテーション実装**: コントローラ・フォームを作成
7. **テスト作成**: 各層のテストを実装

### コーディング規約
- **命名規則**: 
  - クラス名: PascalCase
  - メソッド名: camelCase
  - 定数: SCREAMING_SNAKE_CASE
- **パッケージ命名**:
  - ドメインサービス: `*DomainService`
  - アプリケーションサービス: `*ApplicationService`
  - リポジトリポート: `*RepositoryPort`
  - リポジトリ実装: `*RepositoryAdapter`
- **アノテーション**:
  - アプリケーションサービス: `@Service` + `@Transactional`
  - リポジトリ実装: `@Repository`
  - コントローラ: `@Controller` or `@RestController`

### アーキテクチャ制約
以下の制約はArchUnitテストで自動検証されます：
- ドメイン層は他の層に依存してはならない
- アプリケーション層はドメイン層のみに依存する
- インフラ層はプレゼンテーション層に依存してはならない
- コントローラはアプリケーションサービスのみに依存する

## デプロイメント

### 本番環境設定
```yaml
# application-prod.yml
spring:
  datasource:
    url: ${DATABASE_URL}
    username: ${DATABASE_USERNAME}
    password: ${DATABASE_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
```

### Docker実行
```bash
docker build -t spring-boot-template .
docker run -p 8080:8080 spring-boot-template
```

## プロジェクトの特徴

### 実現された成果
- ✅ 真のクリーンアーキテクチャの実現
- ✅ 依存関係逆転の原則の適用
- ✅ レイヤー分離の徹底
- ✅ テスタビリティの向上
- ✅ 保守性・拡張性の確保
- ✅ アーキテクチャテストによる継続的品質保証

### アクセス・認証
以下のURLからアプリケーションにアクセスできます：

[http://localhost:8080/login](http://localhost:8080/login)

| 項目     | 入力値              |
|--------|------------------|
| ログインID | test@example.com |
| パスワード  | password         |

## ドキュメント
- [技術アーキテクチャドキュメント](./docs/ARCHITECTURE.md)
- [開発者向けクイックスタート](./docs/QUICK_START.md)
- [品質確認チェックリスト](./docs/QUALITY_CHECKLIST.md)
- [ER図](./docs/er/ER.md)

## ライセンス
MIT License

## 貢献
プルリクエストを歓迎します。大きな変更の場合は、まずissueを作成してください。