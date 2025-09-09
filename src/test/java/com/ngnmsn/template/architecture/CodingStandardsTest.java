package com.ngnmsn.template.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

@AnalyzeClasses(packages = "com.ngnmsn.template")
class CodingStandardsTest {
    
    /**
     * クラス名はPascalCaseであるべき
     */
    @ArchTest
    static final ArchRule classesShouldUsePascalCase = 
        classes()
            .should().haveSimpleNameMatching("^[A-Z][a-zA-Z0-9]*$")
            .because("クラス名はPascalCaseにすべき");
    
    /**
     * メソッド名はcamelCaseであるべき
     */
    @ArchTest
    static final ArchRule methodsShouldUseCamelCase = 
        methods()
            .that().areDeclaredInClassesThat().resideInAPackage("com.ngnmsn.template..")
            .and().areNotConstructors()
            .should().haveNameMatching("^[a-z][a-zA-Z0-9]*$")
            .because("メソッド名はcamelCaseにすべき");
    
    /**
     * 定数はSCREAMING_SNAKE_CASEであるべき
     */
    @ArchTest
    static final ArchRule constantsShouldUseScreamingSnakeCase = 
        fields()
            .that().areDeclaredInClassesThat().resideInAPackage("com.ngnmsn.template..")
            .and().areStatic()
            .and().areFinal()
            .and().arePublic()
            .should().haveNameMatching("^[A-Z][A-Z0-9_]*$")
            .because("パブリックな定数はSCREAMING_SNAKE_CASEにすべき");
    
    /**
     * ユーティリティクラスは適切に設計されるべき
     */
    @ArchTest
    static final ArchRule utilityClassesShouldBeFinal = 
        classes()
            .that().haveSimpleNameEndingWith("Util")
            .or().haveSimpleNameEndingWith("Utils")
            .or().haveSimpleNameEndingWith("Helper")
            .should().beFinale()
            .andShould().haveOnlyPrivateConstructors()
            .because("ユーティリティクラスはfinalでプライベートコンストラクタを持つべき");
    
    /**
     * テストクラスの命名規則
     */
    @ArchTest
    static final ArchRule testClassesShouldBeNamed = 
        classes()
            .that().resideInAnyPackage("..test..")
            .or().haveSimpleNameEndingWith("Test")
            .or().haveSimpleNameEndingWith("Tests")
            .should().haveSimpleNameEndingWith("Test")
            .orShould().haveSimpleNameEndingWith("Tests")
            .because("テストクラスは'Test'または'Tests'で終わる名前にすべき");
}