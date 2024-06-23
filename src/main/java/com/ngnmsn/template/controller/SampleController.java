package com.ngnmsn.template.controller;

import java.util.List;

import com.ngnmsn.template.domain.sample.*;
import jakarta.servlet.http.HttpSession;
import org.jooq.types.ULong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import com.ngnmsn.template.service.SampleService;


@Controller
@RequestMapping(value = "/sample")
public class SampleController {

    @Autowired
    private HttpSession session;

    @Autowired
    private SampleService sampleService;

    @GetMapping()
    String list(Model model){
        SampleSearchForm sampleSearchForm = new SampleSearchForm();
        model.addAttribute("sampleSearchForm", sampleSearchForm);
        return "sample/list";
    }

    @PostMapping("/search")
    String search(@ModelAttribute("sampleSearchForm") SampleSearchForm form, Model model){
        List<SampleResult> sampleResults = sampleService.search(form);
        model.addAttribute("sampleResults", sampleResults);
        return "sample/list";
    }

    @GetMapping("/{displayId}")
    String detail(@PathVariable String displayId, Model model){
        SampleResult sampleResult = sampleService.detail(displayId);
        model.addAttribute("sampleResult", sampleResult);
        return "sample/detail";
    }

    @GetMapping("/create")
    String create(Model model){
        SampleCreateForm sampleCreateForm = new SampleCreateForm();
        model.addAttribute("sampleCreateForm", sampleCreateForm);
        return "sample/create";
    }

    @PostMapping("/create/confirm")
    String createConfirm(@ModelAttribute("sampleCreateForm") SampleCreateForm form, Model model){
        session.setAttribute("sampleCreateForm", form);
        model.addAttribute("sampleCreateForm", form);
        return "sample/create_confirm";
    }

    @GetMapping("/create/confirm")
    String returnCreate(Model model) {
        SampleCreateForm form = (SampleCreateForm)session.getAttribute("sampleCreateForm");
        model.addAttribute("sampleCreateForm", form);
        return "sample/create";
    }

    @PostMapping("/create/process")
    String createProcess(){
        SampleCreateForm createForm = (SampleCreateForm)session.getAttribute("sampleCreateForm");
        sampleService.create(createForm);
        session.removeAttribute("sampleCreateForm");
        return "redirect:/sample/create/complete";
    }

    @GetMapping("/create/complete")
    String createComplete(){
        return "sample/create_complete";
    }

    @GetMapping("/{displayId}/update")
    String update(@PathVariable String displayId, Model model){
        SampleResult sampleResult = sampleService.detail(displayId);
        SampleUpdateForm form = new SampleUpdateForm();
        form.setText1(sampleResult.getText1());
        form.setNum1(sampleResult.getNum1());
        session.setAttribute("sampleUpdateId", sampleResult.getId());
        session.setAttribute("sampleUpdateDisplayId", sampleResult.getDisplayId());
        model.addAttribute("sampleUpdateDisplayId", sampleResult.getDisplayId());
        model.addAttribute("sampleUpdateForm", form);
        return "sample/update";
    }

    @PostMapping("/{displayId}/update/confirm")
    String updateConfirm(@ModelAttribute("sampleUpdateForm") SampleUpdateForm form, Model model){
        String displayId = (String)session.getAttribute("sampleUpdateDisplayId");
        session.setAttribute("sampleUpdateForm", form);
        model.addAttribute("sampleUpdateDisplayId", displayId);
        model.addAttribute("sampleUpdateForm", form);
        return "sample/update_confirm";
    }

    @GetMapping("/{displayId}/update/confirm")
    String returnUpdate(Model model) {
        SampleUpdateForm form = (SampleUpdateForm)session.getAttribute("sampleUpdateForm");
        model.addAttribute("sampleUpdateForm", form);
        return "sample/update";
    }

    @PostMapping("/{displayId}/update/process")
    String updateProcess(){
        ULong sampleUpdateId = (ULong)session.getAttribute("sampleUpdateId");
        SampleUpdateForm updateForm = (SampleUpdateForm)session.getAttribute("sampleUpdateForm");
        sampleService.update(sampleUpdateId, updateForm);
        session.removeAttribute("sampleUpdateId");
        session.removeAttribute("sampleUpdateDisplayId");
        session.removeAttribute("sampleUpdateForm");
        return "redirect:/sample/{displayId}/update/complete";
    }

    @GetMapping("/{displayId}/update/complete")
    String updateComplete(){
        return "sample/update_complete";
    }

    @GetMapping("/{displayId}/delete/confirm")
    String deleteConfirm(@PathVariable String displayId, Model model){
        SampleResult sampleResult = sampleService.detail(displayId);
        SampleDeleteForm form = new SampleDeleteForm();
        form.setText1(sampleResult.getText1());
        form.setNum1(sampleResult.getNum1());
        session.setAttribute("sampleDeleteId", sampleResult.getId());
        session.setAttribute("sampleDeleteDisplayId", sampleResult.getDisplayId());
        model.addAttribute("sampleDeleteDisplayId", sampleResult.getDisplayId());
        model.addAttribute("sampleDeleteForm", form);
        return "sample/delete_confirm";
    }

    @PostMapping("/{displayId}/delete/process")
    String deleteProcess(){
        ULong sampleDeleteId = (ULong)session.getAttribute("sampleDeleteId");
        sampleService.delete(sampleDeleteId);
        session.removeAttribute("sampleDeleteId");
        session.removeAttribute("sampleDeleteDisplayId");
        return "redirect:/sample/{displayId}/delete/complete";
    }

    @GetMapping("/{displayId}/delete/complete")
    String deleteComplete(){
        return "sample/delete_complete";
    }
}
