package cl.duoc.basico.network

import retrofit2.http.GET
import retrofit2.http.Path

data class DummyResponse(val products: List<ApiProducto>)

interface ApiService {

    @GET("products")
    suspend fun getProductosSupermercado(): DummyResponse

    @GET("products/category/{category}")
    suspend fun getProductosPorCategoria(
        @Path("category") category: String
    ): DummyResponse
}
