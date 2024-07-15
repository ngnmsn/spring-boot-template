package com.ngnmsn.template.repository;

import com.ngnmsn.template.domain.sample.SampleResult;
import com.ngnmsn.template.domain.sample.SampleResults;
import java.io.Serializable;
import org.jooq.types.ULong;

/**
 * SampleRepositoryクラス
 */
public interface SampleRepository extends Serializable {

  public SampleResults search(String displayId, String text1, int page, int maxNumPerPage);

  public SampleResult findByDisplayId(String displayId);

  public void insert(String displayId, String text1, int num1);

  public void update(ULong id, String text1, int num1);

  public void delete(ULong id);
}
