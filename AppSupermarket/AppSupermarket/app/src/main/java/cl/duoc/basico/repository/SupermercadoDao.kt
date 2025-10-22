package cl.duoc.basico.repository

import androidx.room.*
import cl.duoc.basico.model.Supermercado
import kotlinx.coroutines.flow.Flow

@Dao
interface SupermercadoDao {
    @Query("SELECT * FROM supermercado")
    fun getAll(): Flow<List<Supermercado>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(supermercado: Supermercado)
}
