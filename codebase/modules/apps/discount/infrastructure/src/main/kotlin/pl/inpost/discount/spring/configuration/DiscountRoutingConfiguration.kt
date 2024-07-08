package pl.inpost.discount.spring.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.RouterFunctions.route
import org.springframework.web.servlet.function.ServerResponse
import pl.inpost.discount.all.rest.CalculateTotalPriceWithAllDiscountsHandler
import pl.inpost.discount.generated.model.OrderWeb
import pl.inpost.discount.openapi.rest.InterfaceHandler
import pl.inpost.discount.usecase.PriceWithAllDiscountsCalculator
import pl.inpost.security.OpenPath

@Configuration
internal class DiscountRoutingConfiguration {

    @Bean
    fun discountHandler(calculator: PriceWithAllDiscountsCalculator) =
        CalculateTotalPriceWithAllDiscountsHandler(calculator = calculator)

    @Bean
    fun discountInterfaceHandler(): InterfaceHandler = InterfaceHandler()

    @Bean
    fun discountRouting(
        discountHandler: CalculateTotalPriceWithAllDiscountsHandler,
        discountInterfaceHandler: InterfaceHandler
    ): RouterFunction<ServerResponse> {
        return route()
            .POST("/discounts") { req -> discountHandler.calculate(req.body(OrderWeb::class.java)) }
            .GET("/discounts/_interface", discountInterfaceHandler::openapi)
            .build()
    }

    @Bean
    fun discountOpenApiOpenUrl(): OpenPath = OpenPath("/discounts/_interface")
}
