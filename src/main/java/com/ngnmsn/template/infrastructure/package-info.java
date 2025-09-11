/**
 * インフラストラクチャ層
 * 
 * <p>技術的な実装を担う層で、以下の要素を含みます：
 * <ul>
 *   <li>{@link com.ngnmsn.template.infrastructure.repository} - リポジトリ実装</li>
 *   <li>{@link com.ngnmsn.template.infrastructure.config} - インフラ設定</li>
 *   <li>{@link com.ngnmsn.template.infrastructure.external} - 外部システム連携</li>
 * </ul>
 * 
 * <p><strong>依存関係の制約：</strong>
 * <ul>
 *   <li>domain、applicationパッケージに依存する</li>
 *   <li>presentationパッケージに依存してはならない</li>
 *   <li>外部フレームワークやライブラリの詳細を隠蔽する</li>
 * </ul>
 * 
 * @since 1.0
 */
@NonNullApi
@NonNullFields
package com.ngnmsn.template.infrastructure;

import org.springframework.lang.NonNullApi;
import org.springframework.lang.NonNullFields;