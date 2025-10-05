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
     * 基本的なパッケージ構成テスト - コントローラ（クリーンアーキテクチャ準拠）
     */
    @ArchTest
    static final ArchRule controllersShouldBeInPresentationSubPackages = 
        classes()
            .that().haveSimpleNameEndingWith("Controller")
            .should().resideInAnyPackage("..presentation.web..", "..presentation.api..")
            .allowEmptyShould(true)
            .because("コントローラクラスはpresentation.webまたはpresentation.apiパッケージに配置すべき");
    
    /**
     * 基本的なパッケージ構成テスト - 設定クラス
     */
    @ArchTest
    static final ArchRule configClassesShouldBeInConfigPackage = 
        classes()
            .that().haveSimpleNameEndingWith("Config")
            .should().resideInAPackage("..config..")
            .allowEmptyShould(true)
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
            .allowEmptyShould(true)
            .because("ユーティリティクラスはutilパッケージに配置すべき");
    
    /**
     * アプリケーションサービスが適切なパッケージにある
     */
    @ArchTest
    static final ArchRule applicationServicesShouldBeInApplicationPackage = 
        classes()
            .that().haveSimpleNameEndingWith("ApplicationService")
            .should().resideInAPackage("..application.service..")
            .allowEmptyShould(true)
            .because("アプリケーションサービスはapplication.serviceパッケージに配置すべき");
    
    @Test
    void manualArchitectureTest() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.ngnmsn.template");
        
        // 手動でテスト実行 - allowEmptyShould(true)で同じ設定を適用
        classes()
            .that().haveSimpleNameEndingWith("Controller")
            .should().resideInAnyPackage("..presentation.web..", "..presentation.api..")
            .allowEmptyShould(true)
            .because("コントローラクラスはpresentation.webまたはpresentation.apiパッケージに配置すべき")
            .check(classes);
            
        classes()
            .that().haveSimpleNameEndingWith("Config")
            .should().resideInAPackage("..config..")
            .allowEmptyShould(true)
            .because("設定クラスはconfigパッケージに配置すべき")
            .check(classes);
            
        classes()
            .that().haveSimpleNameEndingWith("Util")
            .or().haveSimpleNameEndingWith("Utils")
            .should().resideInAPackage("..util..")
            .allowEmptyShould(true)
            .because("ユーティリティクラスはutilパッケージに配置すべき")
            .check(classes);
            
        classes()
            .that().haveSimpleNameEndingWith("ApplicationService")
            .should().resideInAPackage("..application.service..")
            .allowEmptyShould(true)
            .because("アプリケーションサービスはapplication.serviceパッケージに配置すべき")
            .check(classes);
        
        System.out.println("Basic architecture tests passed successfully!");
    }
}