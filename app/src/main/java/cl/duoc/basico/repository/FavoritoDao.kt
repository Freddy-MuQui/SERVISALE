package cl.duoc.basico.repository

import androidx.room.*
import cl.duoc.basico.model.Favorito

@Dao
interface FavoritoDao {
    @Query("SELECT * FROM favorito WHERE usuario = :usuario")
    suspend fun getAllByUsuario(usuario: String): List<Favorito>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorito: Favorito)

    @Delete
    suspend fun delete(favorito: Favorito)

    @Query("DELETE FROM favorito WHERE usuario = :usuario AND producto = :producto")
    suspend fun deleteByUsuarioAndProducto(usuario: String, producto: String)
}
