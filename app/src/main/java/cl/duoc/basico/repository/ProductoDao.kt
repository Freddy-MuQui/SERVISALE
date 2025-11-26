package cl.duoc.basico.repository

import androidx.room.*
import cl.duoc.basico.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Query("SELECT * FROM producto")
    suspend fun getAllSync(): List<Producto> // Puedes dejarlo si lo usas en Room

    @Query("SELECT * FROM producto")
    fun getAll(): Flow<List<Producto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: Producto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(productos: List<Producto>)

    @Query("SELECT * FROM producto WHERE idProducto = :id")
    suspend fun getById(id: Int): Producto?
}
