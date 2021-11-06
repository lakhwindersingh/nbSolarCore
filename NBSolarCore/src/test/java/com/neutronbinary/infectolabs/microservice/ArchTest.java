package com.neutronbinary.infectolabs.microservice;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.neutronbinary.infectolabs.microservice");

        noClasses()
            .that()
            .resideInAnyPackage("com.neutronbinary.infectolabs.microservice.service..")
            .or()
            .resideInAnyPackage("com.neutronbinary.infectolabs.microservice.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.neutronbinary.infectolabs.microservice.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
