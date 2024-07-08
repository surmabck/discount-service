package pl.inpost.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.core.userdetails.User as SpringUser

data class OpenPath(val url: String)
data class User(val username: String, val password: String, val roles: Set<String>)

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
class SecurityConfiguration {

    @Suppress("SpreadOperator")
    @Bean
    fun securityFilterChain(security: HttpSecurity, openPaths: List<OpenPath>): SecurityFilterChain {
        return security.csrf { csrf: CsrfConfigurer<HttpSecurity> -> csrf.disable() }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers(*openPaths.map(OpenPath::url).toTypedArray())
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .httpBasic(Customizer.withDefaults())
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Suppress("SpreadOperator")
    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder, users: List<User>): UserDetailsService =
        users.map {
            SpringUser
                .builder()
                .username(it.username)
                .password(passwordEncoder.encode(it.password))
                .roles(*it.roles.toTypedArray())
                .build()
        }.toSet().let { InMemoryUserDetailsManager(it) }

}