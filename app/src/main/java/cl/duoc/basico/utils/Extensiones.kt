package cl.duoc.basico.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatoParaMostrar(): String {
    val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) // Formato fecha-hora local.
    return formato.format(this) // Convierte Date a String para UI.
}

fun convertirAPesosChilenos(precioUsd: Float): Int {
    val tasaCambio = 930f // valor aproximado, puedes ajustarlo
    return (precioUsd * tasaCambio).toInt()
}


fun Date.soloFecha(): String {
    val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) // Solo fecha.
    return formato.format(this) // Formatea sin hora.
}

fun fechaActual(): String {
    return Date().formatoParaMostrar() // Fecha-hora actual formateada.
}

fun Float.formatoMoneda(): String {
    return String.format("$%.0f", this) // Muestra moneda sin decimales para precios enteros.
}

fun List<Any>.aTexto(): String {
    return this.joinToString(", ") // Une elementos con coma para vista rápida en texto.
}
