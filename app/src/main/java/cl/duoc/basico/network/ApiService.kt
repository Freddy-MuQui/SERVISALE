package cl.duoc.basico.network

import retrofit2.http.GET

data class DummyResponse(val products: List<ApiProducto>)

interface ApiService {
    @GET("products")
    suspend fun getProductosSupermercado(): DummyResponse
}
