package com.ngnmsn.template.controller;

import java.util.List;

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

import com.ngnmsn.template.domain.sample.SampleSearchForm;
import com.ngnmsn.template.domain.sample.SampleResult;
import com.ngnmsn.template.domain.sample.SampleCreateForm;
import com.ngnmsn.template.domain.sample.SampleUpdateForm;
import com.ngnmsn.template.service.SampleService;


@Controller
@RequestMapping(value = "/sample")
public class SampleController {

    @Autowired
    private HttpSession session;

    @Autowired
    private SampleService sampleService;

    @GetMapping()
    String list(@ModelAttribute("sampleSearchForm") SampleSearchForm form, Model model){
        return "sample/list";
    }

    @PostMapping("/search")
    String search(@ModelAttribute("sampleSearchForm") SampleSearchForm form, Model model){
        List<SampleResult> sampleResults = sampleService.search(form);
        model.addAttribute("sampleResults", sampleResults);
        return "sample/list";
    }

    @GetMapping("/{id}")
    String detail(@PathVariable String id, Model model){
        SampleResult sampleResult = sampleService.detail(id);
        model.addAttribute("sampleResult", sampleResult);
        return "sample/detail";
    }

    @GetMapping("/create")
    String create(@ModelAttribute("sampleCreateForm") SampleCreateForm form, Model model){
        return "sample/create";
    }

    @PostMapping("/create/confirm")
    String createConfirm(@ModelAttribute("sampleCreateForm") SampleCreateForm form, Model model){
        session.setAttribute("sampleCreateForm", form);
        return "sample/create_confirm";
    }

    @PostMapping("/create/process")
    String createProcess(@ModelAttribute("sampleCreateForm") SampleCreateForm form, Model model){
        SampleCreateForm createForm = (SampleCreateForm)session.getAttribute("sampleCreateForm");
        sampleService.create(createForm);
        return "redirect:/sample/create/complete";
    }

    @GetMapping("/create/complete")
    String createComplete(){
        return "sample/create_complete";
    }

    @GetMapping("/{id}/update")
    String update(@PathVariable String id, Model model){
        SampleResult sampleResult = sampleService.detail(id);
        SampleUpdateForm form = new SampleUpdateForm();
        form.setText1(sampleResult.getText1());
        form.setNum1(sampleResult.getNum1());
        session.setAttribute("sampleUpdateId", sampleResult.getId());
        model.addAttribute("sampleUpdateId", sampleResult.getId());
        model.addAttribute("sampleUpdateForm", form);
        return "sample/update";
    }

    @PostMapping("/{id}/update/confirm")
    String updateConfirm(@ModelAttribute("sampleUpdateForm") SampleUpdateForm form, Model model){
        ULong sampleUpdateId = (ULong)session.getAttribute("sampleUpdateId");
        session.setAttribute("sampleUpdateForm", form);
        model.addAttribute("sampleUpdateId", sampleUpdateId);
        model.addAttribute("sampleUpdateForm", form);
        return "sample/update_confirm";
    }

    @PostMapping("/{id}/update/process")
    String updateProcess(){
        ULong sampleUpdateId = (ULong)session.getAttribute("sampleUpdateId");
        SampleUpdateForm updateForm = (SampleUpdateForm)session.getAttribute("sampleUpdateForm");
        sampleService.update(sampleUpdateId, updateForm);
        return "redirect:/sample/{id}/update/complete";
    }

    @GetMapping("/{id}/update/complete")
    String updateComplete(){
        return "sample/update_complete";
    }
}
