package pl.inpost.discount.all.rest

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import pl.inpost.discount.all.rest.TestConfig.Companion.EXISTING_ITEM_ID
import pl.inpost.discount.all.rest.TestConfig.Companion.NON_EXISTING_ITEM_ID
import pl.inpost.discount.port.ProductPriceProvider
import pl.inpost.discount.spring.configuration.DiscountModuleConfiguration


@WebMvcTest
@ContextConfiguration(
    classes = [TestConfig::class, DiscountModuleConfiguration::class]
)
@AutoConfigureMockMvc
class CalculateTotalPriceWithAllDiscountsHandlerTest(@Autowired val mockMvc: MockMvc) {
    private val validUser = httpBasic("validUser", "validPassword")
    private val inValidUser = httpBasic("otherUser", "validPassword")

    @Test
    fun `should return calculated total price if product exists`() {
        mockMvc
            .perform(
                post(url())
                    .with(validUser)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(order(EXISTING_ITEM_ID, 1))
            )
            .andExpect(status().isOk)
            .andExpect(content().json("""{ "totalPrice": 10}"""))
    }

    @Test
    fun `should return 404 if product does not exists`() {
        mockMvc
            .perform(
                post(url())
                    .with(validUser)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(order(NON_EXISTING_ITEM_ID, 1))
            )
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should return 401 if no Auth header`() {
        mockMvc
            .perform(
                post(url())
                    .content(order(EXISTING_ITEM_ID, 1))
            )
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `should return 401 if invalid User`() {
        mockMvc.perform(
            post(url())
                .with(inValidUser)
                .content(order(EXISTING_ITEM_ID, 1))
        ).andExpect(status().isUnauthorized)
    }

    private fun url(): String = "/discounts"
    private fun order(itemId: String, quantity: Int) = """
        {
            "itemId": "$itemId",
            "quantity": $quantity
        }
    """.trimIndent()
}

@TestConfiguration
internal class TestConfig {
    companion object {
        const val EXISTING_ITEM_ID = "1"
        const val NON_EXISTING_ITEM_ID = "2"

    }

    @Bean
    fun productDetailsProvider(): ProductPriceProvider = ProductPriceProvider { productId ->
        when (productId) {
            EXISTING_ITEM_ID -> 10.toBigDecimal()
            else -> null
        }
    }
}