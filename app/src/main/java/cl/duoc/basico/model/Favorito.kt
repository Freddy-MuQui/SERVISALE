package cl.duoc.basico.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorito")
data class Favorito(
    @PrimaryKey(autoGenerate = true) val idFavorito: Int = 0,
    val usuario: String,
    val producto: String
)
