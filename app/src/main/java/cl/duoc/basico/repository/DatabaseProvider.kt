package cl.duoc.basico.repository

import android.content.Context
import cl.duoc.basico.model.Region
import cl.duoc.basico.model.Comuna
import cl.duoc.basico.model.Usuario
import cl.duoc.basico.model.Favorito
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseProvider {
    fun getDatabase(context: Context): AppDatabase {
        val db = AppDatabase.getDatabase(context)
        inicializarTablasBase(db)
        return db
    }

    private fun inicializarTablasBase(db: AppDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            // Región Metropolitana
            try {
                val regiones = listOf(Region(nombre = "Región Metropolitana"))
                if (db.regionDao().getAll().isEmpty()) {
                    for (r in regiones) db.regionDao().insert(r)
                }
            } catch (_: Exception) {}

            // Comunas
            try {
                val comunas = listOf(
                    Comuna(nombre = "Santiago", region = "Región Metropolitana"),
                    Comuna(nombre = "Providencia", region = "Región Metropolitana"),
                    Comuna(nombre = "Las Condes", region = "Región Metropolitana"),
                    Comuna(nombre = "Puente Alto", region = "Región Metropolitana"),
                    Comuna(nombre = "Maipú", region = "Región Metropolitana")
                )
                if (db.comunaDao().getAll().isEmpty()) {
                    for (c in comunas) db.comunaDao().insert(c)
                }
            } catch (_: Exception) {}

            // Usuario demo (parametrizado en todos lados vía "email")
            try {
                val emailDemo = "demo@email.com"
                if (db.usuarioDao().getByEmail(emailDemo) == null) {
                    db.usuarioDao().insert(
                        Usuario(nombre = "Usuario Demo", email = emailDemo, password = "demo123")
                    )
                }
            } catch (_: Exception) {}

            // Favorito demo para usuario demo
            try {
                val emailDemo = "demo@email.com"
                if (db.favoritoDao().getAllByUsuario(emailDemo).isEmpty()) {
                    db.favoritoDao().insert(
                        Favorito(
                            producto = "Arroz",
                            usuario = emailDemo
                        )
                    )
                }
            } catch (_: Exception) {}
        }
    }
}
