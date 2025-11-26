package cl.duoc.basico.ui.components

import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.basico.utils.convertirAPesosChilenos
import cl.duoc.basico.model.ItemCarrito

@Composable
fun ItemCarritoCard(
    item: ItemCarrito,
    onCantidadChange: (Int) -> Unit,
    onEliminar: () -> Unit
) {
    val precioClp = convertirAPesosChilenos(item.precio)
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Producto: ${item.nombreProducto}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Precio: $precioClp CLP")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Cantidad:")
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Button(
                    onClick = { onCantidadChange(item.cantidad - 1) },
                    enabled = item.cantidad > 1,
                    modifier = Modifier.size(36.dp)
                ) {
                    Text("\u2212") // Unicode U+2212 ("−") símbolo menos
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text("${item.cantidad}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { onCantidadChange(item.cantidad + 1) },
                    modifier = Modifier.size(36.dp)
                ) {
                    Text("+") // símbolo más
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
