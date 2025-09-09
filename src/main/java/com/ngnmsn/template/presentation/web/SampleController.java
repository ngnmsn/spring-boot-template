package com.ngnmsn.template.presentation.web;

import com.ngnmsn.template.application.service.SampleApplicationService;
import com.ngnmsn.template.application.query.SampleSearchQuery;
import com.ngnmsn.template.application.command.SampleCreateCommand;
import com.ngnmsn.template.application.command.SampleUpdateCommand;
import com.ngnmsn.template.application.exception.SampleNotFoundException;
import com.ngnmsn.template.application.exception.SampleValidationException;
import com.ngnmsn.template.domain.exception.SampleBusinessException;
import com.ngnmsn.template.presentation.form.sample.SampleSearchForm;
import com.ngnmsn.template.presentation.form.sample.SampleCreateForm;
import com.ngnmsn.template.presentation.form.sample.SampleUpdateForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
public class SampleController {
    private final SampleApplicationService sampleApplicationService;
    
    public SampleController(SampleApplicationService sampleApplicationService) {
        this.sampleApplicationService = Objects.requireNonNull(sampleApplicationService,
            "SampleApplicationServiceは必須です");
    }
    
    /**
     * サンプル一覧・検索画面
     * 純粋にHTTPリクエストの受付とレスポンス生成のみ
     */
    @GetMapping("/samples")
    public String search(@ModelAttribute SampleSearchForm form, Model model) {
        // Form → Query 変換（単純なデータ変換のみ）
        var query = new SampleSearchQuery(
            form.getDisplayId(),
            form.getText1(),
            form.getPage(),
            form.getMaxNumPerPage()
        );
        
        // アプリケーションサービスに委譲
        var results = sampleApplicationService.search(query);
        
        // モデルへの設定（単純な設定のみ）
        model.addAttribute("results", results);
        model.addAttribute("searchForm", form);
        
        return "sample/search";
    }
    
    /**
     * サンプル詳細画面
     * IDバリデーション以外のロジックは含まない
     */
    @GetMapping("/samples/{id}")
    public String detail(@PathVariable Long id, Model model) {
        // パスパラメータの基本検証のみ
        if (id == null || id <= 0) {
            model.addAttribute("errorMessage", "不正なIDです");
            return "error";
        }
        
        try {
            // アプリケーションサービスに委譲
            var sample = sampleApplicationService.findById(id);
            
            // モデルへの設定
            model.addAttribute("sample", sample);
            
            return "sample/detail";
            
        } catch (SampleNotFoundException e) {
            model.addAttribute("errorMessage", "サンプルが見つかりません");
            return "error";
        }
    }
    
    /**
     * サンプル作成画面表示
     */
    @GetMapping("/samples/create")
    public String createForm(Model model) {
        model.addAttribute("createForm", new SampleCreateForm());
        return "sample/create";
    }
    
    /**
     * サンプル作成処理
     * バリデーション結果の確認と変換処理のみ
     */
    @PostMapping("/samples")
    public String create(@Valid SampleCreateForm form, 
                        BindingResult result, 
                        Model model,
                        RedirectAttributes redirectAttributes) {
        
        // バリデーションエラーの確認（Spring Validationの結果のみ）
        if (result.hasErrors()) {
            return "sample/create";
        }
        
        try {
            // Form → Command 変換（単純なデータ変換のみ）
            var command = new SampleCreateCommand(form.getText1(), form.getNum1());
            
            // アプリケーションサービスに委譲
            var createdSample = sampleApplicationService.createSample(command);
            
            // 成功メッセージの設定
            redirectAttributes.addFlashAttribute("successMessage", "サンプルを作成しました");
            redirectAttributes.addFlashAttribute("createdId", createdSample.getId());
            
            return "redirect:/samples/" + createdSample.getId();
            
        } catch (SampleValidationException e) {
            // アプリケーション例外をプレゼンテーション用メッセージに変換
            model.addAttribute("errorMessage", e.getMessage());
            return "sample/create";
            
        } catch (SampleBusinessException e) {
            // ビジネス例外をプレゼンテーション用メッセージに変換
            model.addAttribute("errorMessage", e.getMessage());
            return "sample/create";
        }
    }
    
