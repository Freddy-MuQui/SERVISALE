package cl.duoc.basico.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "producto")
data class Producto(
    @PrimaryKey(autoGenerate = true) val idProducto: Int = 0,
    val nombre: String,
    val descripcion: String,
    val precio: Float,
    val cantidadDisponible: Int,
    val supermercado: String,
    val categoria: String,
    val esGenerico: Boolean = false
)
