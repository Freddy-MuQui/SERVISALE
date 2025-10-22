package cl.duoc.basico.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carrito")
data class ItemCarrito(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productoId: Int,
    val nombreProducto: String,
    val precio: Float,
    val cantidad: Int,
    val usuario: String
)
