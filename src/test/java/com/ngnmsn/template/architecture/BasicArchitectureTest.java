package com.ngnmsn.template.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.ngnmsn.template")
class BasicArchitectureTest {
    
    /**
     * 基本的なパッケージ構成テスト - コントローラ
     */
    @ArchTest
    static final ArchRule controllersShouldBeInControllerPackage = 
        classes()
            .that().haveSimpleNameEndingWith("Controller")
            .should().resideInAPackage("..controller..")
            .because("コントローラクラスはcontrollerパッケージに配置すべき");
    
    /**
     * 基本的なパッケージ構成テスト - 設定クラス
     */
    @ArchTest
    static final ArchRule configClassesShouldBeInConfigPackage = 
        classes()
            .that().haveSimpleNameEndingWith("Config")
            .should().resideInAPackage("..config..")
            .because("設定クラスはconfigパッケージに配置すべき");
    
    /**
     * 基本的なパッケージ構成テスト - ユーティリティクラス
     */
    @ArchTest
    static final ArchRule utilClassesShouldBeInUtilPackage = 
        classes()
            .that().haveSimpleNameEndingWith("Util")
            .or().haveSimpleNameEndingWith("Utils")
            .should().resideInAPackage("..util..")
            .because("ユーティリティクラスはutilパッケージに配置すべき");
    
    /**
     * アプリケーションサービスが適切なパッケージにある
     */
    @ArchTest
    static final ArchRule applicationServicesShouldBeInApplicationPackage = 
        classes()
            .that().haveSimpleNameEndingWith("ApplicationService")
            .should().resideInAPackage("..application.service..")
            .because("アプリケーションサービスはapplication.serviceパッケージに配置すべき");
    
    @Test
    void manualArchitectureTest() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.ngnmsn.template");
        
        // 手動でテスト実行
        controllersShouldBeInControllerPackage.check(classes);
        configClassesShouldBeInConfigPackage.check(classes);
        utilClassesShouldBeInUtilPackage.check(classes);
        applicationServicesShouldBeInApplicationPackage.check(classes);
        
        System.out.println("Basic architecture tests passed successfully!");
    }
}