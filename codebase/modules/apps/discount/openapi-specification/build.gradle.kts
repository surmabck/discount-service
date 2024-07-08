import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

buildscript {
    val openApiGeneratorGradlePluginVersion: String by project
    repositories {
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
        maven("https://repo.spring.io/milestone")
        maven("https://repo.spring.io/snapshot")
    }
    dependencies {
        classpath("org.openapitools:openapi-generator-gradle-plugin:${openApiGeneratorGradlePluginVersion}")
    }
}

apply(plugin = "org.openapi.generator")

val globalPropertiesMap = mapOf(
    "models" to "",
    "modelDocs" to "false",
    "useJakartaEe" to "true"
)
val typeMappingsMap = mapOf(
    "DateTime" to "OffsetDateTime",
    "double" to "BigDecimal",
    "Day" to "DayOfWeek"
)
val importMappingsMap = mapOf(
    "OffsetDateTime" to "java.time.OffsetDateTime",
    "BigDecimal" to "java.math.BigDecimal",
    "DayOfWeek" to "java.time.DayOfWeek"
)
val configOptionsMap = mapOf(
    "enumPropertyNaming" to "UPPERCASE",
    "generateApis" to "true",
    "generateApiTests" to "false",
    "generateModels" to "true",
    "generateModelTests" to "false",
    "useSpringBoot3" to "true"
)

tasks.withType<GenerateTask> {
    generatorName.set("kotlin-spring")
    inputSpec.set("$projectDir/src/main/resources/discount-openapi.yaml")
    outputDir.set("$projectDir/")
    generateAliasAsModel.set(true)
    globalProperties.set(globalPropertiesMap)
    modelNameSuffix.set("Web")
    typeMappings.set(typeMappingsMap)
    importMappings.set(importMappingsMap)
    additionalProperties.set(mapOf("enumPropertyNaming" to "UPPERCASE"))
    configOptions.set(configOptionsMap)
    modelPackage.set("pl.inpost.discount.generated.model")
}


dependencies {
    implementation("jakarta.validation:jakarta.validation-api:${project.property("validationApiVersion")}")
    implementation("io.swagger.core.v3:swagger-core:${project.property("swaggerCoreVersion")}")
    implementation("io.swagger.core.v3:swagger-annotations:${project.property("swaggerApiVersion")}")
}
