package cl.duoc.basico.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.basico.model.Producto
import cl.duoc.basico.repository.AppDatabase
import cl.duoc.basico.viewmodel.CarritoViewModel
import cl.duoc.basico.viewmodel.FavoritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarjetaProducto(
    producto: Producto,
    usuarioActual: String,
    db: AppDatabase,
    onNavigateToDetalle: () -> Unit
) {
    val carritoViewModel = remember { CarritoViewModel(db.carritoDao(), usuarioActual) }
    val favoritoViewModel = remember { FavoritoViewModel(db.favoritoDao(), usuarioActual) }

    val favoritos by favoritoViewModel.favoritos.collectAsState()
    val esFavorito = favoritos.any { it.producto == producto.nombre }

    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onNavigateToDetalle
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        producto.nombre,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Supermercado: ${producto.supermercado}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Categoría: ${producto.categoria}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                IconButton(
                    onClick = { favoritoViewModel.toggleFavorito(producto.nombre) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorito",
                        tint = if (esFavorito) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "$${producto.precio}",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Button(
                    onClick = {
                        carritoViewModel.agregarProducto(
                            producto.idProducto,
                            producto.nombre,
                            producto.precio
                        )
                    },
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(
                        Icons.Default.AddShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
