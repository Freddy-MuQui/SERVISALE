package cl.duoc.basico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.basico.model.ItemCarrito
import cl.duoc.basico.repository.CarritoDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CarritoViewModel(
    private val carritoDao: CarritoDao,
    private val usuarioActual: String
) : ViewModel() {

    // Categorías de productos comestibles
    private val categoriasComestibles = listOf("despensa", "snacks", "frutas", "carnes", "bebidas", "lácteos", "panadería", "congelados")

    private val _carrito = MutableStateFlow<List<ItemCarrito>>(emptyList()) // Estado interno del carrito por usuario.
    private val _carritoComestibles = MutableStateFlow<List<ItemCarrito>>(emptyList()) // Carrito filtrado solo con productos comestibles.
    
    // Exposición inmutable para la UI - solo productos comestibles
    val carrito: StateFlow<List<ItemCarrito>> = _carritoComestibles

    init {
        viewModelScope.launch { // Suscripción al carrito del usuario actual.
            carritoDao.getCarritoByUsuario(usuarioActual).collect { lista ->
                _carrito.value = lista // Sincroniza estado local con la BD.
                // Filtrar solo productos comestibles
                _carritoComestibles.value = lista.filter { item ->
                    item.categoria.isNotBlank() && 
                    categoriasComestibles.contains(item.categoria.lowercase()) 
                }
            }
        }
    }

    fun agregarProducto(productoId: Int, nombre: String, precio: Float, categoria: String) {
        viewModelScope.launch { // Mutación del carrito en BD.
            // Solo agregar si es un producto comestible
            if (!categoriasComestibles.contains(categoria.lowercase())) {
                return@launch
            }
            
            val itemExistente = _carrito.value.find { it.productoId == productoId }
            if (itemExistente != null) {
                carritoDao.actualizarCantidad(itemExistente.id, itemExistente.cantidad + 1) // Incrementa cantidad.
            } else {
                carritoDao.agregarAlCarrito(
                    ItemCarrito(
                        productoId = productoId,
                        nombreProducto = nombre,
                        precio = precio,
                        cantidad = 1,
                        usuario = usuarioActual,
                        categoria = categoria
                    ) // Inserta nuevo ítem para el usuario actual.
                )
            }
        }
    }

    fun eliminarItem(itemId: Int) {
        viewModelScope.launch {
            carritoDao.eliminarDelCarrito(itemId) // Elimina ítem por ID.
        }
    }

    fun actualizarCantidad(itemId: Int, nuevaCantidad: Int) {
        viewModelScope.launch {
            if (nuevaCantidad > 0) {
                carritoDao.actualizarCantidad(itemId, nuevaCantidad) // Actualiza cantidad válida.
            } else {
                carritoDao.eliminarDelCarrito(itemId) // Si llega a 0, elimina el ítem.
            }
        }
    }

    fun vaciarCarrito() {
        viewModelScope.launch {
            carritoDao.vaciarCarrito(usuarioActual) // Borra todos los ítems del usuario.
        }
    }

    fun calcularTotal(): Float {
        // Calcular total solo de productos comestibles
        return _carritoComestibles.value.sumOf { (it.precio * it.cantidad).toDouble() }.toFloat() // Suma total acumulada.
    }
}
