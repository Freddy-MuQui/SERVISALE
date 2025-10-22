package cl.duoc.basico.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "region")
data class Region(
    @PrimaryKey(autoGenerate = true) val idRegion: Int = 0,
    val nombre: String
)
