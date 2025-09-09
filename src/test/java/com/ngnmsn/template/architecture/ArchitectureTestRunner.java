package com.ngnmsn.template.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchConfiguration;

import java.io.File;

public class ArchitectureTestRunner {
    
    private static final String REPORT_DIR = "build/reports/archunit";
    
    public static void runWithReport() {
        // レポートディレクトリを作成
        new File(REPORT_DIR).mkdirs();
        
        // ArchUnitの設定
        ArchConfiguration.get().setProperty("archunit.report.format", "html");
        ArchConfiguration.get().setProperty("archunit.report.dir", REPORT_DIR);
        
        // テストクラスを実行
        JavaClasses classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.ngnmsn.template");
        
        // 各種ルールを実行
        CleanArchitectureTest.domainLayerShouldNotDependOnOtherLayers.check(classes);
        CleanArchitectureTest.applicationLayerShouldOnlyDependOnDomainLayer.check(classes);
        CleanArchitectureTest.infrastructureLayerShouldNotDependOnPresentationLayer.check(classes);
        CleanArchitectureTest.domainLayerShouldNotDependOnFrameworks.check(classes);
        
        // サイクル依存チェック
        CyclicDependencyTest.packagesShouldNotHaveCyclicDependencies.check(classes);
        CyclicDependencyTest.layersShouldNotHaveCyclicDependencies.check(classes);
        
        // カスタムルール実行
        CustomArchitectureRules.SAMPLE_SPECIFIC_RULES.check(classes);
        CustomArchitectureRules.NO_REFLECTION_IN_DOMAIN.check(classes);
        
        System.out.println("Architecture test report generated at: " + REPORT_DIR);
    }
}