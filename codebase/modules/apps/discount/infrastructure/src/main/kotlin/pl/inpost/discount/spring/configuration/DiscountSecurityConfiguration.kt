package pl.inpost.discount.spring.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.inpost.security.User

@Configuration
class DiscountSecurityConfiguration {

    @Bean
    fun discountUser() = User("validUser", "validPassword", setOf("ADMIN"))
}