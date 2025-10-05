package com.ngnmsn.template.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = "com.ngnmsn.template")
class CyclicDependencyTest {
    
    /**
     * パッケージレベルでの循環依存をチェック
     */
    @ArchTest
    static final ArchRule packagesShouldNotHaveCyclicDependencies = 
        slices()
            .matching("com.ngnmsn.template.(*)..")
            .should().beFreeOfCycles()
            .because("パッケージ間で循環依存があってはならない");
    
    /**
     * レイヤー間の循環依存をチェック
     */
    @ArchTest
    static final ArchRule layersShouldNotHaveCyclicDependencies = 
        slices()
            .matching("com.ngnmsn.template.(*)")
            .should().beFreeOfCycles()
            .because("レイヤー間で循環依存があってはならない");
    
    /**
     * ドメインモデル内の循環依存をチェック
     */
    @ArchTest
    static final ArchRule domainModelShouldNotHaveCyclicDependencies = 
        slices()
            .matching("com.ngnmsn.template.domain.model.(*)..")
            .should().beFreeOfCycles()
            .because("ドメインモデル間で循環依存があってはならない");
}