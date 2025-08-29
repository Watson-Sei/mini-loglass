# HTTPステータスコードガイド

このドキュメントは、APIコントローラーで使用すべきHTTPステータスコードについて説明します。

## 参考資料
- [MDN Web Docs - HTTP レスポンスステータスコード](https://developer.mozilla.org/ja/docs/Web/HTTP/Status)

## よく使用されるステータスコード

### 2xx 成功レスポンス

#### 200 OK
- **用途**: リクエストが成功し、レスポンスボディにコンテンツを返す場合
- **使用例**: GET、PUT、PATCHリクエストの成功時
```kotlin
@GetMapping("/accounts")
fun list(): ResponseEntity<List<Account>> {
    return ResponseEntity.ok(accounts)
}
```

#### 201 Created
- **用途**: 新しいリソースの作成に成功した場合
- **使用例**: POSTリクエストでの新規作成時
- **現在の実装**: レスポンスボディなし、Locationヘッダーなし
```kotlin
@PostMapping("/accounts")
fun create(@RequestBody params: CreateAccountRequest): ResponseEntity<Void> {
    createUseCase.execute(params.code, params.name, params.accountType, params.parentCode)
    return ResponseEntity.status(HttpStatus.CREATED).build()
}
```

#### 204 No Content
- **用途**: リクエストは成功したが、レスポンスボディを返さない場合
- **使用例**: DELETE、レスポンスボディが不要なPUT/PATCH
```kotlin
@DeleteMapping("/accounts/{id}")
fun delete(@PathVariable id: Long): ResponseEntity<Void> {
    accountService.delete(id)
    return ResponseEntity.noContent().build()
}
```

### 4xx クライアントエラー

#### 400 Bad Request
- **用途**: リクエストが不正（バリデーションエラー等）
- **使用例**: 必須パラメータの欠如、データ形式の誤り
```kotlin
@ExceptionHandler(ValidationException::class)
fun handleValidation(e: ValidationException): ResponseEntity<String> {
    return ResponseEntity.badRequest().body(e.message)
}
```

#### 401 Unauthorized
- **用途**: 認証が必要だが、認証情報が提供されていない
- **使用例**: ログインが必要なエンドポイントへの未認証アクセス

#### 403 Forbidden
- **用途**: 認証はされているが、リソースへのアクセス権限がない
- **使用例**: 他ユーザーのリソースへのアクセス試行

#### 404 Not Found
- **用途**: リクエストされたリソースが存在しない
- **使用例**: 存在しないIDでのGET/PUT/DELETE
```kotlin
@GetMapping("/accounts/{id}")
fun findById(@PathVariable id: Long): ResponseEntity<Account> {
    return accountRepository.findById(id)
        ?.let { ResponseEntity.ok(it) }
        ?: ResponseEntity.notFound().build()
}
```

### 5xx サーバーエラー

#### 500 Internal Server Error
- **用途**: サーバー側で予期しないエラーが発生
- **使用例**: 通常は明示的に返さず、例外ハンドラーで処理
