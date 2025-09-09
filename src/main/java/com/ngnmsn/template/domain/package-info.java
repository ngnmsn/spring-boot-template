/**
 * ドメイン層
 * 
 * <p>ビジネスロジックの中核を担う層で、以下の要素を含みます：
 * <ul>
 *   <li>{@link com.ngnmsn.template.domain.model} - エンティティ・バリューオブジェクト</li>
 *   <li>{@link com.ngnmsn.template.domain.service} - ドメインサービス</li>
 *   <li>{@link com.ngnmsn.template.domain.repository} - リポジトリポート</li>
 *   <li>{@link com.ngnmsn.template.domain.exception} - ドメイン例外</li>
 * </ul>
 * 
 * <p><strong>依存関係の制約：</strong>
 * <ul>
 *   <li>外部フレームワークに依存してはならない</li>
 *   <li>他の層（application、infrastructure、presentation）に依存してはならない</li>
 *   <li>純粋なJavaコードのみを使用する</li>
 * </ul>
 * 
 * @since 1.0
 */
@NonNullApi
@NonNullFields
package com.ngnmsn.template.domain;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;