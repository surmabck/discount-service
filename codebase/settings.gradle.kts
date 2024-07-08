pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val detektVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        id("org.springframework.boot") version springBootVersion apply false
        id("io.spring.dependency-management") version springDependencyManagementVersion apply false
        id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion apply false
        id("io.gitlab.arturbosch.detekt") version detektVersion apply false
    }

    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.springframework.boot") {
                useModule("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
            }
        }
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "discount-service"
rootProject.buildFileName = "build.gradle.kts"
include("modules:deployment")
include("modules:apps")
include("modules:commons-web")
include("modules:security")

include("modules:apps:discount")
include("modules:apps:discount:openapi-specification")
findProject(":modules:apps:discount:openapi-specification")?.name = "discount-openapi-specification"
include("modules:apps:discount:application")
findProject(":modules:apps:discount:application")?.name = "discount-application"
include("modules:apps:discount:domain")
include("modules:apps:discount:infrastructure")

include("modules:apps:product")
include("modules:apps:product:openapi-specification")
findProject(":modules:apps:product:openapi-specification")?.name = "product-openapi-specification"
include("modules:apps:product:application")
findProject(":modules:apps:product:application")?.name = "product-application"
