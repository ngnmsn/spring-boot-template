/**
 * アプリケーション層
 * 
 * <p>ユースケースの実現とオーケストレーションを担う層で、以下の要素を含みます：
 * <ul>
 *   <li>{@link com.ngnmsn.template.application.service} - アプリケーションサービス</li>
 *   <li>{@link com.ngnmsn.template.application.command} - コマンドオブジェクト</li>
 *   <li>{@link com.ngnmsn.template.application.query} - クエリオブジェクト</li>
 *   <li>{@link com.ngnmsn.template.application.response} - レスポンスオブジェクト</li>
 *   <li>{@link com.ngnmsn.template.application.exception} - アプリケーション例外</li>
 * </ul>
 * 
 * <p><strong>依存関係の制約：</strong>
 * <ul>
 *   <li>domainパッケージのみに依存する</li>
 *   <li>infrastructure、presentationパッケージに依存してはならない</li>
 *   <li>トランザクション制御を担当する</li>
 * </ul>
 * 
 * @since 1.0
 */
@NonNullApi
@NonNullFields
package com.ngnmsn.template.application;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;