    /**
     * サンプル編集画面表示
     */
    @GetMapping("/samples/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("errorMessage", "不正なIDです");
            return "error";
        }
        
        try {
            var sample = sampleApplicationService.findById(id);
            
            // Response → Form 変換（表示用変換のみ）
            var editForm = new SampleUpdateForm();
            editForm.setText1(sample.getText1());
            editForm.setNum1(sample.getNum1());
            
            model.addAttribute("sample", sample);
            model.addAttribute("updateForm", editForm);
            
            return "sample/edit";
            
        } catch (SampleNotFoundException e) {
            model.addAttribute("errorMessage", "サンプルが見つかりません");
            return "error";
        }
    }
    
    /**
     * サンプル更新処理
     */
    @PutMapping("/samples/{id}")
    public String update(@PathVariable Long id,
                        @Valid SampleUpdateForm form,
                        BindingResult result,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        
        // 基本的なパラメータ検証
        if (id == null || id <= 0) {
            model.addAttribute("errorMessage", "不正なIDです");
            return "error";
        }
        
        // バリデーションエラーの確認
        if (result.hasErrors()) {
            try {
                var sample = sampleApplicationService.findById(id);
                model.addAttribute("sample", sample);
            } catch (SampleNotFoundException e) {
                model.addAttribute("errorMessage", "サンプルが見つかりません");
                return "error";
            }
            return "sample/edit";
        }
        
        try {
            // Form → Command 変換
            var command = new SampleUpdateCommand(form.getText1(), form.getNum1());
            
            // アプリケーションサービスに委譲
            var updatedSample = sampleApplicationService.updateSample(id, command);
            
            // 成功メッセージの設定
            redirectAttributes.addFlashAttribute("successMessage", "サンプルを更新しました");
            
            return "redirect:/samples/" + id;
            
        } catch (SampleNotFoundException e) {
            model.addAttribute("errorMessage", "サンプルが見つかりません");
            return "error";
            
        } catch (SampleValidationException | SampleBusinessException e) {
            // 例外をプレゼンテーション用メッセージに変換
            model.addAttribute("errorMessage", e.getMessage());
            
            try {
                var sample = sampleApplicationService.findById(id);
                model.addAttribute("sample", sample);
            } catch (SampleNotFoundException ex) {
                model.addAttribute("errorMessage", "サンプルが見つかりません");
                return "error";
            }
            
            return "sample/edit";
        }
    }
    
    /**
     * サンプル削除処理
     */
    @DeleteMapping("/samples/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        // 基本的なパラメータ検証
        if (id == null || id <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "不正なIDです");
            return "redirect:/samples";
        }
        
        try {
            // アプリケーションサービスに委譲
            sampleApplicationService.deleteSample(id);
            
            redirectAttributes.addFlashAttribute("successMessage", "サンプルを削除しました");
            
        } catch (SampleNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "サンプルが見つかりません");
            
        } catch (SampleBusinessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        
        return "redirect:/samples";
    }
    
    /**
     * 一括削除処理
     */
    @PostMapping("/samples/bulk-delete")
    public String bulkDelete(@RequestParam("selectedIds") List<Long> selectedIds,
                            RedirectAttributes redirectAttributes) {
        
        // 基本的なパラメータ検証
        if (selectedIds == null || selectedIds.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "削除対象を選択してください");
            return "redirect:/samples";
        }
        
        try {
            // アプリケーションサービスに委譲
            sampleApplicationService.bulkDeleteSamples(selectedIds);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                selectedIds.size() + "件のサンプルを削除しました");
            
        } catch (SampleBusinessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "削除処理でエラーが発生しました");
        }
        
        return "redirect:/samples";
    }
}