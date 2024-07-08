package pl.inpost.discount.all.rest

import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.notFound
import org.springframework.web.servlet.function.ServerResponse.ok
import pl.inpost.discount.generated.model.OrderWeb
import pl.inpost.discount.generated.model.PriceWeb
import pl.inpost.discount.usecase.PriceWithAllDiscountsCalculator

internal class CalculateTotalPriceWithAllDiscountsHandler(
    private val calculator: PriceWithAllDiscountsCalculator
) {

    fun calculate(order: OrderWeb): ServerResponse =
        calculator.calculate(itemId = order.itemId, quantity = order.quantity)
            ?.let { ok().body(PriceWeb(totalPrice = it.toBigDecimal())) }
            ?: notFound().build()
}