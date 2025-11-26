package cl.duoc.basico.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.basico.model.Producto
import cl.duoc.basico.model.Favorito

@Composable
fun TarjetaProducto(
    producto: Producto,
    usuarioActual: String,
    favoritos: List<Favorito>, // tipo Favorito
    onToggleFavorito: (Producto) -> Unit,
    onAgregarCarrito: (Producto) -> Unit,
    onNavigateToDetalle: () -> Unit,
    showSnackbar: (String) -> Unit
) {
    fun convertirAPesosChilenos(precioUsd: Float): Int = (precioUsd * 930f).toInt()
    val precioClp = convertirAPesosChilenos(producto.precio)
    val precioFormateado = "%,d".format(precioClp)

    // Verifica si el producto está en favoritos (por nombre, o cambia a idProducto si tu modelo lo tiene)
    val esFavorito = favoritos.any { it.producto == producto.nombre }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 48.dp)
            .clickable { onNavigateToDetalle() }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(all = 16.dp)) {
                Text(text = producto.nombre, style = MaterialTheme.typography.titleMedium)
                Text("Supermercado: ${producto.supermercado}")
                Text("Categoría: ${producto.categoria}")
                Text("Precio: $precioFormateado CLP")
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(all = 12.dp)
        ) {
            IconButton(onClick = {
                onAgregarCarrito(producto)
                showSnackbar("Producto agregado al carrito")
            }) {
                Icon(Icons.Default.AddShoppingCart, contentDescription = "Agregar al carrito")
            }
            IconButton(onClick = { onToggleFavorito(producto) }) {
                Icon(
                    if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (esFavorito) "Quitar de favoritos" else "Agregar a favoritos",
                    tint = if (esFavorito) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}
