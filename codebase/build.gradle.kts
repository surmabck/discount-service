import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension


val kotlinVersion: String by project
val jvmVersion: String by project
val projectGroup: String by project
val projectVersion: String by project

group = projectGroup
version = projectVersion

plugins {
    kotlin("jvm") apply true
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management") apply false
    id("org.jetbrains.kotlin.plugin.spring") apply false
    id("io.gitlab.arturbosch.detekt") apply true
    id("maven-publish")
    id("java-library")
    id("base") apply true
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml") {
            version { strictly("2.14.2") }
        }
    }
}

allprojects {
    group = projectGroup
    version = projectVersion

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
        maven("https://repo.spring.io/milestone")
        maven("https://repo.spring.io/snapshot")
    }

    configurations {
        all {
            exclude(module = "android-json", group = "com.vaadin.external.google")
        }
    }
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "base")

    dependencies {
        
        implementation(kotlin("stdlib"))
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${project.property("jacksonVersion")}")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${project.property("jacksonVersion")}")
        implementation("io.github.microutils:kotlin-logging:${project.property("kotlinLoggingVersion")}") {
            exclude("org.jetbrains.kotlin")
        }

        implementation("org.springframework.boot:spring-boot-starter-actuator")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.junit.jupiter:junit-jupiter-params")
        testImplementation("io.kotest:kotest-assertions-core-jvm:${project.property("kotestAssertionsVersion")}")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = jvmVersion
    }

    the<DependencyManagementExtension>().apply {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES) {
                bomProperty("kotlin.version", kotlinVersion)
            }
        }
    }

    detekt {
        config.setFrom("${rootDir}/detekt/detekt-config.yml")
        baseline = file("${rootDir}/detekt/detekt-baseline.xml")
        source.setFrom("src/main/kotlin")

    }
    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        exclude("**/generated/**")
    }

}