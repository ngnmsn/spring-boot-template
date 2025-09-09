# アーキテクチャドキュメント

## 設計思想
このプロジェクトは Robert C. Martin の Clean Architecture の原則に基づいて設計されています。

### 核となる原則
1. **依存関係逆転原則**: 抽象に依存し、具象に依存しない
2. **単一責任原則**: 各クラス・モジュールは単一の責任を持つ
3. **開放閉鎖原則**: 拡張に対して開いており、修正に対して閉じている
4. **リスコフ置換原則**: サブクラスはスーパークラスと置き換え可能
5. **インターフェース分離原則**: クライアントは使用しないメソッドに依存しない

### レイヤー詳細

#### Domain Layer（ドメイン層）
**責務**: ビジネスロジックの中核
**構成要素**:
- **エンティティ**: ビジネス上の重要なオブジェクト
- **バリューオブジェクト**: 不変で等価性で比較される値オブジェクト
- **ドメインサービス**: 複数エンティティにまたがるビジネスロジック
- **リポジトリポート**: データ永続化の抽象化

**制約**:
- フレームワークに依存しない純粋なJavaコード
- 他のレイヤーに依存してはならない
- I/O操作を直接行ってはならない

#### Application Layer（アプリケーション層）
**責務**: ユースケースの実現とオーケストレーション
**構成要素**:
- **アプリケーションサービス**: ユースケースの実装
- **コマンドオブジェクト**: 入力データの表現
- **クエリオブジェクト**: 検索条件の表現
- **レスポンスオブジェクト**: 出力データの表現

**制約**:
- ドメイン層のみに依存
- トランザクション境界を管理
- ビジネスロジックは含まない

#### Infrastructure Layer（インフラストラクチャ層）
**責務**: 外部システムとの結合
**構成要素**:
- **リポジトリアダプター**: ドメインポートの具象実装
- **設定クラス**: フレームワークの設定
- **外部API連携**: 外部システムとの通信

**制約**:
- ドメインとアプリケーション層に依存可能
- 技術的な詳細を隠蔽する

#### Presentation Layer（プレゼンテーション層）
**責務**: ユーザーインターフェースとHTTP通信
**構成要素**:
- **コントローラ**: HTTPリクエストの処理
- **フォーム**: HTML フォームの表現
- **リクエスト/レスポンス**: API通信のデータ表現

**制約**:
- アプリケーション層のみに依存
- ビジネスロジックは含まない
- データ変換のみ行う

## 実装パターン

### Repository Pattern
```java
// Domain Layer
public interface SampleRepositoryPort {
    Optional<Sample> findById(SampleId id);
    void save(Sample sample);
}

// Infrastructure Layer  
@Repository
public class JooqSampleRepositoryAdapter implements SampleRepositoryPort {
    // JOOQ実装
}
```

### Command Pattern
```java
// Application Layer
public class SampleCreateCommand {
    private final String text;
    private final Integer number;
    // コンストラクタ・ゲッター
}

// Usage
@Service
@Transactional
public class SampleApplicationService {
    public void createSample(SampleCreateCommand command) {
        // ユースケース実装
    }
}
```

### Domain Service Pattern
```java
public class SampleDomainService {
    public DisplayId generateUniqueDisplayId() {
        // 複雑なビジネスロジック
    }
}
```

## テスト戦略

### テストピラミッド
```
    ┌─────────┐
    │   E2E   │ <- 10%（画面・API）
    ├─────────┤  
    │ 統合テスト │ <- 20%（レイヤー結合）
    ├─────────┤
    │ 単体テスト │ <- 70%（各クラス）
    └─────────┘
```

### レイヤー別テスト方針
- **Domain Layer**: 単体テスト中心、90%以上のカバレッジ
- **Application Layer**: モックを使用した単体テスト、85%以上
- **Infrastructure Layer**: 統合テスト中心、70%以上
- **Presentation Layer**: WebMvcTestによるスライステスト、60%以上

## パフォーマンス要件
- **レスポンス時間**: 95%のリクエストが200ms以内
- **スループット**: 100 requests/second以上
- **メモリ使用量**: 初期512MB以下
- **起動時間**: 30秒以内

## セキュリティ考慮事項
- **入力検証**: 全ての外部入力をバリデーション
- **SQL インジェクション**: パラメータクエリの使用
- **XSS対策**: テンプレートエンジンでの自動エスケープ
- **CSRF対策**: Spring Security CSRF保護

## 運用・監視
- **ログレベル**: 本番環境はINFO以上
- **メトリクス**: Micrometer + Prometheus
- **ヘルスチェック**: Spring Boot Actuator
- **APM**: 外部監視ツールとの連携