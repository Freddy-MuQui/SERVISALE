package cl.duoc.basico.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.widget.Toast
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
    fun convertirAPesosChilenos(precioUsd: Float): Int {
        val tasaCambio = 930f
        return (precioUsd * tasaCambio).toInt()
    }

    val productoViewModel = remember { ProductoViewModel() }
    val carritoViewModel = remember { CarritoViewModel(db.carritoDao(), usuarioActual) }
    val favoritoViewModel = remember { FavoritoViewModel(db.favoritoDao(), usuarioActual) }

    LaunchedEffect(productoId) {
        productoViewModel.obtenerProductoPorId(productoId)
    }

    val producto by productoViewModel.productoSeleccionado.collectAsState()
    val favoritos by favoritoViewModel.favoritos.collectAsState()
    var cantidadSeleccionada by remember { mutableStateOf(1) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Producto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        val p = producto
        if (p != null) {

            val precioClp = convertirAPesosChilenos(p.precio)
            val precioFormateado = "%,d".format(precioClp)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = p.nombre,
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Categoría: ${p.categoria}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        IconButton(
                            onClick = { favoritoViewModel.toggleFavorito(p.nombre) }
                        ) {
                            val esFav = favoritos.any { it.producto == p.nombre }
                            Icon(
                                imageVector = if (esFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorito",
                                tint = if (esFav)
                                    MaterialTheme.colorScheme.error
                                else
                                    MaterialTheme.colorScheme.outline
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

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
                                    text = "$precioFormateado CLP",
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
                                    text = p.supermercado,
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
                                    text = "${p.cantidadDisponible} unidades",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = p.descripcion,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

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
                                IconButton(
                                    onClick = {
                                        if (cantidadSeleccionada > 1) cantidadSeleccionada--
                                    }
                                ) {
                                    Icon(Icons.Default.Remove, contentDescription = "Reducir")
                                }
                                Text(
                                    text = "$cantidadSeleccionada",
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                IconButton(
                                    onClick = {
                                        if (cantidadSeleccionada < p.cantidadDisponible)
                                            cantidadSeleccionada++
                                    }
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Aumentar")
                                }
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onVerMapa,
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
                                productoId = p.idProducto,
                                nombre = p.nombre,
                                precio = precioClp.toFloat(),   // mantienes CLP
                                categoria = p.categoria,
                                cantidad = cantidadSeleccionada,
                                imagenUrl = p.imagenUrl        // se usa la url real
                            )
                            Toast.makeText(
                                context,
                                "Producto agregado al carrito",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Agregar al carrito")
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
