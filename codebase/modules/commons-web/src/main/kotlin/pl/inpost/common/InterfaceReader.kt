package pl.inpost.common

import org.json.JSONObject
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.yaml.snakeyaml.Yaml
import java.io.InputStream

object InterfaceReader {

    fun openapi(moduleName: String, openApiFileName: String) =
        moduleResource(moduleName, openApiFileName)
            ?.let { readYamlAsJson(it.inputStream).toString() }

    private fun moduleResource(moduleName: String, path: String): Resource? {
        val scanner = PathMatchingResourcePatternResolver()
        return scanner.getResources("classpath*:$path")
            .firstOrNull { it.url.path.contains(moduleName, ignoreCase = true) }
    }

    private fun readYamlAsJson(inputStream: InputStream): JSONObject {
        val yaml = inputStream
            .bufferedReader()
            .use { it.readText() }
        return yamlToJson(yaml)
    }

    private fun yamlToJson(yamlString: String): JSONObject {
        val yaml = Yaml()
        val map = yaml.loadAs(yamlString, mutableMapOf<String, Any>().javaClass)
        return JSONObject(map)
    }


}

