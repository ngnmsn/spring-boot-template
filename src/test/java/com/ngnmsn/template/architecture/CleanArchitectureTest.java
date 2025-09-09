package com.ngnmsn.template.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.ngnmsn.template")
class CleanArchitectureTest {
    
    // レイヤー定義
    private static final String DOMAIN_LAYER = "..domain..";
    private static final String APPLICATION_LAYER = "..application..";
    private static final String INFRASTRUCTURE_LAYER = "..infrastructure..";
    private static final String PRESENTATION_LAYER = "..presentation..";
    
    /**
     * ドメイン層は他の層に依存してはならない
     */
    @ArchTest
    static final ArchRule domainLayerShouldNotDependOnOtherLayers = 
        noClasses()
            .that().resideInAPackage(DOMAIN_LAYER)
            .should().dependOnClassesThat()
            .resideInAnyPackage(APPLICATION_LAYER, INFRASTRUCTURE_LAYER, PRESENTATION_LAYER)
            .because("ドメイン層は最上位層であり、他の層に依存してはならない");
    
    /**
     * アプリケーション層はドメイン層のみに依存する
     */
    @ArchTest
    static final ArchRule applicationLayerShouldOnlyDependOnDomainLayer = 
        noClasses()
            .that().resideInAPackage(APPLICATION_LAYER)
            .should().dependOnClassesThat()
            .resideInAnyPackage(INFRASTRUCTURE_LAYER, PRESENTATION_LAYER)
            .because("アプリケーション層はドメイン層のみに依存すべき");
    
    /**
     * インフラストラクチャ層はプレゼンテーション層に依存してはならない
     */
    @ArchTest
    static final ArchRule infrastructureLayerShouldNotDependOnPresentationLayer = 
        noClasses()
            .that().resideInAPackage(INFRASTRUCTURE_LAYER)
            .should().dependOnClassesThat()
            .resideInAPackage(PRESENTATION_LAYER)
            .because("インフラストラクチャ層はプレゼンテーション層に依存してはならない");
    
    /**
     * ドメイン層はフレームワークに依存してはならない
     */
    @ArchTest
    static final ArchRule domainLayerShouldNotDependOnFrameworks = 
        noClasses()
            .that().resideInAPackage(DOMAIN_LAYER)
            .should().dependOnClassesThat()
            .resideInAnyPackage(
                "org.springframework..",
                "javax.persistence..",
                "jakarta.persistence..",
                "javax.validation..",
                "jakarta.validation..",
                "org.hibernate..",
                "com.fasterxml.jackson.."
            )
            .because("ドメイン層は外部フレームワークに依存してはならない");
            
    /**
     * ドメインエンティティクラスは適切なパッケージに配置される
     */
    @ArchTest
    static final ArchRule domainEntitiesShouldBeInModelPackage = 
        classes()
            .that().resideInAPackage("..domain.model..")
            .and().haveNameMatching(".*(?<!Test)$") // テストクラス以外
            .and().areNotEnums()
            .and().areNotInterfaces()
            .should().beTopLevelClasses()
            .because("ドメインエンティティは適切にカプセル化されるべき");
}