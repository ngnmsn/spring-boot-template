package com.ngnmsn.template.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.ngnmsn.template.domain")
class DomainLayerArchitectureTest {
    
    /**
     * ドメインサービスの命名規則
     */
    @ArchTest
    static final ArchRule domainServicesShouldBeNamed = 
        classes()
            .that().resideInAPackage("..domain.service..")
            .and().haveSimpleNameNotEndingWith("Test")
            .should().haveSimpleNameEndingWith("DomainService")
            .because("ドメインサービスは'DomainService'で終わる名前にすべき");
    
    /**
     * リポジトリポートの命名規則
     */
    @ArchTest
    static final ArchRule repositoryPortsShouldBeNamed = 
        classes()
            .that().resideInAPackage("..domain.repository..")
            .should().haveSimpleNameEndingWith("RepositoryPort")
            .andShould().beInterfaces()
            .because("リポジトリポートは'RepositoryPort'で終わるインターフェースにすべき");
    
    /**
     * エンティティは適切なパッケージに配置される
     */
    @ArchTest
    static final ArchRule entitiesShouldBeInModelPackage = 
        classes()
            .that().areAnnotatedWith("javax.persistence.Entity")
            .or().areAnnotatedWith("jakarta.persistence.Entity")
            .should().resideInAPackage("..domain.model..")
            .allowEmptyShould(true)
            .because("エンティティはdomain.modelパッケージに配置すべき");
    
    /**
     * ドメインモデルは適切なパッケージに配置される
     */
    @ArchTest
    static final ArchRule domainModelsShouldBeInModelPackage = 
        classes()
            .that().resideInAPackage("..domain.model..")
            .and().haveSimpleNameNotEndingWith("Exception")
            .and().haveSimpleNameNotEndingWith("Test")
            .and().areNotEnums()
            .and().areNotInterfaces()
            .should().beTopLevelClasses()
            .because("ドメインモデルは適切に定義されるべき");
}