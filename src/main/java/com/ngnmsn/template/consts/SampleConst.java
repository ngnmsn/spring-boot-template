package com.ngnmsn.template.consts;

/**
 * SampleConstクラス
 */
public class SampleConst {

  public static final String URL_SAMPLE = "/sample";

  public static final String REDIRECT_SAMPLE_SEARCH = "redirect:"
      + URL_SAMPLE
      + WebConst.URL_SEARCH;
  public static final String REDIRECT_SAMPLE_CREATE_COMPLETE = "redirect:"
      + URL_SAMPLE
      + WebConst.URL_CREATE_COMPLETE;
  public static final String REDIRECT_SAMPLE_UPDATE_COMPLETE = "redirect:"
      + URL_SAMPLE
      + WebConst.URL_UPDATE_COMPLETE;
  public static final String REDIRECT_SAMPLE_DELETE_COMPLETE = "redirect:"
      + URL_SAMPLE
      + WebConst.URL_DELETE_COMPLETE;

  public static final String TEMPLATE_SAMPLE_LIST = "sample/" + WebConst.TEMPLATE_LIST;
  public static final String TEMPLATE_SAMPLE_DETAIL = "sample/" + WebConst.TEMPLATE_DETAIL;
  public static final String TEMPLATE_SAMPLE_CREATE = "sample/" + WebConst.TEMPLATE_CREATE;
  public static final String TEMPLATE_SAMPLE_CREATE_CONFIRM = "sample/"
      + WebConst.TEMPLATE_CREATE_CONFIRM;
  public static final String TEMPLATE_SAMPLE_CREATE_COMPLETE = "sample/"
      + WebConst.TEMPLATE_CREATE_COMPLETE;
  public static final String TEMPLATE_SAMPLE_UPDATE = "sample/" + WebConst.TEMPLATE_UPDATE;
  public static final String TEMPLATE_SAMPLE_UPDATE_CONFIRM = "sample/"
      + WebConst.TEMPLATE_UPDATE_CONFIRM;
  public static final String TEMPLATE_SAMPLE_UPDATE_COMPLETE = "sample/"
      + WebConst.TEMPLATE_UPDATE_COMPLETE;
  public static final String TEMPLATE_SAMPLE_DELETE_CONFIRM = "sample/"
      + WebConst.TEMPLATE_DELETE_CONFIRM;
  public static final String TEMPLATE_SAMPLE_DELETE_COMPLETE = "sample/"
      + WebConst.TEMPLATE_DELETE_COMPLETE;
}
