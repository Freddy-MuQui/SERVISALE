package cl.duoc.basico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.duoc.basico.repository.ProductoDao

class ProductoViewModelFactory(private val productoDao: ProductoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductoViewModel(productoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
