package cl.duoc.basico.repository

import androidx.room.*
import cl.duoc.basico.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Query("SELECT * FROM producto")
    fun getAll(): Flow<List<Producto>>

    @Query("SELECT * FROM producto")
    suspend fun getAllSync(): List<Producto>

    @Query("SELECT * FROM producto WHERE idProducto = :id")
    suspend fun getById(id: Int): Producto?

    @Query("SELECT * FROM producto WHERE categoria = :categoria")
    fun getByCategoria(categoria: String): Flow<List<Producto>>

    @Query("SELECT * FROM producto WHERE supermercado = :supermercado")
    fun getBySupermercado(supermercado: String): Flow<List<Producto>>

    @Query("SELECT DISTINCT categoria FROM producto")
    suspend fun getCategorias(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: Producto)

    @Delete
    suspend fun delete(producto: Producto)

    @Update
    suspend fun update(producto: Producto)
}
