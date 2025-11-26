package cl.duoc.basico.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "producto")
data class Producto(
    @PrimaryKey(autoGenerate = false) val idProducto: Int,
    val nombre: String,           // Usará title de la API
    val descripcion: String,
    val precio: Float,
    val cantidadDisponible: Int,
    val supermercado: String,
    val categoria: String,
    val imagenUrl: String = "",
    val esGenerico: Boolean = false
)
