package cl.duoc.basico.repository

import androidx.room.*
import cl.duoc.basico.model.HistorialBusqueda

@Dao
interface HistorialBusquedaDao {
    @Query("SELECT * FROM historial_busqueda WHERE usuario = :usuario")
    suspend fun getAllByUsuario(usuario: String): List<HistorialBusqueda>

    @Insert
    suspend fun insert(historial: HistorialBusqueda)
}
