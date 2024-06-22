# spring-boot-template
## 概要
Spring Bootのテンプレートプロジェクト。<br>
Spring Bootにおける基本的な処理の実装を行う。
## 構成要素
- 認証
- 基本的なCRUD
  - 一覧
  - 詳細
  - 作成
  - 更新
  - 削除
## 開発環境
| 項目                                                                                          | 説明          | バージョン             |
|---------------------------------------------------------------------------------------------|-------------|-------------------|
| [Java](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/what-is-corretto-21.html) | 言語          | Amazon Correto 21 |
| [Gradle](https://gradle.org/)                                                               | ビルドツール      | 8.7               |
| [Spring Boot](https://spring.io/projects/spring-boot)                                       | Javaフレームワーク | 3.2.5             |
| [Thymeleaf](https://www.thymeleaf.org/)                                                     | テンプレートエンジン  | 3.1.2             |
| [Flyway](https://flywaydb.org/)                                                             | マイグレーションツール | 9.22.3            |
| [jOOQ](https://www.jooq.org/)                                                               | ORマッパー      | 3.18.14           |
| [MySQL](https://www.mysql.com/jp/)                                                          | DB          | 8.3.0             |

## 環境構築方法
### リポジトリクローン
```
git clone https://github.com/ngnmsn/spring-boot-template.git
cd spring-boot-template
```
### ビルド
```
./gradlew bootRun
```
### アクセス
以下の画面よりログインする。

[http://localhost:8080/login](http://localhost:8080/login)

| 項目 | 入力値              |
| ---- |------------------|
| ログインID | test@example.com |
| パスワード | password         |

## ドキュメント
- [ER図](./docs/er/ER.md)
