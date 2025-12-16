package cl.duoc.basico.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatoParaMostrar(): String {
    val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return formato.format(this)
}

fun Date.soloFecha(): String {
    val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formato.format(this)
}

fun fechaActual(): String {
    return Date().formatoParaMostrar()
}

fun Float.formatoMoneda(): String {
    return String.format("$%.0f", this)
}

fun List<String>.aTexto(): String {
    return this.joinToString(", ")
}
