package com.ngnmsn.template.service;

import com.ngnmsn.template.domain.sample.SampleCreateForm;
import com.ngnmsn.template.domain.sample.SampleResult;
import com.ngnmsn.template.domain.sample.SampleSearchForm;
import com.ngnmsn.template.domain.sample.SampleUpdateForm;
import com.ngnmsn.template.repository.impl.SampleRepositoryImpl;
import com.ngnmsn.template.util.StringUtil;
import java.util.List;
import org.jooq.types.ULong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SampleServiceクラス
 */
@Service
public class SampleService {

  @Autowired
  private SampleRepositoryImpl sampleRepositoryImpl;

  @Autowired
  private StringUtil stringUtil;

  /**
   * searchメソッド
   *
   * @param form 検索フォーム
   * @return List<code><</code>SampleResult<code>></code> 検索結果リスト
   */
  public List<SampleResult> search(SampleSearchForm form) {

    String displayId = form.getDisplayId();
    String text1 = form.getText1();
    int page = form.getPage();
    int maxNumPerPage = form.getMaxNumPerPage();
    return sampleRepositoryImpl.search(displayId, text1, page, maxNumPerPage);
  }

  /**
   * detailメソッド
   *
   * @param displayId 表示ID
   * @return SampleResult クエリ結果
   */
  public SampleResult detail(String displayId) {
    return sampleRepositoryImpl.findByDisplayId(displayId);
  }

  /**
   * createメソッド
   *
   * @param form Sample作成用フォーム
   */
  public void create(SampleCreateForm form) {
    String displayId = stringUtil.generateUuid();
    sampleRepositoryImpl.insert(displayId, form.getText1(), form.getNum1());
  }

  /**
   * updateメソッド
   *
   * @param id   ID
   * @param form Sample更新用フォーム
   */
  public void update(ULong id, SampleUpdateForm form) {
    sampleRepositoryImpl.update(id, form.getText1(), form.getNum1());
  }

  /**
   * deleteメソッド
   *
   * @param id ID
   */
  public void delete(ULong id) {
    sampleRepositoryImpl.delete(id);
  }
}
