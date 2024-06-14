# spring-boot-template
## 概要
Spring Bootのテンプレートプロジェクト。<br>
Spring Bootにおける基本的な処理の実装を行う。
## 構成要素
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
```
### IntelliJ
クローンしたリポジトリのフォルダを指定しIntelliJを開く
### ビルド
```
Gradle > Tasks > application > bootRun
```
で起動
### アクセス
[http://localhost/8080/sample](http://localhost/8080/sample)でアクセス可能
## ドキュメント
- [ER図](./docs/er/ER.md)
