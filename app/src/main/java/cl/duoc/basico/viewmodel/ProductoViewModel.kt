package cl.duoc.basico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.basico.model.Producto
import cl.duoc.basico.network.ApiProducto
import cl.duoc.basico.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductoViewModel : ViewModel() {
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val _productoSeleccionado = MutableStateFlow<Producto?>(null)
    val productoSeleccionado: StateFlow<Producto?> = _productoSeleccionado

    private val supermercados = listOf("Jumbo", "Líder", "Tottus", "Santa Isabel", "Unimarc")
    private val categorias = listOf("bebidas", "despensa", "limpieza", "snacks", "congelados", "lácteos", "panadería", "cuidado personal")

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        cargarProductos()
    }

    private fun mapApiToProducto(api: ApiProducto, index: Int): Producto {
        return Producto(
            idProducto = api.id,
            nombre = api.title,
            descripcion = api.description,
            precio = api.price,
            cantidadDisponible = (10..120).random(),
            supermercado = supermercados.random(),
            categoria = categorias[index % categorias.size],
            imagenUrl = api.thumbnail,
            esGenerico = false
        )
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = RetrofitInstance.api.getProductosSupermercado()
                val seleccion = response.products
                    .take(20)
                    .mapIndexed { index, apiProduct -> mapApiToProducto(apiProduct, index) }

                _productos.value = seleccion
            } catch (e: Exception) {
                _productos.value = emptyList()
                _errorMessage.value = "Error al cargar productos. Intenta más tarde."
            }
            _isLoading.value = false
        }
    }

    fun obtenerProductoPorId(id: Int) {
        _productoSeleccionado.value = _productos.value.find { it.idProducto == id }
    }
}
