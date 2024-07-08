package pl.inpost.product

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import pl.inpost.product.configuration.ProductModuleConfiguration


@WebMvcTest
@ContextConfiguration(
    classes = [TestConfig::class, ProductModuleConfiguration::class]
)
@AutoConfigureMockMvc
class GetProductRestControllerTest(@Autowired val mockMvc: MockMvc) {
    private val existingProductId = "2f36c55e-4bcb-4d0a-9448-183047879e70"
    private val nonExistingUrl = "15603f96-1ef0-465c-9a7f-bb9e106e2b64"
    private val validUser = httpBasic("validUser", "validPassword")
    private val inValidUser = httpBasic("otherUser", "validPassword")

    @Test
    fun `should return product if product exists`() {
        mockMvc
            .perform(get(getProductUrl(existingProductId)).with(validUser))
            .andExpect(status().isOk)
            .andExpect(
                content().json(
                    """
                    {
                      "productId": "2f36c55e-4bcb-4d0a-9448-183047879e70",
                      "name": "Product",
                      "price": {
                        "value": 10
                      }
                    }
                """.trimIndent()
                )
            )
    }

    @Test
    fun `should return 404 if product does not exists`() {
        mockMvc
            .perform(get(getProductUrl(nonExistingUrl)).with(validUser))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should return 401 if no Auth header`() {
        mockMvc
            .perform(get(getProductUrl(existingProductId)))
            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `should return 401 if no invalid User`() {
        mockMvc.perform(get(getProductUrl(existingProductId)).with(inValidUser))
            .andExpect(status().isUnauthorized)
    }

    private fun getProductUrl(productId: String): String = "/products/$productId"
}

@TestConfiguration
class TestConfig {
    @Bean
    fun productDetailsProvider(): ProductDetailsProvider = InMemoryProductDetailsProvider(
        listOf(
            Product(ProductId("2f36c55e-4bcb-4d0a-9448-183047879e70"), "Product", ProductPrice(10.toBigDecimal()))
        )
    )
}