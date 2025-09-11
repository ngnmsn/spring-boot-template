/**
 * プレゼンテーション層
 * 
 * <p>ユーザーインターフェースを担う層で、以下の要素を含みます：
 * <ul>
 *   <li>{@link com.ngnmsn.template.presentation.web} - Webコントローラ</li>
 *   <li>{@link com.ngnmsn.template.presentation.api} - REST APIコントローラ</li>
 *   <li>{@link com.ngnmsn.template.presentation.form} - フォームオブジェクト</li>
 *   <li>{@link com.ngnmsn.template.presentation.request} - APIリクエスト</li>
 *   <li>{@link com.ngnmsn.template.presentation.response} - APIレスポンス</li>
 *   <li>{@link com.ngnmsn.template.presentation.exception} - プレゼンテーション例外処理</li>
 * </ul>
 * 
 * <p><strong>依存関係の制約：</strong>
 * <ul>
 *   <li>applicationパッケージのサービスのみに依存する</li>
 *   <li>domain、infrastructureパッケージに直接依存してはならない</li>
 *   <li>入力値の検証とフォーマット変換を担当する</li>
 * </ul>
 * 
 * @since 1.0
 */
@NonNullApi
@NonNullFields
package com.ngnmsn.template.presentation;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;