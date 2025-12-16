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
        viewModelScope.launch {
            carritoDao.getCarritoByUsuario(usuarioActual).collect {
                _carrito.value = it
            }
        }
    }

    fun agregarProducto(productoId: Int, nombre: String, precio: Float) {
        viewModelScope.launch {
            val itemExistente = _carrito.value.find { it.productoId == productoId }
            if (itemExistente != null) {
                carritoDao.actualizarCantidad(itemExistente.id, itemExistente.cantidad + 1)
            } else {
                carritoDao.agregarAlCarrito(
                    ItemCarrito(
                        productoId = productoId,
                        nombreProducto = nombre,
                        precio = precio,
                        cantidad = 1,
                        usuario = usuarioActual
                    )
                )
            }
        }
    }

    fun eliminarItem(itemId: Int) {
        viewModelScope.launch {
            carritoDao.eliminarDelCarrito(itemId)
        }
    }

    fun actualizarCantidad(itemId: Int, nuevaCantidad: Int) {
        viewModelScope.launch {
            if (nuevaCantidad > 0) {
                carritoDao.actualizarCantidad(itemId, nuevaCantidad)
            } else {
                carritoDao.eliminarDelCarrito(itemId)
            }
        }
    }

    fun vaciarCarrito() {
        viewModelScope.launch {
            carritoDao.vaciarCarrito(usuarioActual)
        }
    }

    fun calcularTotal(): Float {
        return _carrito.value.sumOf { (it.precio * it.cantidad).toDouble() }.toFloat()
    }
}
