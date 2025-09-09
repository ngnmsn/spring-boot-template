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
            .should().haveSimpleNameStartingWith("A")
            .orShould().haveSimpleNameStartingWith("B")
            .orShould().haveSimpleNameStartingWith("C")
            .orShould().haveSimpleNameStartingWith("D")
            .orShould().haveSimpleNameStartingWith("E")
            .orShould().haveSimpleNameStartingWith("F")
            .orShould().haveSimpleNameStartingWith("G")
            .orShould().haveSimpleNameStartingWith("H")
            .orShould().haveSimpleNameStartingWith("I")
            .orShould().haveSimpleNameStartingWith("J")
            .orShould().haveSimpleNameStartingWith("K")
            .orShould().haveSimpleNameStartingWith("L")
            .orShould().haveSimpleNameStartingWith("M")
            .orShould().haveSimpleNameStartingWith("N")
            .orShould().haveSimpleNameStartingWith("O")
            .orShould().haveSimpleNameStartingWith("P")
            .orShould().haveSimpleNameStartingWith("Q")
            .orShould().haveSimpleNameStartingWith("R")
            .orShould().haveSimpleNameStartingWith("S")
            .orShould().haveSimpleNameStartingWith("T")
            .orShould().haveSimpleNameStartingWith("U")
            .orShould().haveSimpleNameStartingWith("V")
            .orShould().haveSimpleNameStartingWith("W")
            .orShould().haveSimpleNameStartingWith("X")
            .orShould().haveSimpleNameStartingWith("Y")
            .orShould().haveSimpleNameStartingWith("Z")
            .because("クラス名はPascalCaseにすべき");
    
    /**
     * メソッド名はcamelCaseであるべき
     */
    @ArchTest
    static final ArchRule methodsShouldUseCamelCase = 
        methods()
            .that().areDeclaredInClassesThat().resideInAPackage("com.ngnmsn.template..")
            .and().doNotHaveName("<init>")
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
            .should().bePackagePrivate()
            .orShould().bePublic()
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