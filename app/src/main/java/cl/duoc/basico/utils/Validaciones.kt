package cl.duoc.basico.utils

import android.util.Patterns

fun validarEmail(email: String): Boolean {
    return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() // Usa patrón del SDK para email.
}

fun validarPassword(password: String): Boolean {
    return password.length >= 6 // Regla mínima de longitud.
}

fun validarNombre(nombre: String): Boolean {
    return nombre.isNotEmpty() && nombre.length >= 3 // Nombre requerido y con longitud mínima.
}

fun noEsVacio(campo: String): Boolean {
    return campo.isNotBlank() // Evita campos vacíos o solo espacios.
}

fun validarFormatoEmail(email: String): Boolean {
    val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}") // Regex explícito como alternativa.
    return emailPattern.matches(email) // Coincidencia con el patrón definido.
}
