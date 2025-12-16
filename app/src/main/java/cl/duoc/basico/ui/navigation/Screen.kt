package cl.duoc.basico.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Registro : Screen("registro")
    object Home : Screen("home")
    object DetalleProducto : Screen("detalle/{productoId}") {
        fun createRoute(productoId: Int) = "detalle/$productoId"
    }
    object Carrito : Screen("carrito")
    object Favoritos : Screen("favoritos")
    object Perfil : Screen("perfil")
    object Mapa : Screen("mapa/{productoId}") {
        fun createRoute(productoId: Int) = "mapa/$productoId"
    }
}
