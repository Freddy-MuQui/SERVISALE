package cl.duoc.basico.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Query("SELECT * FROM carrito WHERE productoId = :productoId AND usuario = :usuario LIMIT 1")
    suspend fun obtenerItemPorProductoYUsuario(
        productoId: Int,
        usuario: String
    ): ItemCarrito?

    @Query("UPDATE carrito SET cantidad = :nuevaCantidad WHERE id = :itemId")
    suspend fun actualizarCantidad(
        itemId: Int,
        nuevaCantidad: Int
    )
}
