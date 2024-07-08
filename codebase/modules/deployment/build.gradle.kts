apply(plugin = "org.springframework.boot")

dependencies {
    implementation(project(":modules:apps:discount:discount-application"))
    implementation(project(":modules:apps:discount:infrastructure"))
    implementation(project(":modules:apps:product:product-application"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}
