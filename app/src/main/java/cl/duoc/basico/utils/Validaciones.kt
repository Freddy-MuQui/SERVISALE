package cl.duoc.basico.utils

import android.util.Patterns

fun validarEmail(email: String): Boolean {
    return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun validarPassword(password: String): Boolean {
    return password.length >= 6
}

fun validarNombre(nombre: String): Boolean {
    return nombre.isNotEmpty() && nombre.length >= 3
}

fun noEsVacio(campo: String): Boolean {
    return campo.isNotBlank()
}

fun validarFormatoEmail(email: String): Boolean {
    val emailPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    return emailPattern.matches(email)
}
