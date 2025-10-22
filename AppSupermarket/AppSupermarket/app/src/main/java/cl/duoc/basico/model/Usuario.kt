package cl.duoc.basico.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val idUsuario: Int = 0,
    val nombre: String,
    val email: String,
    val password: String
)
