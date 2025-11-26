package cl.duoc.basico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.basico.model.Supermercado
import cl.duoc.basico.repository.SupermercadoDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class SupermercadoViewModel(private val supermercadoDao: SupermercadoDao) : ViewModel() {

    val supermercados: StateFlow<List<Supermercado>> = supermercadoDao.getAll() // Flujo del DAO con la lista.
        .stateIn(
            scope = viewModelScope, // Mantiene el flujo activo mientras el VM esté vivo.
            started = SharingStarted.WhileSubscribed(5000), // Reengancha durante 5s tras la última suscripción.
            initialValue = emptyList() // Valor inicial para la UI.
        )
}
