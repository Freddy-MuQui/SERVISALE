package cl.duoc.basico.repository

import androidx.room.*
import cl.duoc.basico.model.Usuario

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: Usuario)

    @Query("SELECT * FROM usuario WHERE email = :email")
    suspend fun getByEmail(email: String): Usuario?

    @Query("SELECT * FROM usuario WHERE email = :email AND password = :password")
    suspend fun login(email: String, password: String): Usuario?
}
