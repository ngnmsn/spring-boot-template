package com.ngnmsn.template.controller;

import com.ngnmsn.template.consts.SampleConst;
import com.ngnmsn.template.consts.WebConst;
import com.ngnmsn.template.domain.sample.SampleCreateForm;
import com.ngnmsn.template.domain.sample.SampleDeleteForm;
import com.ngnmsn.template.domain.sample.SampleResult;
import com.ngnmsn.template.domain.sample.SampleResults;
import com.ngnmsn.template.domain.sample.SampleSearchForm;
import com.ngnmsn.template.domain.sample.SampleUpdateForm;
import com.ngnmsn.template.service.SampleService;
import jakarta.servlet.http.HttpSession;
import org.jooq.types.ULong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SampleControllerクラス
 */
@Controller
@RequestMapping(value = SampleConst.URL_SAMPLE)
public class SampleController {

  @Autowired
  private HttpSession session;

  @Autowired
  private SampleService sampleService;

  @PreAuthorize("hasAuthority('sample-read')")
  @GetMapping()
  String list(Model model) {

    SampleSearchForm sampleSearchForm = new SampleSearchForm();
    model.addAttribute("sampleSearchForm", sampleSearchForm);
    return SampleConst.TEMPLATE_SAMPLE_LIST;
  }

  @PreAuthorize("hasAuthority('sample-read')")
  @GetMapping(WebConst.URL_SEARCH)
  String search(@ModelAttribute("sampleSearchForm") SampleSearchForm form, Model model) {
    SampleResults sampleResults = sampleService.search(form);
    session.setAttribute("sampleSearchForm", form);
    model.addAttribute("sampleResults", sampleResults);
    return SampleConst.TEMPLATE_SAMPLE_LIST;
  }

  @PreAuthorize("hasAuthority('sample-read')")
  @GetMapping(WebConst.URL_SEARCH_RETURN)
  String returnSearch(Model mode) {
    SampleSearchForm sampleSearchForm = (SampleSearchForm) session.getAttribute("sampleSearchForm");
    if (sampleSearchForm == null) {
      sampleSearchForm = new SampleSearchForm();
    }
    return SampleConst.REDIRECT_SAMPLE_SEARCH + sampleSearchForm.generateQueryParameter();
  }

