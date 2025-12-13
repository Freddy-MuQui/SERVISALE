package cl.duoc.basico.ui.components

import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cl.duoc.basico.utils.convertirAPesosChilenos
import cl.duoc.basico.model.ItemCarrito

@Composable
fun ItemCarritoCard(
    item: ItemCarrito,
    onCantidadChange: (Int) -> Unit,
    onEliminar: () -> Unit
) {
    val precioClp = convertirAPesosChilenos(item.precio)
    val context = LocalContext.current
    val urlImagen = item.imagenUrl?.takeIf { it.isNotBlank() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            if (urlImagen != null) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(urlImagen)
                        .crossfade(true)
                        .build(),
                    contentDescription = item.nombreProducto,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(
                "Producto: ${item.nombreProducto}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("Precio: $precioClp CLP")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Cantidad:")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                OutlinedButton(
                    onClick = { if (item.cantidad > 1) onCantidadChange(item.cantidad - 1) },
                    enabled = item.cantidad > 1
                ) {
                    Text(text = "-")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("${item.cantidad}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(
                    onClick = { onCantidadChange(item.cantidad + 1) }
                ) {
                    Text(text = "+")
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("Categoría: ${item.categoria}")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onEliminar) {
                Text("Eliminar del carrito")
            }
        }
    }
}
