package pl.inpost.product.openapi.rest

import org.springframework.http.MediaType
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.notFound
import org.springframework.web.servlet.function.ServerResponse.ok
import pl.inpost.common.InterfaceReader

class InterfaceHandler {
    companion object {
        private const val MODULE_CONTAINING_OPENAPI = "openapi-specification"
        private const val OPENAPI_FILE_NAME = "product-openapi.yaml"
    }

    fun openapi(request: ServerRequest): ServerResponse =
        InterfaceReader.openapi(MODULE_CONTAINING_OPENAPI, OPENAPI_FILE_NAME)
            ?.let { ok().contentType(MediaType.APPLICATION_JSON).body(it) }
            ?: notFound().build()
}