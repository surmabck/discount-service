package pl.inpost.product.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.inpost.security.User

@Configuration
internal class ProductSecurityConfiguration {

    @Bean
    fun productUser() = User("validUser", "validPassword", setOf("ADMIN"))
}