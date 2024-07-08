package pl.inpost.product.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import pl.inpost.security.SecurityConfiguration

@Configuration
@Import(
    SecurityConfiguration::class,
    ProductSecurityConfiguration::class,
    ProductRoutingConfiguration::class,
    ProductConfiguration::class
)
class ProductModuleConfiguration