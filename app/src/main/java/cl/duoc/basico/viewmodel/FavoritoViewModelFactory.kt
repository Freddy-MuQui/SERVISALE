package cl.duoc.basico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.duoc.basico.repository.FavoritoDao

class FavoritoViewModelFactory(
    private val favoritoDao: FavoritoDao,
    private val usuarioActual: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritoViewModel(favoritoDao, usuarioActual) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
