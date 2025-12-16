package cl.duoc.basico.repository

import android.content.Context
import cl.duoc.basico.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseProvider {

    fun getDatabase(context: Context): AppDatabase {
        val db = AppDatabase.getDatabase(context)
        initializeDatabaseIfEmpty(db)
        return db
    }

    private fun initializeDatabaseIfEmpty(db: AppDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            // Verificar si ya hay datos
            val productosExistentes = db.productoDao().getAllSync()
            if (productosExistentes.isEmpty()) {
                insertarDatosIniciales(db)
            }
        }
    }

    private suspend fun insertarDatosIniciales(db: AppDatabase) {
        // Insertar regiones
        val regiones = listOf(
            Region(nombre = "Región Metropolitana"),
            Region(nombre = "Región de Valparaíso"),
            Region(nombre = "Región del Biobío")
        )
        regiones.forEach { db.regionDao().insert(it) }

        // Insertar comunas
        val comunas = listOf(
            Comuna(nombre = "Santiago", region = "Región Metropolitana"),
            Comuna(nombre = "Providencia", region = "Región Metropolitana"),
            Comuna(nombre = "Las Condes", region = "Región Metropolitana"),
            Comuna(nombre = "Maipú", region = "Región Metropolitana"),
            Comuna(nombre = "Valparaíso", region = "Región de Valparaíso"),
            Comuna(nombre = "Viña del Mar", region = "Región de Valparaíso"),
            Comuna(nombre = "Concepción", region = "Región del Biobío"),
            Comuna(nombre = "Talcahuano", region = "Región del Biobío")
        )
        comunas.forEach { db.comunaDao().insert(it) }

        // Insertar supermercados
        val supermercados = listOf(
            Supermercado(
                nombre = "Líder Santiago Centro",
                latitud = -33.4489,
                longitud = -70.6693,
                direccion = "Av. Libertador Bernardo O'Higgins 1234",
                comuna = "Santiago",
                region = "Región Metropolitana"
            ),
            Supermercado(
                nombre = "Jumbo Providencia",
                latitud = -33.4372,
                longitud = -70.6106,
                direccion = "Av. Providencia 2510",
                comuna = "Providencia",
                region = "Región Metropolitana"
            ),
            Supermercado(
                nombre = "Santa Isabel Las Condes",
                latitud = -33.4156,
                longitud = -70.5756,
                direccion = "Av. Apoquindo 4900",
                comuna = "Las Condes",
                region = "Región Metropolitana"
            ),
            Supermercado(
                nombre = "Unimarc Maipú",
                latitud = -33.5115,
                longitud = -70.7580,
                direccion = "Av. Pajaritos 3020",
                comuna = "Maipú",
                region = "Región Metropolitana"
            )
        )
        supermercados.forEach { db.supermercadoDao().insert(it) }

        // Insertar productos de ejemplo
        val productos = listOf(
            Producto(
                nombre = "Leche Descremada 1L",
                descripcion = "Leche descremada pasteurizada",
                precio = 990f,
                cantidadDisponible = 100,
                supermercado = "Líder Santiago Centro",
                categoria = "Lácteos",
                esGenerico = false
            ),
            Producto(
                nombre = "Leche Descremada 1L",
                descripcion = "Leche descremada pasteurizada",
                precio = 1090f,
                cantidadDisponible = 80,
                supermercado = "Jumbo Providencia",
                categoria = "Lácteos",
                esGenerico = false
            ),
            Producto(
                nombre = "Pan de Molde Integral",
                descripcion = "Pan de molde integral 500g",
                precio = 1490f,
                cantidadDisponible = 50,
                supermercado = "Santa Isabel Las Condes",
                categoria = "Panadería",
                esGenerico = false
            ),
            Producto(
                nombre = "Pan de Molde Integral",
                descripcion = "Pan de molde integral 500g",
                precio = 1390f,
                cantidadDisponible = 60,
                supermercado = "Unimarc Maipú",
                categoria = "Panadería",
                esGenerico = false
            ),
            Producto(
                nombre = "Arroz Grado 1 1kg",
                descripcion = "Arroz blanco grado 1",
                precio = 1290f,
                cantidadDisponible = 120,
                supermercado = "Líder Santiago Centro",
                categoria = "Abarrotes",
                esGenerico = true
            ),
            Producto(
                nombre = "Arroz Grado 1 1kg",
                descripcion = "Arroz blanco grado 1",
                precio = 1190f,
                cantidadDisponible = 90,
                supermercado = "Jumbo Providencia",
                categoria = "Abarrotes",
                esGenerico = true
            ),
            Producto(
                nombre = "Aceite Vegetal 900ml",
                descripcion = "Aceite vegetal 100% natural",
                precio = 2490f,
                cantidadDisponible = 70,
                supermercado = "Santa Isabel Las Condes",
                categoria = "Abarrotes",
                esGenerico = false
            ),
            Producto(
                nombre = "Aceite Vegetal 900ml",
                descripcion = "Aceite vegetal 100% natural",
                precio = 2390f,
                cantidadDisponible = 85,
                supermercado = "Unimarc Maipú",
                categoria = "Abarrotes",
                esGenerico = false
            ),
            Producto(
                nombre = "Azúcar Blanca 1kg",
                descripcion = "Azúcar refinada",
                precio = 890f,
                cantidadDisponible = 150,
                supermercado = "Líder Santiago Centro",
                categoria = "Abarrotes",
                esGenerico = true
            ),
            Producto(
                nombre = "Azúcar Blanca 1kg",
                descripcion = "Azúcar refinada",
                precio = 950f,
                cantidadDisponible = 130,
                supermercado = "Jumbo Providencia",
                categoria = "Abarrotes",
                esGenerico = true
            ),
            Producto(
                nombre = "Fideos Spaghetti 400g",
                descripcion = "Pasta italiana tradicional",
                precio = 790f,
                cantidadDisponible = 200,
                supermercado = "Santa Isabel Las Condes",
                categoria = "Abarrotes",
                esGenerico = false
            ),
            Producto(
                nombre = "Fideos Spaghetti 400g",
                descripcion = "Pasta italiana tradicional",
                precio = 690f,
                cantidadDisponible = 180,
                supermercado = "Unimarc Maipú",
                categoria = "Abarrotes",
                esGenerico = false
            )
        )
        productos.forEach { db.productoDao().insert(it) }

        // Usuario de prueba
        db.usuarioDao().insert(
            Usuario(
                nombre = "Usuario Demo",
                email = "demo@test.com",
                password = "123456"
            )
        )
    }
}
