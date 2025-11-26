package cl.duoc.basico.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.basico.model.ItemCarrito

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCarritoCard(
    item: ItemCarrito,
    onCantidadChange: (Int) -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    item.nombreProducto,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "$${item.precio}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { onCantidadChange(item.cantidad - 1) }
                ) {
                    Icon(Icons.Default.Remove, "Reducir")
                }

                Text(
                    "${item.cantidad}",
                    style = MaterialTheme.typography.titleMedium
                )

                IconButton(
                    onClick = { onCantidadChange(item.cantidad + 1) }
                ) {
                    Icon(Icons.Default.Add, "Aumentar")
                }

                IconButton(
                    onClick = onEliminar
                ) {
                    Icon(
                        Icons.Default.Delete,
                        "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
