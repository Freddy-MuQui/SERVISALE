package cl.duoc.basico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.basico.model.Favorito
import cl.duoc.basico.repository.FavoritoDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoritoViewModel(
    private val favoritoDao: FavoritoDao,
    private val usuarioActual: String
) : ViewModel() {

    private val _favoritos = MutableStateFlow<List<Favorito>>(emptyList()) // Estado local de favoritos por usuario.
    val favoritos: StateFlow<List<Favorito>> = _favoritos // Exposición inmutable para UI.

    init {
        cargarFavoritos() // Carga inicial para mostrar lista.
    }

    private fun cargarFavoritos() {
        viewModelScope.launch {
            _favoritos.value = favoritoDao.getAllByUsuario(usuarioActual) // Consulta por usuario.
        }
    }

    fun toggleFavorito(productoNombre: String) {
        viewModelScope.launch {
            val favoritoExistente = _favoritos.value.find { it.producto == productoNombre }
            if (favoritoExistente != null) {
                favoritoDao.delete(favoritoExistente) // Elimina si ya existe.
            } else {
                favoritoDao.insert(Favorito(usuario = usuarioActual, producto = productoNombre)) // Inserta si no está.
            }
            cargarFavoritos() // Refresca estado tras mutación.
        }
    }

    fun esFavorito(productoNombre: String): Boolean {
        return _favoritos.value.any { it.producto == productoNombre } // Comprobación rápida en memoria.
    }
}
