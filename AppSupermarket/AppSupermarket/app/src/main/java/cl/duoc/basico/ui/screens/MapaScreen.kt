package cl.duoc.basico.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.basico.repository.AppDatabase
import cl.duoc.basico.viewmodel.ProductoViewModel
import cl.duoc.basico.viewmodel.SupermercadoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapaScreen(
    db: AppDatabase,
    productoId: Int,
    onBack: () -> Unit
) {
    val productoViewModel = remember { ProductoViewModel(db.productoDao()) }
    val supermercadoViewModel = remember { SupermercadoViewModel(db.supermercadoDao()) }

    val supermercados by supermercadoViewModel.supermercados.collectAsState()

    LaunchedEffect(productoId) {
        productoViewModel.obtenerProductoPorId(productoId)
    }

    val producto by productoViewModel.productoSeleccionado.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ubicaciones de ${producto?.nombre ?: "Producto"}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Simulación de mapa (puede integrarse con Google Maps real)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Mapa de supermercados",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Santiago, Chile",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "📍 ${supermercados.size} supermercados disponibles",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Lista de supermercados
            Text(
                "Supermercados cercanos:",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            supermercados.forEach { supermercado ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            supermercado.nombre,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "📍 ${supermercado.direccion}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "📞 ${supermercado.comuna} - ${supermercado.region}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
