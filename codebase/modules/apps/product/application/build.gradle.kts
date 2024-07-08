dependencies{
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(project(":modules:apps:product:product-openapi-specification"))
    implementation(project(":modules:commons-web"))
    implementation(project(":modules:security"))
    implementation("org.json:json:${project.property("jsonVersion")}")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}