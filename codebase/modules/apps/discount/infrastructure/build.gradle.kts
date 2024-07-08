dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.json:json:${project.property("jsonVersion")}")
    implementation(project(":modules:apps:discount:discount-application"))
    implementation(project(":modules:apps:discount:domain"))
    implementation(project(":modules:apps:discount:discount-openapi-specification"))
    implementation(project(":modules:commons-web"))
    implementation(project(":modules:security"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}