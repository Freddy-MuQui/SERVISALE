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

    private val _favoritos = MutableStateFlow<List<Favorito>>(emptyList())
    val favoritos: StateFlow<List<Favorito>> = _favoritos

    init {
        cargarFavoritos()
    }

    private fun cargarFavoritos() {
        viewModelScope.launch {
            _favoritos.value = favoritoDao.getAllByUsuario(usuarioActual)
        }
    }

    fun toggleFavorito(productoNombre: String) {
        viewModelScope.launch {
            val favoritoExistente = _favoritos.value.find { it.producto == productoNombre }
            if (favoritoExistente != null) {
                favoritoDao.delete(favoritoExistente)
            } else {
                favoritoDao.insert(Favorito(usuario = usuarioActual, producto = productoNombre))
            }
            cargarFavoritos()
        }
    }

    fun esFavorito(productoNombre: String): Boolean {
        return _favoritos.value.any { it.producto == productoNombre }
    }
}
