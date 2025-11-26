package cl.duoc.basico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.basico.model.Usuario
import cl.duoc.basico.repository.UsuarioDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(private val usuarioDao: UsuarioDao) : ViewModel() {

    private val _usuarioActual = MutableStateFlow<Usuario?>(null) // Estado de sesión actual.
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual // Exposición inmutable para observar sesión.

    fun registrarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuarioDao.insert(usuario) // Inserta usuario en BD local.
        }
    }

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val usuario = usuarioDao.login(email, password) // Consulta credenciales en BD.
            _usuarioActual.value = usuario // Actualiza estado de sesión.
            onResult(usuario != null) // Notifica éxito/fracaso al caller.
        }
    }
}
