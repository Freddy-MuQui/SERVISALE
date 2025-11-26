package cl.duoc.basico.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.basico.repository.AppDatabase
import cl.duoc.basico.ui.components.ItemCarritoCard
import cl.duoc.basico.viewmodel.CarritoViewModel
import cl.duoc.basico.utils.convertirAPesosChilenos

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    db: AppDatabase,
    usuarioActual: String,
    onBack: () -> Unit
) {
    val carritoViewModel = remember { CarritoViewModel(db.carritoDao(), usuarioActual) }
    val carrito by carritoViewModel.carrito.collectAsState()

    // Calcula subtotal en CLP sumando todos los productos y sus cantidades
    val subtotalClp = carrito.sumOf { item -> convertirAPesosChilenos(item.precio) * item.cantidad }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Carrito") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    if (carrito.isNotEmpty()) {
                        IconButton(onClick = { carritoViewModel.vaciarCarrito() }) {
                            Icon(Icons.Default.Delete, contentDescription = "Vaciar carrito")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        if (carrito.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Tu carrito está vacío", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Agrega productos para continuar",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                LazyColumn(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(carrito, key = { item -> item.id }) { item ->
                        ItemCarritoCard(
                            item = item,
                            onCantidadChange = { nuevaCantidad -> carritoViewModel.actualizarCantidad(item.id, nuevaCantidad) },
                            onEliminar = { carritoViewModel.eliminarItem(item.id) }
                        )
                    }
                }
                Divider(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    tonalElevation = 4.dp
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Subtotal:",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "$subtotalClp CLP",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Total:",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                "$subtotalClp CLP", // Mismo valor, puedes cambiar si tienes descuentos o envío
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { /* Implementar pago */ },
                            modifier = Modifier.fillMaxWidth().height(48.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Proceder al pago", style = MaterialTheme.typography.labelLarge)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = { /* Continuar comprando */ },
                            modifier = Modifier.fillMaxWidth().height(40.dp)
                        ) {
                            Text("Continuar comprando")
                        }
                    }
                }
            }
        }
    }
}
