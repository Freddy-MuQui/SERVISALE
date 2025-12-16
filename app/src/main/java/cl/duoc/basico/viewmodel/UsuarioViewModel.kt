package cl.duoc.basico.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.basico.model.Usuario
import cl.duoc.basico.repository.UsuarioDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(private val usuarioDao: UsuarioDao) : ViewModel() {

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual

    fun registrarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuarioDao.insert(usuario)
        }
    }

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val usuario = usuarioDao.login(email, password)
            _usuarioActual.value = usuario
            onResult(usuario != null)
        }
    }

    fun guardarNombreSesion(context: Context, nombreUsuario: String) {
        val prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        prefs.edit()
            .putString("username", nombreUsuario)
            .apply()
    }

}
