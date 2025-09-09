package com.ngnmsn.template.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.ngnmsn.template")
class ApplicationLayerArchitectureTest {
    
    /**
     * アプリケーションサービスの命名規則
     */
    @ArchTest
    static final ArchRule applicationServicesShouldBeNamed = 
        classes()
            .that().resideInAPackage("..application.service..")
            .should().haveSimpleNameEndingWith("ApplicationService")
            .allowEmptyShould(true)
            .because("アプリケーションサービスは'ApplicationService'で終わる名前にすべき");
    
    /**
     * アプリケーションサービスは適切なパッケージに配置される
     */
    @ArchTest
    static final ArchRule applicationServicesShouldBeInServicePackage = 
        classes()
            .that().resideInAPackage("..application.service..")
            .and().haveSimpleNameEndingWith("ApplicationService")
            .should().beTopLevelClasses()
            .allowEmptyShould(true)
            .because("アプリケーションサービスは適切なパッケージに配置すべき");
    
    /**
     * コマンドオブジェクトの命名規則
     */
    @ArchTest
    static final ArchRule commandsShouldBeNamed = 
        classes()
            .that().resideInAPackage("..application.command..")
            .should().haveSimpleNameEndingWith("Command")
            .allowEmptyShould(true)
            .because("コマンドオブジェクトは'Command'で終わる名前にすべき");
    
    /**
     * クエリオブジェクトの命名規則
     */
    @ArchTest
    static final ArchRule queriesShouldBeNamed = 
        classes()
            .that().resideInAPackage("..application.query..")
            .should().haveSimpleNameEndingWith("Query")
            .allowEmptyShould(true)
            .because("クエリオブジェクトは'Query'で終わる名前にすべき");
    
    /**
     * アプリケーション例外の命名規則
     */
    @ArchTest
    static final ArchRule applicationExceptionsShouldBeNamed = 
        classes()
            .that().resideInAPackage("..application.exception..")
            .should().haveSimpleNameEndingWith("Exception")
            .andShould().beAssignableTo(RuntimeException.class)
            .allowEmptyShould(true)
            .because("アプリケーション例外は適切な命名規則に従うべき");
}