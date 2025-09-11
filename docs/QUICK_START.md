# 開発者向けクイックスタートガイド

## 環境構築（5分）

### 必要なツール
```bash
# Java 21の確認
java -version

# Git の確認  
git --version

# IDE（推奨：IntelliJ IDEA）の準備
```

### プロジェクト取得・実行
```bash
# 1. プロジェクトのクローン
git clone <repository-url>
cd spring-boot-template

# 2. 依存関係解決・ビルド
./gradlew build

# 3. アプリケーション起動
./gradlew bootRun
```

### 動作確認
- ブラウザで http://localhost:8080/samples にアクセス
- サンプル一覧が表示されれば成功

## 簡単な機能追加（15分）

### 新しい機能「User」を追加してみる

#### 1. ドメインモデルの作成
```java
// src/main/java/com/ngnmsn/template/domain/model/user/User.java
public class User {
    private final UserId id;
    private UserName name;
    private EmailAddress email;
    
    // コンストラクタ・メソッド実装
}

// バリューオブジェクト
public final class UserName {
    private final String value;
    // 実装
}
```

#### 2. リポジトリポートの定義
```java  
// src/main/java/com/ngnmsn/template/domain/repository/user/UserRepositoryPort.java
public interface UserRepositoryPort {
    Optional<User> findById(UserId id);
    void save(User user);
}
```

#### 3. アプリケーションサービスの実装
```java
// src/main/java/com/ngnmsn/template/application/service/user/UserApplicationService.java
@Service
@Transactional
public class UserApplicationService {
    // ユースケース実装
}
```

#### 4. インフラ実装
```java
// src/main/java/com/ngnmsn/template/infrastructure/repository/jooq/user/JooqUserRepositoryAdapter.java
@Repository
public class JooqUserRepositoryAdapter implements UserRepositoryPort {
    // JOOQ実装
}
```

#### 5. コントローラ実装
```java
// src/main/java/com/ngnmsn/template/presentation/web/user/UserController.java
@Controller
public class UserController {
    // HTTP処理
}
```

### テストの追加
各層に対応するテストを作成：
```bash
src/test/java/com/ngnmsn/template/
├── domain/model/user/UserTest.java
├── application/service/user/UserApplicationServiceTest.java
├── infrastructure/repository/jooq/user/JooqUserRepositoryAdapterTest.java
└── presentation/web/user/UserControllerTest.java
```

### 実行・確認
```bash
# テスト実行
./gradlew test

# アーキテクチャテストで制約確認
./gradlew archTest

# アプリケーション起動
./gradlew bootRun
```

## デバッグのコツ

### よくあるエラー
1. **依存関係エラー**: アーキテクチャテストで検出される
2. **バリデーションエラー**: バリューオブジェクト作成時の制約違反
3. **トランザクションエラー**: @Transactional の設定不備

### 推奨デバッグ手順
1. 単体テストを先に実行
2. アーキテクチャテストで制約違反をチェック  
3. 統合テストで動作確認
4. ログレベルをDEBUGに変更して詳細確認

## IDE設定

### IntelliJ IDEA
1. **プロジェクトのインポート**: Gradle プロジェクトとして開く
2. **フォーマッター設定**: Google Java Style を適用
3. **プラグイン**: Lombok、Spring Boot プラグインを有効化

### VS Code  
1. **Extension Pack for Java** をインストール
2. **Spring Boot Extension Pack** をインストール
3. **Gradle Task** でタスク実行

## 参考資料
- [Clean Architecture 書籍](https://www.amazon.co.jp/dp/4048930656/)
- [Spring Boot リファレンス](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [ArchUnit ドキュメント](https://www.archunit.org/userguide/html/000_Index.html)