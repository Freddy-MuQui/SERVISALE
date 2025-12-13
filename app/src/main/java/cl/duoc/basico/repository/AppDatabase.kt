package cl.duoc.basico.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.duoc.basico.model.*

@Database(
    entities = [
        Producto::class,
        Usuario::class,
        Favorito::class,
        Supermercado::class,
        Region::class,
        Comuna::class,
        ItemCarrito::class
    ],
    version = 5, // sube en +1 al agregar imagenUrl o cualquier cambio
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun favoritoDao(): FavoritoDao
    abstract fun supermercadoDao(): SupermercadoDao
    abstract fun regionDao(): RegionDao
    abstract fun comunaDao(): ComunaDao
    abstract fun carritoDao(): CarritoDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "compara_precio_db"
                )
                    .fallbackToDestructiveMigration() // mantiene tu comportamiento actual
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
