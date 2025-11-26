package cl.duoc.basico.ui.screens

import cl.duoc.basico.ui.components.TarjetaProducto
import cl.duoc.basico.ui.components.BottomNavigationBar
import cl.duoc.basico.ui.components.FiltrosDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import cl.duoc.basico.viewmodel.ProductoViewModel
import cl.duoc.basico.viewmodel.CarritoViewModel
import cl.duoc.basico.viewmodel.FavoritoViewModel
import cl.duoc.basico.viewmodel.FavoritoViewModelFactory
import cl.duoc.basico.repository.AppDatabase
import androidx.lifecycle.viewmodel.compose.viewModel

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
    val productoViewModel: ProductoViewModel = viewModel()
    val carritoViewModel = remember { CarritoViewModel(db.carritoDao(), usuarioActual) }
    val favoritoViewModel: FavoritoViewModel = viewModel(
        factory = FavoritoViewModelFactory(db.favoritoDao(), usuarioActual)
    )
    val productos by productoViewModel.productos.collectAsState()
    val favoritos by favoritoViewModel.favoritos.collectAsState()
    val isLoading by productoViewModel.isLoading.collectAsState()
    val errorMessage by productoViewModel.errorMessage.collectAsState()

    val context = LocalContext.current

    var bottomIndex by remember { mutableStateOf(0) }
    var showFiltros by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val comunas = listOf("Santiago", "Providencia", "Maipú", "Ñuñoa")
    val regions = listOf("RM", "Valparaíso", "Biobío")
    var regionSeleccionada by remember { mutableStateOf(regions[0]) }
    var comunaSeleccionada by remember { mutableStateOf(comunas[0]) }

    val productosFiltrados = productos.filter { producto ->
        searchQuery.isBlank() ||
                producto.nombre.contains(searchQuery, ignoreCase = true) ||
                producto.descripcion.contains(searchQuery, ignoreCase = true) ||
                producto.categoria.contains(searchQuery, ignoreCase = true)
    }

    fun showSnackbar(mensaje: String) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("ServiSale") },
                    actions = {
                        IconButton(onClick = { showFiltros = true }) {
                            Icon(Icons.Default.FilterList, contentDescription = "Filtros")
                        }
                    }
                )
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Buscar productos...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = bottomIndex,
                onItemSelected = { index ->
                    bottomIndex = index
                    when (index) {
                        0 -> {} // Ya estás en Home
                        1 -> onNavigateToCarrito()
                        2 -> onNavigateToFavoritos()
                        3 -> onNavigateToPerfil()
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (showFiltros) {
                FiltrosDialog(
                    comunas = comunas,
                    regiones = regions,
                    regionSeleccionada = regionSeleccionada,
                    comunaSeleccionada = comunaSeleccionada,
                    onComunaSelected = { comunaSeleccionada = it },
                    onRegionSelected = { regionSeleccionada = it },
                    onAplicar = { showFiltros = false },
                    onDismiss = { showFiltros = false }
                )
            }
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                !errorMessage.isNullOrEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
                    }
                }
                productosFiltrados.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No hay productos disponibles")
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(productosFiltrados) { producto ->
                            TarjetaProducto(
                                producto = producto,
                                usuarioActual = usuarioActual,
                                favoritos = favoritos,
                                onToggleFavorito = { favorito ->
                                    favoritoViewModel.toggleFavorito(favorito.nombre)
                                    showSnackbar(
                                        if (favoritos.any { it.producto == favorito.nombre })
                                            "Quitado de favoritos"
                                        else
                                            "Agregado a favoritos"
                                    )
                                },
                                onAgregarCarrito = { productoSeleccionado ->
                                    carritoViewModel.agregarProducto(
                                        productoSeleccionado.idProducto,
                                        productoSeleccionado.nombre,
                                        productoSeleccionado.precio,
                                        productoSeleccionado.categoria
                                    )
                                    showSnackbar("Producto agregado al carrito")
                                },
                                onNavigateToDetalle = { onNavigateToDetalle(producto.idProducto) },
                                showSnackbar = { msg -> showSnackbar(msg) }
                            )
                        }
                    }
                }
            }
        }
    }
}
