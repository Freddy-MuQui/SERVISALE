package cl.duoc.basico.repository

import androidx.room.*
import cl.duoc.basico.model.ItemCarrito
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDao {
    @Query("SELECT * FROM carrito WHERE usuario = :usuario")
    fun getCarritoByUsuario(usuario: String): Flow<List<ItemCarrito>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun agregarAlCarrito(item: ItemCarrito)

    @Query("DELETE FROM carrito WHERE id = :itemId")
    suspend fun eliminarDelCarrito(itemId: Int)

    @Query("DELETE FROM carrito WHERE usuario = :usuario")
    suspend fun vaciarCarrito(usuario: String)

    @Query("UPDATE carrito SET cantidad = :cantidad WHERE id = :itemId")
    suspend fun actualizarCantidad(itemId: Int, cantidad: Int)
}
