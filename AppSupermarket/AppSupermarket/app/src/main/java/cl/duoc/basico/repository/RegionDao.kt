package cl.duoc.basico.repository

import androidx.room.*
import cl.duoc.basico.model.Region

@Dao
interface RegionDao {
    @Query("SELECT * FROM region")
    suspend fun getAll(): List<Region>

    @Insert
    suspend fun insert(region: Region)
}
