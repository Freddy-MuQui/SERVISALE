package cl.duoc.basico.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.basico.model.Producto

@Composable
fun TarjetaProducto(
    producto: Producto,
    usuarioActual: String,
    onAgregarCarrito: (Producto) -> Unit,
    onAgregarFavorito: (Producto) -> Unit,
    onNavigateToDetalle: () -> Unit
) {
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
                Text("Precio: $${producto.precio}")
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(all = 12.dp)
        ) {
            IconButton(onClick = { onAgregarCarrito(producto) }) {
                Icon(Icons.Default.AddShoppingCart, contentDescription = "Agregar al carrito")
            }
            IconButton(onClick = { onAgregarFavorito(producto) }) {
                Icon(Icons.Default.Favorite, contentDescription = "Agregar a favoritos")
            }
        }
    }
}
