package cl.duoc.basico.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supermercado")
data class Supermercado(
    @PrimaryKey(autoGenerate = true) val idSupermercado: Int = 0,
    val nombre: String,
    val latitud: Double,
    val longitud: Double,
    val direccion: String,
    val comuna: String,
    val region: String
)
