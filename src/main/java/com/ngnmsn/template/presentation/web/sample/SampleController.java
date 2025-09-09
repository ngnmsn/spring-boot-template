package com.ngnmsn.template.presentation.web.sample;

import com.ngnmsn.template.consts.SampleConst;
import com.ngnmsn.template.consts.WebConst;
import com.ngnmsn.template.domain.model.sample.SampleResult;
import com.ngnmsn.template.domain.model.sample.SampleResults;
import com.ngnmsn.template.domain.service.SampleService;
import com.ngnmsn.template.application.command.SampleCreateCommand;
import com.ngnmsn.template.application.command.SampleUpdateCommand;
import com.ngnmsn.template.application.query.SampleSearchQuery;
import com.ngnmsn.template.presentation.form.sample.SampleCreateForm;
import com.ngnmsn.template.presentation.form.sample.SampleDeleteForm;
import com.ngnmsn.template.presentation.form.sample.SampleSearchForm;
import com.ngnmsn.template.presentation.form.sample.SampleUpdateForm;
import com.ngnmsn.template.presentation.response.sample.SampleDeleteResponse;
import com.ngnmsn.template.presentation.response.sample.SampleDetailResponse;
import com.ngnmsn.template.presentation.response.sample.SampleSearchResponse;
import com.ngnmsn.template.presentation.response.sample.SampleUpdateResponse;
import jakarta.servlet.http.HttpSession;
import org.jooq.types.ULong;
import org.springframework.beans.BeanUtils;
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
  String list(@ModelAttribute SampleSearchForm sampleSearchForm, Model model) {

    SampleSearchResponse sampleSearchResponse = new SampleSearchResponse();
    sampleSearchResponse.setSampleResults(new SampleResults());
    model.addAttribute("sampleSearchResponse", sampleSearchResponse);
    return SampleConst.TEMPLATE_SAMPLE_LIST;
  }

  @PreAuthorize("hasAuthority('sample-read')")
  @GetMapping(WebConst.URL_SEARCH)
  String search(@ModelAttribute SampleSearchForm sampleSearchForm,
      Model model) {
    // Form → Query conversion
    var query = new SampleSearchQuery(
        sampleSearchForm.getDisplayId(),
        sampleSearchForm.getText1(),
        sampleSearchForm.getPage(),
        sampleSearchForm.getMaxNumPerPage());
    SampleResults sampleResults = sampleService.search(query);
    SampleSearchResponse sampleSearchResponse = new SampleSearchResponse();
    sampleSearchResponse.setSampleResults(sampleResults);
    session.setAttribute("sampleSearchForm", sampleSearchForm);
    model.addAttribute("sampleSearchResponse", sampleSearchResponse);
    return SampleConst.TEMPLATE_SAMPLE_LIST;
  }

  @PreAuthorize("hasAuthority('sample-read')")
  @GetMapping(WebConst.URL_SEARCH_RETURN)
  String returnSearch() {
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
    SampleDetailResponse sampleDetailResponse = new SampleDetailResponse();
    sampleDetailResponse.setSampleResult(sampleResult);
    model.addAttribute("sampleDetailResponse", sampleDetailResponse);
    return SampleConst.TEMPLATE_SAMPLE_DETAIL;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @GetMapping(WebConst.URL_CREATE)
  String create(@ModelAttribute SampleCreateForm sampleCreateForm, Model model) {
    return SampleConst.TEMPLATE_SAMPLE_CREATE;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @PostMapping(WebConst.URL_CREATE_CONFIRM)
  String createConfirm(@ModelAttribute @Validated SampleCreateForm sampleCreateForm,
      BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return SampleConst.TEMPLATE_SAMPLE_CREATE;
    }
    session.setAttribute("sampleCreateForm", sampleCreateForm);
    return SampleConst.TEMPLATE_SAMPLE_CREATE_CONFIRM;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @GetMapping(WebConst.URL_CREATE_CONFIRM)
  String returnCreate(Model model) {
    SampleCreateForm sampleCreateForm = (SampleCreateForm) session.getAttribute("sampleCreateForm");
    model.addAttribute("sampleCreateForm", sampleCreateForm);
    return SampleConst.TEMPLATE_SAMPLE_CREATE;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @PostMapping(WebConst.URL_CREATE_PROCESS)
  String createProcess() {
    SampleCreateForm sampleCreateForm = (SampleCreateForm) session.getAttribute("sampleCreateForm");
    // Form → Command conversion
    var command = new SampleCreateCommand(
        sampleCreateForm.getText1(),
        sampleCreateForm.getNum1());
    sampleService.create(command);
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
  String update(@PathVariable String displayId, @ModelAttribute SampleUpdateForm sampleUpdateForm,
      Model model) {
    SampleResult sampleResult = sampleService.detail(displayId);
    sampleUpdateForm.setText1(sampleResult.getText1());
    sampleUpdateForm.setNum1(sampleResult.getNum1());
    SampleUpdateResponse sampleUpdateResponse = new SampleUpdateResponse();
    sampleUpdateResponse.setSampleUpdateDisplayId(sampleResult.getDisplayId());
    session.setAttribute("sampleUpdateId", sampleResult.getId());
    session.setAttribute("sampleUpdateDisplayId", sampleResult.getDisplayId());
    session.setAttribute("sampleUpdateForm", sampleUpdateForm);
    model.addAttribute("sampleUpdateResponse", sampleUpdateResponse);
    return SampleConst.TEMPLATE_SAMPLE_UPDATE;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @PostMapping(WebConst.URL_UPDATE_CONFIRM)
  String updateConfirm(@ModelAttribute @Validated SampleUpdateForm sampleUpdateForm,
      BindingResult bindingResult, Model model) {
    String sampleUpdateDisplayId = (String) session.getAttribute("sampleUpdateDisplayId");
    SampleUpdateResponse sampleUpdateResponse = new SampleUpdateResponse();
    sampleUpdateResponse.setSampleUpdateDisplayId(sampleUpdateDisplayId);
    model.addAttribute("sampleUpdateResponse", sampleUpdateResponse);
    SampleUpdateForm beforeForm = (SampleUpdateForm) session.getAttribute("sampleUpdateForm");
    if (bindingResult.hasErrors()) {
      return SampleConst.TEMPLATE_SAMPLE_UPDATE;
    }
    if (sampleUpdateForm.equals(beforeForm)) {
      model.addAttribute("alertMessage", "変更がありません。");
      return SampleConst.TEMPLATE_SAMPLE_UPDATE;
    }
    session.setAttribute("sampleUpdateForm", sampleUpdateForm);
    return SampleConst.TEMPLATE_SAMPLE_UPDATE_CONFIRM;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @GetMapping(WebConst.URL_UPDATE_CONFIRM)
  String returnUpdate(@ModelAttribute SampleUpdateForm sampleUpdateForm, Model model) {
    String sampleUpdateDisplayId = (String) session.getAttribute("sampleUpdateDisplayId");
    BeanUtils.copyProperties(session.getAttribute("sampleUpdateForm"),
        sampleUpdateForm);
    SampleUpdateResponse sampleUpdateResponse = new SampleUpdateResponse();
    sampleUpdateResponse.setSampleUpdateDisplayId(sampleUpdateDisplayId);
    model.addAttribute("sampleUpdateResponse", sampleUpdateResponse);
    return SampleConst.TEMPLATE_SAMPLE_UPDATE;
  }

  @PreAuthorize("hasAuthority('sample-write')")
  @PostMapping(WebConst.URL_UPDATE_PROCESS)
  String updateProcess() {
    ULong sampleUpdateId = (ULong) session.getAttribute("sampleUpdateId");
    SampleUpdateForm sampleUpdateForm =
        (SampleUpdateForm) session.getAttribute("sampleUpdateForm");
    // Form → Command conversion
    var command = new SampleUpdateCommand(
        sampleUpdateForm.getText1(),
        sampleUpdateForm.getNum1());
    sampleService.update(sampleUpdateId, command);
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
  String deleteConfirm(@PathVariable String displayId,
      @ModelAttribute SampleDeleteForm sampleDeleteForm, Model model) {
    SampleResult sampleResult = sampleService.detail(displayId);
    sampleDeleteForm.setText1(sampleResult.getText1());
    sampleDeleteForm.setNum1(sampleResult.getNum1());
    SampleDeleteResponse sampleDeleteResponse = new SampleDeleteResponse();
    sampleDeleteResponse.setSampleDeleteDisplayId(sampleResult.getDisplayId());
    session.setAttribute("sampleDeleteId", sampleResult.getId());
    session.setAttribute("sampleDeleteDisplayId", sampleResult.getDisplayId());
    model.addAttribute("sampleDeleteResponse", sampleDeleteResponse);
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
