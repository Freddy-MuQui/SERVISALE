package cl.duoc.basico.network

data class ApiProducto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Float,
    val category: String,
    val thumbnail: String
)
