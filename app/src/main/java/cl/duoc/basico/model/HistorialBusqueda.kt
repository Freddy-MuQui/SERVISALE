package cl.duoc.basico.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historial_busqueda")
data class HistorialBusqueda(
    @PrimaryKey(autoGenerate = true) val idBusqueda: Int = 0,
    val usuario: String,
    val termino: String,
    val fecha: String
)
