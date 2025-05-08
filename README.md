# TODO アプリ

KotlinとSpring Frameworkを使用したシンプルなTODOアプリケーションです。

## 技術スタック

- 言語: Kotlin
- フレームワーク: Spring Boot
- データベース: H2 (インメモリ)
- ビルドツール: Gradle (Kotlin DSL)

## 機能

- TODOタスクの作成
- TODOタスクの一覧表示
- TODOタスクの詳細表示
- TODOタスクの更新
- TODOタスクの削除
- TODOタスクの完了/未完了の切り替え

## プロジェクト構成

```
src/main/kotlin/com/mofumofu/todoapp/
├── TodoAppApplication.kt        # アプリケーションのエントリーポイント
├── controller/                  # RESTコントローラー
│   └── TodoController.kt        # TODOのエンドポイント
├── model/                       # データモデル
│   └── Todo.kt                  # TODOエンティティ
├── repository/                  # データアクセス
│   └── TodoRepository.kt        # TODOのリポジトリ
└── service/                     # ビジネスロジック
    └── TodoService.kt           # TODOのサービス
```

## APIエンドポイント

| メソッド | URL                    | 説明                       |
|---------|------------------------|----------------------------|
| GET     | /api/todos             | すべてのTODOを取得          |
| GET     | /api/todos/{id}        | 指定IDのTODOを取得          |
| POST    | /api/todos             | 新しいTODOを作成            |
| PUT     | /api/todos/{id}        | 指定IDのTODOを更新          |
| DELETE  | /api/todos/{id}        | 指定IDのTODOを削除          |
| PATCH   | /api/todos/{id}/toggle | 指定IDのTODOの状態を切り替え |

## セットアップと実行方法

### 前提条件

- JDK 17以上
- Gradle

### ビルドと実行

```bash
# プロジェクトのビルド
./gradlew build

# アプリケーションの実行
./gradlew bootRun
```

アプリケーションは http://localhost:8080 で実行されます。

### H2コンソールへのアクセス

H2データベースの管理コンソールは http://localhost:8080/h2-console で利用可能です。

接続情報:
- JDBC URL: `jdbc:h2:mem:tododb`
- ユーザー名と パスワードは application.properties ファイルで設定されています
