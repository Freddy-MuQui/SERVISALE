package cl.duoc.basico.ui.utils

import cl.duoc.basico.model.Producto

private val placeholdersPorCategoria = mapOf(
    "groceries" to "https://images.pexels.com/photos/776538/pexels-photo-776538.jpeg",
    "smartphones" to "https://images.pexels.com/photos/404280/pexels-photo-404280.jpeg",
    "skincare" to "https://images.pexels.com/photos/3738364/pexels-photo-3738364.jpeg",
    "furniture" to "https://images.pexels.com/photos/6964073/pexels-photo-6964073.jpeg"
)

fun urlImagenParaProducto(producto: Producto): String? {
    val directa = producto.imagenUrl.takeIf { it.isNotBlank() }
    if (directa != null) return directa
    return placeholdersPorCategoria[producto.categoria]
}
