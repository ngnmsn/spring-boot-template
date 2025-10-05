package com.ngnmsn.template.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.core.domain.JavaClass;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.ngnmsn.template.infrastructure")
class InfrastructureLayerArchitectureTest {
    
    /**
     * リポジトリ実装の命名規則
     */
    @ArchTest
    static final ArchRule repositoryAdaptersShouldBeNamed = 
        classes()
            .that().resideInAPackage("..infrastructure.repository..")
            .and().areNotInterfaces()
            .and().haveSimpleNameNotEndingWith("Test")
            .and().haveSimpleNameNotEndingWith("Tests")
            .should().haveSimpleNameEndingWith("RepositoryAdapter")
            .orShould().haveSimpleNameEndingWith("Repository")
            .because("リポジトリ実装は'RepositoryAdapter'または'Repository'で終わる名前にすべき");
    
    /**
     * リポジトリ実装は@Repositoryアノテーションを持つ
     */
    @ArchTest
    static final ArchRule repositoryAdaptersShouldBeAnnotated = 
        classes()
            .that().resideInAPackage("..infrastructure.repository..")
            .and().areNotInterfaces()
            .and().haveSimpleNameEndingWith("RepositoryAdapter")
            .should().beAnnotatedWith("org.springframework.stereotype.Repository")
            .because("リポジトリ実装は@Repositoryアノテーションを持つべき");
    
    /**
     * 設定クラスの命名規則
     */
    @ArchTest
    static final ArchRule configurationClassesShouldBeNamed = 
        classes()
            .that().resideInAPackage("..infrastructure.config..")
            .and().areAnnotatedWith("org.springframework.context.annotation.Configuration")
            .should().haveSimpleNameEndingWith("Config")
            .orShould().haveSimpleNameEndingWith("Configuration")
            .because("設定クラスは'Config'または'Configuration'で終わる名前にすべき");
    
    /**
     * インフラ層のクラスはドメインポートを実装すべき
     */
    @ArchTest
    static final ArchRule repositoryAdaptersShouldImplementDomainPorts = 
        classes()
            .that().resideInAPackage("..infrastructure.repository..")
            .and().haveSimpleNameEndingWith("RepositoryAdapter")
            .should().implement(JavaClass.Predicates.resideInAPackage("..domain.repository.."))
            .because("リポジトリアダプターはドメインのリポジトリポートを実装すべき");
}