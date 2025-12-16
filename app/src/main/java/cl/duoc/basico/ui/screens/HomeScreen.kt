package cl.duoc.basico.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.basico.repository.AppDatabase
import cl.duoc.basico.ui.components.BottomNavigationBar
import cl.duoc.basico.ui.components.TarjetaProducto
import cl.duoc.basico.viewmodel.ProductoViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    db: AppDatabase,
    usuarioActual: String,
    onNavigateToDetalle: (Int) -> Unit,
    onNavigateToCarrito: () -> Unit,
    onNavigateToFavoritos: () -> Unit,
    onNavigateToPerfil: () -> Unit
) {
    val productoViewModel = remember { ProductoViewModel(db.productoDao()) }
    val productos by productoViewModel.productos.collectAsState()
    val categorias by productoViewModel.categorias.collectAsState()

    var selectedBottomNav by remember { mutableStateOf(0) }
    var mostrarFiltros by remember { mutableStateOf(false) }
    var textoBusqueda by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf<String?>(null) }
    var regionSeleccionada by remember { mutableStateOf<String?>(null) }
    var comunaSeleccionada by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    if (mostrarFiltros) {
        AlertDialog(
            onDismissRequest = { mostrarFiltros = false },
            title = { Text("Filtrar por ubicación") },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "Región: ${regionSeleccionada ?: "Todas"}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        "Comuna: ${comunaSeleccionada ?: "Todas"}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Filtros de ubicación disponibles próximamente",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    mostrarFiltros = false
                    scope.launch {
                        snackbarHostState.showSnackbar("Filtros aplicados")
                    }
                }) {
                    Text("Aplicar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarFiltros = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Sesión iniciada en ServiSale",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = { mostrarFiltros = true }) {
                        Icon(
                            Icons.Default.FilterList,
                            contentDescription = "Filtrar por ubicación"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = selectedBottomNav,
                onItemSelected = { index ->
                    selectedBottomNav = index
                    when (index) {
                        0 -> {}
                        1 -> onNavigateToCarrito()
                        2 -> onNavigateToFavoritos()
                        3 -> onNavigateToPerfil()
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                OutlinedTextField(
                    value = textoBusqueda,
                    onValueChange = {
                        textoBusqueda = it
                        if (it.isEmpty()) {
                            categoriaSeleccionada = null
                        }
                    },
                    label = { Text("Buscar producto") },
                    placeholder = { Text("Ej: leche, pan, arroz...") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    },
                    trailingIcon = {
                        if (textoBusqueda.isNotEmpty()) {
                            IconButton(onClick = {
                                textoBusqueda = ""
                                categoriaSeleccionada = null
                            }) {
                                Icon(Icons.Default.Close, contentDescription = "Limpiar")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )
            }

            // Filtros por categoría (chips horizontales)
            if (categorias.isNotEmpty()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Categorías",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        items(categorias) { categoria ->
                            FilterChip(
                                selected = categoriaSeleccionada == categoria,
                                onClick = {
                                    if (categoriaSeleccionada == categoria) {
                                        categoriaSeleccionada = null
                                    } else {
                                        categoriaSeleccionada = categoria
                                        productoViewModel.filtrarPorCategoria(categoria)
                                    }
                                },
                                label = { Text(categoria) },
                                leadingIcon = {
                                    if (categoriaSeleccionada == categoria) {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Contador de productos
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${productos.size} productos disponibles",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )

                if (categoriaSeleccionada != null) {
                    AssistChip(
                        onClick = {
                            categoriaSeleccionada = null
                        },
                        label = { Text("Limpiar filtros") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    )
                }
            }

            // Lista de productos
            if (productos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "No hay productos disponibles",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            if (categoriaSeleccionada != null)
                                "Intenta con otra categoría"
                            else
                                "Carga la base de datos",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = productos.filter {
                            if (textoBusqueda.isNotEmpty()) {
                                it.nombre.contains(textoBusqueda, ignoreCase = true) ||
                                        it.descripcion.contains(textoBusqueda, ignoreCase = true) ||
                                        it.supermercado.contains(textoBusqueda, ignoreCase = true)
                            } else {
                                true
                            }
                        }
                    ) { producto ->
                        TarjetaProducto(
                            producto = producto,
                            usuarioActual = usuarioActual,
                            db = db,
                            onNavigateToDetalle = {
                                onNavigateToDetalle(producto.idProducto)
                            }
                        )
                    }
                }
            }
        }
    }
}
