package pl.inpost.discount.spring.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import pl.inpost.security.SecurityConfiguration

@Configuration
@Import(
    SecurityConfiguration::class,
    DiscountSecurityConfiguration::class,
    DiscountPoliciesConfiguration::class,
    DiscountRoutingConfiguration::class,
    DiscountUseCasesConfiguration::class
)
class DiscountModuleConfiguration