  @PreAuthorize("hasAuthority('sample-read')")
  @GetMapping(WebConst.URL_DETAIL)
  String detail(@PathVariable String displayId, Model model) {
    SampleResult sampleResult = sampleService.detail(displayId);
    model.addAttribute("sampleResult", sampleResult);
    return SampleConst.TEMPLATE_SAMPLE_DETAIL;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @GetMapping(WebConst.URL_CREATE)
  String create(Model model) {
    SampleCreateForm sampleCreateForm = new SampleCreateForm();
    model.addAttribute("sampleCreateForm", sampleCreateForm);
    return SampleConst.TEMPLATE_SAMPLE_CREATE;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @PostMapping(WebConst.URL_CREATE_CONFIRM)
  String createConfirm(@ModelAttribute("sampleCreateForm") @Validated SampleCreateForm form,
      BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return SampleConst.TEMPLATE_SAMPLE_CREATE;
    }
    session.setAttribute("sampleCreateForm", form);
    model.addAttribute("sampleCreateForm", form);
    return SampleConst.TEMPLATE_SAMPLE_CREATE_CONFIRM;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @GetMapping(WebConst.URL_CREATE_CONFIRM)
  String returnCreate(Model model) {
    SampleCreateForm form = (SampleCreateForm) session.getAttribute("sampleCreateForm");
    model.addAttribute("sampleCreateForm", form);
    return SampleConst.TEMPLATE_SAMPLE_CREATE;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @PostMapping(WebConst.URL_CREATE_PROCESS)
  String createProcess() {
    SampleCreateForm createForm = (SampleCreateForm) session.getAttribute("sampleCreateForm");
    sampleService.create(createForm);
    session.removeAttribute("sampleCreateForm");
    return SampleConst.REDIRECT_SAMPLE_CREATE_COMPLETE;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @GetMapping(WebConst.URL_CREATE_COMPLETE)
  String createComplete() {
    return SampleConst.TEMPLATE_SAMPLE_CREATE_COMPLETE;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @GetMapping(WebConst.URL_UPDATE)
  String update(@PathVariable String displayId, Model model) {
    SampleResult sampleResult = sampleService.detail(displayId);
    SampleUpdateForm form = new SampleUpdateForm();
    form.setText1(sampleResult.getText1());
    form.setNum1(sampleResult.getNum1());
    session.setAttribute("sampleUpdateId", sampleResult.getId());
    session.setAttribute("sampleUpdateDisplayId", sampleResult.getDisplayId());
    session.setAttribute("sampleUpdateForm", form);
    model.addAttribute("sampleUpdateDisplayId", sampleResult.getDisplayId());
    model.addAttribute("sampleUpdateForm", form);
    return SampleConst.TEMPLATE_SAMPLE_UPDATE;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @PostMapping(WebConst.URL_UPDATE_CONFIRM)
  String updateConfirm(@ModelAttribute("sampleUpdateForm") @Validated SampleUpdateForm form,
      BindingResult bindingResult, Model model) {
    String displayId = (String) session.getAttribute("sampleUpdateDisplayId");
    model.addAttribute("sampleUpdateDisplayId", displayId);
    SampleUpdateForm beforeForm = (SampleUpdateForm) session.getAttribute("sampleUpdateForm");
    if (bindingResult.hasErrors()) {
      return SampleConst.TEMPLATE_SAMPLE_UPDATE;
    }
    if (form.equals(beforeForm)) {
      model.addAttribute("alertMessage", "変更がありません。");
      return SampleConst.TEMPLATE_SAMPLE_UPDATE;
    }
    session.setAttribute("sampleUpdateForm", form);
    model.addAttribute("sampleUpdateForm", form);
    return SampleConst.TEMPLATE_SAMPLE_UPDATE_CONFIRM;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @GetMapping(WebConst.URL_UPDATE_CONFIRM)
  String returnUpdate(Model model) {
    String displayId = (String) session.getAttribute("sampleUpdateDisplayId");
    SampleUpdateForm form = (SampleUpdateForm) session.getAttribute("sampleUpdateForm");
    model.addAttribute("sampleUpdateDisplayId", displayId);
    model.addAttribute("sampleUpdateForm", form);
    return SampleConst.TEMPLATE_SAMPLE_UPDATE;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @PostMapping(WebConst.URL_UPDATE_PROCESS)
  String updateProcess() {
    ULong sampleUpdateId = (ULong) session.getAttribute("sampleUpdateId");
    SampleUpdateForm updateForm = (SampleUpdateForm) session.getAttribute("sampleUpdateForm");
    sampleService.update(sampleUpdateId, updateForm);
    session.removeAttribute("sampleUpdateId");
    session.removeAttribute("sampleUpdateDisplayId");
    session.removeAttribute("sampleUpdateForm");
    return SampleConst.REDIRECT_SAMPLE_UPDATE_COMPLETE;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @GetMapping(WebConst.URL_UPDATE_COMPLETE)
  String updateComplete() {
    return SampleConst.TEMPLATE_SAMPLE_UPDATE_COMPLETE;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @GetMapping(WebConst.URL_DELETE_CONFIRM)
  String deleteConfirm(@PathVariable String displayId, Model model) {
    SampleResult sampleResult = sampleService.detail(displayId);
    SampleDeleteForm form = new SampleDeleteForm();
    form.setText1(sampleResult.getText1());
    form.setNum1(sampleResult.getNum1());
    session.setAttribute("sampleDeleteId", sampleResult.getId());
    session.setAttribute("sampleDeleteDisplayId", sampleResult.getDisplayId());
    model.addAttribute("sampleDeleteDisplayId", sampleResult.getDisplayId());
    model.addAttribute("sampleDeleteForm", form);
    return SampleConst.TEMPLATE_SAMPLE_DELETE_CONFIRM;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @PostMapping(WebConst.URL_DELETE_PROCESS)
  String deleteProcess() {
    ULong sampleDeleteId = (ULong) session.getAttribute("sampleDeleteId");
    sampleService.delete(sampleDeleteId);
    session.removeAttribute("sampleDeleteId");
    session.removeAttribute("sampleDeleteDisplayId");
    return SampleConst.REDIRECT_SAMPLE_DELETE_COMPLETE;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @GetMapping(WebConst.URL_DELETE_COMPLETE)
  String deleteComplete() {
    return SampleConst.TEMPLATE_SAMPLE_DELETE_COMPLETE;
  }
}
