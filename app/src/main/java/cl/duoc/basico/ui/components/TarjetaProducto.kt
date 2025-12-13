package cl.duoc.basico.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cl.duoc.basico.model.Producto
import cl.duoc.basico.model.Favorito
import cl.duoc.basico.ui.utils.urlImagenParaProducto
import java.text.DecimalFormat

@Composable
fun TarjetaProducto(
    producto: Producto,
    usuarioActual: String,
    favoritos: List<Favorito>,
    onToggleFavorito: (Producto) -> Unit,
    onAgregarCarrito: (Producto, Int) -> Unit,
    onNavigateToDetalle: () -> Unit,
    showSnackbar: (String) -> Unit
) {
    var cantidad by remember { mutableStateOf(1) }

    fun convertirAPesosChilenos(precioUsd: Float): Int {
        val tasaCambio = 930f
        return (precioUsd * tasaCambio).toInt()
    }

    val precioClp = convertirAPesosChilenos(producto.precio)
    val d = DecimalFormat("#,###")
    val precioFormateado = d.format(precioClp)

    val esFavorito = favoritos.any { it.producto == producto.nombre }

    val context = LocalContext.current
    val urlImagen = urlImagenParaProducto(producto)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 48.dp)
            .clickable { onNavigateToDetalle() }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(all = 16.dp)
            ) {

                if (urlImagen != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(urlImagen)
                            .crossfade(true)
                            .build(),
                        contentDescription = producto.nombre,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = "Supermercado: ${producto.supermercado}")
                Text(text = "Categoría: ${producto.categoria}")
                Text(text = "Precio: $precioFormateado CLP")

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { if (cantidad > 1) cantidad-- }
                    ) {
                        Text(text = "-")
                    }

                    Text(text = cantidad.toString())

                    OutlinedButton(
                        onClick = { cantidad++ }
                    ) {
                        Text(text = "+")
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = {
                            onAgregarCarrito(producto, cantidad)
                            showSnackbar("Se agregaron $cantidad ${producto.nombre} al carrito")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddShoppingCart,
                            contentDescription = "Agregar al carrito"
                        )
                    }

                    IconButton(
                        onClick = { onToggleFavorito(producto) }
                    ) {
                        Icon(
                            imageVector = if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (esFavorito) "Quitar de favoritos" else "Agregar a favoritos",
                            tint = if (esFavorito) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }
    }
}
