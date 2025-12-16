package cl.duoc.basico.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.basico.repository.AppDatabase
import cl.duoc.basico.viewmodel.CarritoViewModel
import cl.duoc.basico.viewmodel.FavoritoViewModel
import cl.duoc.basico.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProductoScreen(
    db: AppDatabase,
    productoId: Int,
    usuarioActual: String,
    onBack: () -> Unit,
    onVerMapa: () -> Unit
) {
    val productoViewModel = remember { ProductoViewModel(db.productoDao()) }
    val carritoViewModel = remember { CarritoViewModel(db.carritoDao(), usuarioActual) }
    val favoritoViewModel = remember { FavoritoViewModel(db.favoritoDao(), usuarioActual) }

    LaunchedEffect(productoId) {
        productoViewModel.obtenerProductoPorId(productoId)
    }

    val producto by productoViewModel.productoSeleccionado.collectAsState()
    val favoritos by favoritoViewModel.favoritos.collectAsState()
    var cantidadSeleccionada by remember { mutableStateOf(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Producto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->
        if (producto != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    // Encabezado
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                producto!!.nombre,
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Categoría: ${producto!!.categoria}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        IconButton(
                            onClick = {
                                favoritoViewModel.toggleFavorito(producto!!.nombre)
                            }
                        ) {
                            Icon(
                                if (favoritos.any { it.producto == producto!!.nombre })
                                    Icons.Default.Favorite
                                else
                                    Icons.Default.FavoriteBorder,
                                contentDescription = "Favorito",
                                tint = if (favoritos.any { it.producto == producto!!.nombre })
                                    MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Información del producto
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Precio:", style = MaterialTheme.typography.titleMedium)
                                Text(
                                    "$${producto!!.precio}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Supermercado:", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    producto!!.supermercado,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Disponible:", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    "${producto!!.cantidadDisponible} unidades",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                producto!!.descripcion,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Cantidad
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Cantidad:", style = MaterialTheme.typography.titleMedium)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 12.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = {
                                    if (cantidadSeleccionada > 1) cantidadSeleccionada--
                                }) {
                                    Icon(Icons.Default.Remove, "Reducir")
                                }
                                Text(
                                    "$cantidadSeleccionada",
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                IconButton(onClick = {
                                    if (cantidadSeleccionada < producto!!.cantidadDisponible)
                                        cantidadSeleccionada++
                                }) {
                                    Icon(Icons.Default.Add, "Aumentar")
                                }
                            }
                        }
                    }
                }

                // Botones de acción
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { onVerMapa() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.outlinedButtonColors()
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ver en mapa")
                    }

                    Button(
                        onClick = {
                            carritoViewModel.agregarProducto(
                                producto!!.idProducto,
                                producto!!.nombre,
                                producto!!.precio
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Agregar al carrito")
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
