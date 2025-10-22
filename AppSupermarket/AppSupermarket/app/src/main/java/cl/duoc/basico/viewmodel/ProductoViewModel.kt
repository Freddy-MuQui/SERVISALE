package cl.duoc.basico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.basico.model.Producto
import cl.duoc.basico.repository.ProductoDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductoViewModel(private val productoDao: ProductoDao) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val _categorias = MutableStateFlow<List<String>>(emptyList())
    val categorias: StateFlow<List<String>> = _categorias

    private val _productoSeleccionado = MutableStateFlow<Producto?>(null)
    val productoSeleccionado: StateFlow<Producto?> = _productoSeleccionado

    init {
        cargarProductos()
        cargarCategorias()
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            productoDao.getAll().collect {
                _productos.value = it
            }
        }
    }

    private fun cargarCategorias() {
        viewModelScope.launch {
            _categorias.value = productoDao.getCategorias()
        }
    }

    fun buscarPorNombre(nombre: String) {
        val filtrados = _productos.value.filter {
            it.nombre.contains(nombre, ignoreCase = true)
        }
        _productos.value = filtrados
    }

    fun filtrarPorCategoria(categoria: String) {
        viewModelScope.launch {
            productoDao.getByCategoria(categoria).collect {
                _productos.value = it
            }
        }
    }

    fun obtenerProductoPorId(id: Int) {
        viewModelScope.launch {
            _productoSeleccionado.value = productoDao.getById(id)
        }
    }

    fun obtenerMejorPrecio(nombreProducto: String): Producto? {
        return _productos.value
            .filter { it.nombre == nombreProducto }
            .minByOrNull { it.precio }
    }
}
