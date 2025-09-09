package com.ngnmsn.template.architecture;

import com.tngtech.archunit.lang.ArchRule;
import org.slf4j.Logger;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.core.domain.JavaCall.Predicates.target;
import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.assignableTo;
import static com.tngtech.archunit.core.domain.JavaMember.Predicates.name;
import static com.tngtech.archunit.core.domain.properties.HasOwner.Predicates.owner;
import static com.tngtech.archunit.core.domain.properties.HasParameterTypes.Predicates.rawParameterTypes;

public class CustomArchitectureRules {
    
    /**
     * プロジェクト固有のアーキテクチャルール
     */
    public static final ArchRule SAMPLE_SPECIFIC_RULES = 
        classes()
            .that().haveSimpleNameStartingWith("Sample")
            .should().resideInAnyPackage(
                "..domain.model.sample..", 
                "..application.service.sample..", 
                "..infrastructure.repository.jooq.sample..",
                "..presentation.web.sample..",
                "..presentation.api.sample.."
            )
            .because("Sample関連のクラスは適切なパッケージに配置すべき");
    
    /**
     * セキュリティに関するルール
     */
    public static final ArchRule NO_PASSWORD_IN_LOGS = 
        noClasses()
            .should().callMethodWhere(
                target(name("info"))
                .and(owner(assignableTo(Logger.class)))
                .and(rawParameterTypes(String.class))
            )
            .andShould().callMethodWhere(
                target(name("debug"))
                .and(owner(assignableTo(Logger.class)))
                .and(rawParameterTypes(String.class))
            )
            .because("パスワード等の機密情報をログに出力してはならない");
    
    /**
     * パフォーマンスに関するルール
     */
    public static final ArchRule NO_REFLECTION_IN_DOMAIN = 
        noClasses()
            .that().resideInAPackage("..domain..")
            .should().accessClassesThat()
            .resideInAnyPackage("java.lang.reflect..", "org.springframework.util.ReflectionUtils")
            .because("ドメイン層でリフレクションを使用してはならない");
}