package cl.duoc.basico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.basico.model.ItemCarrito
import cl.duoc.basico.repository.CarritoDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarritoViewModel(
    private val carritoDao: CarritoDao,
    private val usuarioActual: String
) : ViewModel() {

    private val _carrito = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val carrito: StateFlow<List<ItemCarrito>> = _carrito

    init {
        cargarCarrito()
    }

    fun cargarCarrito() {
        viewModelScope.launch {
            carritoDao.getCarritoByUsuario(usuarioActual).collect { lista ->
                _carrito.value = lista
            }
        }
    }

    fun agregarProducto(
        productoId: Int,
        nombre: String,
        precio: Float,
        categoria: String,
        cantidad: Int = 1,
        imagenUrl: String          // NUEVO PARÁMETRO
    ) {
        viewModelScope.launch {
            val itemExistente =
                carritoDao.obtenerItemPorProductoYUsuario(productoId, usuarioActual)

            if (itemExistente != null) {
                val nuevaCantidad = itemExistente.cantidad + cantidad
                carritoDao.actualizarCantidad(itemExistente.id, nuevaCantidad)
            } else {
                val nuevoItem = ItemCarrito(
                    productoId = productoId,
                    nombreProducto = nombre,
                    precio = precio,
                    cantidad = cantidad,
                    usuario = usuarioActual,
                    categoria = categoria,
                    imagenUrl = imagenUrl
                )
                carritoDao.agregarAlCarrito(nuevoItem)
            }

            cargarCarrito()
        }
    }

    fun eliminarItem(itemId: Int) {
        viewModelScope.launch {
            carritoDao.eliminarDelCarrito(itemId)
            cargarCarrito()
        }
    }

    fun actualizarCantidad(itemId: Int, nuevaCantidad: Int) {
        viewModelScope.launch {
            if (nuevaCantidad > 0) {
                carritoDao.actualizarCantidad(itemId, nuevaCantidad)
            } else {
                carritoDao.eliminarDelCarrito(itemId)
            }
            cargarCarrito()
        }
    }

    fun vaciarCarrito() {
        viewModelScope.launch {
            carritoDao.vaciarCarrito(usuarioActual)
            cargarCarrito()
        }
    }

    fun calcularTotal(): Float {
        return carrito.value.sumOf { it.precio * it.cantidad.toDouble() }.toFloat()
    }
}
