# My Portfolio

Java / Spring Boot で構築したポートフォリオサイトです。
管理画面から作品・ブログ・スキル・経歴などを管理できます。

---

## 使用技術

- **バックエンド**: Java 17 / Spring Boot 3.3.5
- **フロントエンド**: Thymeleaf / HTML / CSS / JavaScript
- **データベース**: MySQL 8.0
- **セキュリティ**: Spring Security（ログイン + 二段階パスコード認証）
- **その他**: Lombok / Spring Data JPA / Quill.js

---

## 主な機能

### 公開側
- トップページ（プロフィール・スキル円グラフ・経歴タイムライン・作品一覧）
- 作品詳細ページ（リッチテキスト対応）
- ブログ一覧・詳細ページ（リッチテキスト対応）
- お問い合わせフォーム（ジャンル選択付き）

### 管理画面（Admin）
- Spring Security によるログイン認証
- 二段階パスコード認証（6桁・5回失敗で2時間ロック）
- プロフィール編集（画像アップロード対応）
- スキル管理（自己評価％・円グラフ表示）
- 経歴管理（タイムライン表示）
- 作品管理（画像アップロード・Quillエディタ）
- ブログ管理（画像アップロード・Quillエディタ）
- お問い合わせ管理（既読・未読トグル・未読バッジ通知）

---

## 画面イメージ

| トップページ | 管理画面 |
|---|---|
| スキル円グラフ・経歴タイムライン | お問い合わせ未読バッジ通知 |

---

## ローカル環境での起動方法

### 必要な環境
- Java 17
- MySQL 8.0
- MAMP（またはMySQL単体）

### 手順

1. リポジトリをクローン
```
git clone https://github.com/Ibuki-kamiza/my-portfolio.git
```

2. MySQLでデータベースを作成
```sql
CREATE DATABASE my_portfolio;
```

3. `application.properties` のDB接続情報を環境に合わせて変更

4. Spring Boot アプリケーションを起動

5. ブラウザで `http://localhost:8080` にアクセス

---

## 工夫したポイント

- Spring Security で二段階認証を実装（パスコード5回失敗で2時間ロック）
- 管理画面のお問い合わせにiPhoneのような未読バッジ通知を実装
- Quill.jsでリッチテキストエディタを導入し、画像をGoogle Drive URLで挿入可能
- CSS / JavaScript は全て別ファイルに分離（インラインスタイル禁止）
- スクロールアニメーション・カスタムカーソル・背景浮遊図形を実装

---

## 作者

ibuki kamizakae