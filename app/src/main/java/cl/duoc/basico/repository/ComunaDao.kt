package cl.duoc.basico.repository

import androidx.room.*
import cl.duoc.basico.model.Comuna

@Dao
interface ComunaDao {
    @Query("SELECT * FROM comuna")
    suspend fun getAll(): List<Comuna>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comuna: Comuna)
}
