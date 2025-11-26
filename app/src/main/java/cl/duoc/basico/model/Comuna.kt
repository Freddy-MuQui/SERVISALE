package cl.duoc.basico.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comuna")
data class Comuna(
    @PrimaryKey(autoGenerate = true) val idComuna: Int = 0,
    val nombre: String,
    val region: String
)
