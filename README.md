# spring-boot-template

## 概要

Spring Bootのテンプレートプロジェクト。<br>
Spring Bootにおける基本的な処理の実装を行う。

## 構成要素

- サーバサイド
    - 認証
    - 基本的なCRUD
        - 一覧
        - 詳細
        - 作成
        - 更新
        - 削除
    - テスト
        - ControllerクラスのUT
        - ServiceクラスのUT
        - RepositoryクラスのUT
        - UtilクラスのUT
    - コード規約チェック<br>
      CheckStyleを使用し、Googleのコード規約でチェック
- クライアントサイド
    - Bootstrapによるデザイン実装
    - JQueryによるJavaScript実装

## 開発環境

| 項目                                                                                          | 説明                | バージョン             |
|---------------------------------------------------------------------------------------------|-------------------|-------------------|
| [Java](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/what-is-corretto-21.html) | 言語                | Amazon Correto 21 |
| [Gradle](https://gradle.org/)                                                               | ビルドツール            | 8.7               |
| [Spring Boot](https://spring.io/projects/spring-boot)                                       | Javaフレームワーク       | 3.2.5             |
| [Thymeleaf](https://www.thymeleaf.org/)                                                     | テンプレートエンジン        | 3.1.2             |
| [Flyway](https://flywaydb.org/)                                                             | マイグレーションツール       | 9.22.3            |
| [jOOQ](https://www.jooq.org/)                                                               | ORマッパー            | 3.18.14           |
| [MySQL](https://www.mysql.com/jp/)                                                          | DB                | 8.3.0             |
| [CheckStyle](https://checkstyle.sourceforge.io/)                                            | コード規約チェック         | 10.17.0           |
| [Bootstrap](https://getbootstrap.jp/)                                                       | CSSフレームワーク        | 5.3.3　            |
| [JQuery](https://jquery.com/)                                                               | JavaScriptフレームワーク | 3.7.1             |

## 環境構築方法

### MySQLの設定

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

### リポジトリクローン

```
git clone https://github.com/ngnmsn/spring-boot-template.git
cd spring-boot-template
```

### 実行

```
./gradlew bootRun
```

### テスト

```
./gradlew test
```

### コード規約チェック

```
./gradlew checkStyleMain
./gradlew checkStyleTest
```

### アクセス

以下の画面よりログインする。

[http://localhost:8080/login](http://localhost:8080/login)

| 項目     | 入力値              |
|--------|------------------|
| ログインID | test@example.com |
| パスワード  | password         |

## ドキュメント

- [ER図](./docs/er/ER.md)
