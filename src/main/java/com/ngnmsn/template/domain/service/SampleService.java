package com.ngnmsn.template.domain.service;

import com.ngnmsn.template.domain.model.sample.SampleResult;
import com.ngnmsn.template.domain.model.sample.SampleResults;
import com.ngnmsn.template.application.command.SampleCreateCommand;
import com.ngnmsn.template.application.command.SampleUpdateCommand;
import com.ngnmsn.template.application.query.SampleSearchQuery;
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
   * @param query 検索クエリ
   * @return List<code><</code>SampleResult<code>></code> 検索結果リスト
   */
  public SampleResults search(SampleSearchQuery query) {

    String displayId = query.getDisplayId();
    String text1 = query.getText1();
    int page = query.getPage();
    int maxNumPerPage = query.getMaxNumPerPage();
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
   * @param command Sample作成用コマンド
   */
  public void create(SampleCreateCommand command) {
    String displayId = stringUtil.generateUuid();
    sampleRepositoryPort.insert(displayId, command.getText1(), command.getNum1());
  }

  /**
   * updateメソッド
   *
   * @param id   ID
   * @param command Sample更新用コマンド
   */
  public void update(ULong id, SampleUpdateCommand command) {
    sampleRepositoryPort.update(id, command.getText1(), command.getNum1());
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
