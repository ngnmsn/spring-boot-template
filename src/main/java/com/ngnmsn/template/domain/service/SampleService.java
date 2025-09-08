package com.ngnmsn.template.domain.service;

import com.ngnmsn.template.domain.model.sample.SampleResult;
import com.ngnmsn.template.domain.model.sample.SampleResults;
import com.ngnmsn.template.form.sample.SampleCreateForm;
import com.ngnmsn.template.form.sample.SampleSearchForm;
import com.ngnmsn.template.form.sample.SampleUpdateForm;
import com.ngnmsn.template.application.port.SampleRepositoryPort;
import com.ngnmsn.template.util.StringUtil;
import org.jooq.types.ULong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SampleServiceクラス
 */
@Service
public class SampleService {

  private final SampleRepositoryPort sampleRepositoryPort;
  private final StringUtil stringUtil;

  public SampleService(SampleRepositoryPort sampleRepositoryPort, StringUtil stringUtil) {
    this.sampleRepositoryPort = sampleRepositoryPort;
    this.stringUtil = stringUtil;
  }

  /**
   * searchメソッド
   *
   * @param form 検索フォーム
   * @return List<code><</code>SampleResult<code>></code> 検索結果リスト
   */
  public SampleResults search(SampleSearchForm form) {

    String displayId = form.getDisplayId();
    String text1 = form.getText1();
    int page = form.getPage();
    int maxNumPerPage = form.getMaxNumPerPage();
    return sampleRepositoryPort.search(displayId, text1, page, maxNumPerPage);
  }

  /**
   * detailメソッド
   *
   * @param displayId 表示ID
   * @return SampleResult クエリ結果
   */
  public SampleResult detail(String displayId) {
    return sampleRepositoryPort.findByDisplayId(displayId);
  }

  /**
   * createメソッド
   *
   * @param form Sample作成用フォーム
   */
  public void create(SampleCreateForm form) {
    String displayId = stringUtil.generateUuid();
    sampleRepositoryPort.insert(displayId, form.getText1(), form.getNum1());
  }

  /**
   * updateメソッド
   *
   * @param id   ID
   * @param form Sample更新用フォーム
   */
  public void update(ULong id, SampleUpdateForm form) {
    sampleRepositoryPort.update(id, form.getText1(), form.getNum1());
  }

  /**
   * deleteメソッド
   *
   * @param id ID
   */
  public void delete(ULong id) {
    sampleRepositoryPort.delete(id);
  }
}
