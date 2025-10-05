# Spring Boot Clean Architecture Template - プロジェクト概要

## プロジェクトの目的
Spring Bootを使用したクリーンアーキテクチャのテンプレートプロジェクト。Robert C. Martinのクリーンアーキテクチャ原則に基づいて設計され、保守性・テスタビリティ・拡張性に優れた設計を実現している。

## 技術スタック
- **Java**: 21+
- **Spring Boot**: 3.5.5
- **ビルドツール**: Gradle 8.x
- **Web**: Spring MVC + Thymeleaf
- **データアクセス**: jOOQ (タイプセーフSQL)
- **データベース**: MySQL 9.x
- **セキュリティ**: Spring Security
- **テスト**: JUnit 5, Mockito, ArchUnit, Selenium
- **マイグレーション**: Flyway

## アーキテクチャ構成

### レイヤー構造
```
com.ngnmsn.template/
├── domain/                 # ドメイン層（コア・最上位）
│   ├── model/             # エンティティ・バリューオブジェクト
│   ├── service/           # ドメインサービス
│   ├── repository/        # リポジトリポート（インターフェース）
│   └── exception/         # ドメイン例外
├── application/           # アプリケーション層
│   ├── service/           # アプリケーションサービス（ユースケース）
│   ├── command/           # コマンドオブジェクト
│   ├── query/             # クエリオブジェクト
│   └── response/          # レスポンスオブジェクト
├── infrastructure/       # インフラストラクチャ層
│   ├── repository/        # リポジトリ実装（アダプター）
│   ├── config/            # インフラ設定
│   └── adapter/           # 外部システムアダプター
└── presentation/         # プレゼンテーション層
    ├── web/              # Webコントローラ
    ├── api/              # REST APIコントローラ
    ├── form/             # フォームオブジェクト
    └── request/          # APIリクエスト
```

### 依存関係の方向
- **Domain Layer**: 他のレイヤーに依存しない（最上位・コア）
- **Application Layer**: Domain Layerにのみ依存
- **Infrastructure Layer**: Domain LayerとApplication Layerに依存
- **Presentation Layer**: Application Layerにのみ依存

## コア設計原則
1. **依存関係逆転原則**: 抽象に依存し、具象に依存しない
2. **単一責任原則**: 各クラス・モジュールは単一の責任を持つ
3. **開放閉鎖原則**: 拡張に対して開いており、修正に対して閉じている
4. **リスコフ置換原則**: サブクラスはスーパークラスと置き換え可能
5. **インターフェース分離原則**: クライアントは使用しないメソッドに依存しない
