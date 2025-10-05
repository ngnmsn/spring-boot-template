package com.ngnmsn.template.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.ngnmsn.template.presentation")
class PresentationLayerArchitectureTest {
    
    /**
     * Webコントローラの命名規則
     */
    @ArchTest
    static final ArchRule webControllersShouldBeNamed = 
        classes()
            .that().resideInAPackage("..presentation.web..")
            .and().haveSimpleNameNotEndingWith("Test")
            .should().haveSimpleNameEndingWith("Controller")
            .because("Webコントローラは'Controller'で終わる名前にすべき");
    
    /**
     * APIコントローラの命名規則
     */
    @ArchTest
    static final ArchRule apiControllersShouldBeNamed = 
        classes()
            .that().resideInAPackage("..presentation.api..")
            .should().haveSimpleNameEndingWith("ApiController")
            .orShould().haveSimpleNameEndingWith("RestController")
            .because("APIコントローラは'ApiController'または'RestController'で終わる名前にすべき");
    
    /**
     * Webコントローラは@Controllerアノテーションを持つ
     */
    @ArchTest
    static final ArchRule webControllersShouldBeAnnotated = 
        classes()
            .that().resideInAPackage("..presentation.web..")
            .and().haveSimpleNameEndingWith("Controller")
            .should().beAnnotatedWith("org.springframework.stereotype.Controller")
            .because("Webコントローラは@Controllerアノテーションを持つべき");
    
    /**
     * APIコントローラは@RestControllerアノテーションを持つ
     */
    @ArchTest
    static final ArchRule apiControllersShouldBeAnnotated = 
        classes()
            .that().resideInAPackage("..presentation.api..")
            .and().haveSimpleNameEndingWith("ApiController")
            .should().beAnnotatedWith("org.springframework.web.bind.annotation.RestController")
            .because("APIコントローラは@RestControllerアノテーションを持つべき");
    
    /**
     * フォームクラスの命名規則
     */
    @ArchTest
    static final ArchRule formsShouldBeNamed = 
        classes()
            .that().resideInAPackage("..presentation.form..")
            .should().haveSimpleNameEndingWith("Form")
            .because("フォームクラスは'Form'で終わる名前にすべき");
    
    /**
     * リクエストクラスの命名規則
     */
    @ArchTest
    static final ArchRule requestsShouldBeNamed = 
        classes()
            .that().resideInAPackage("..presentation.request..")
            .should().haveSimpleNameEndingWith("Request")
            .because("リクエストクラスは'Request'で終わる名前にすべき");
    
    /**
     * レスポンスクラスの命名規則
     */
    @ArchTest
    static final ArchRule responsesShouldBeNamed = 
        classes()
            .that().resideInAPackage("..presentation.response..")
            .and().areNotInnerClasses()
            .and().haveSimpleNameNotEndingWith("Test")
            .should().haveSimpleNameEndingWith("Response")
            .because("レスポンスクラスは'Response'で終わる名前にすべき");
    
    /**
     * コントローラはアプリケーションサービスのみに依存すべき
     */
    @ArchTest
    static final ArchRule controllersShouldOnlyDependOnApplicationServices = 
        classes()
            .that().resideInAnyPackage("..presentation.web..", "..presentation.api..")
            .and().haveSimpleNameEndingWith("Controller")
            .should().onlyDependOnClassesThat()
            .resideInAnyPackage(
                "java..",
                "javax..",
                "jakarta..",
                "org.springframework..",
                "org.slf4j..",
                "..presentation..",
                "..application.service..",
                "..application.command..",
                "..application.query..",
                "..application.exception..",
                "..application.dto.."
            )
            .because("コントローラはアプリケーションサービスのみに依存すべき");
}