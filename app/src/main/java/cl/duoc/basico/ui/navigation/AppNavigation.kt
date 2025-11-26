package cl.duoc.basico.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cl.duoc.basico.repository.AppDatabase
import cl.duoc.basico.ui.screens.*

@Composable
fun AppNavigation(
    navController: NavHostController,
    db: AppDatabase
) {
    val usuarioLogueado = remember { mutableStateOf<String?>(null) }

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(
                onNavigateToLogin = { navController.navigate("login") }
            )
        }

        composable("login") {
            LoginScreen(
                db = db,
                onLoginSuccess = { userEmail ->
                    usuarioLogueado.value = userEmail
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate("registro") }
            )
        }

        composable("registro") {
            RegistroScreen(
                db = db,
                onRegisterSuccess = {
                    navController.navigate("login") {
                        popUpTo("registro") { inclusive = true }
                    }
                },
                onBackToLogin = { navController.popBackStack() }
            )
        }

        composable("home") {
            // Solo navega si usuario válido
            if (!usuarioLogueado.value.isNullOrBlank()) {
                HomeScreen(
                    db = db,
                    usuarioActual = usuarioLogueado.value ?: "",
                    onNavigateToDetalle = { productoId ->
                        navController.navigate("detalleProducto/$productoId")
                    },
                    onNavigateToCarrito = { navController.navigate("carrito") },
                    onNavigateToFavoritos = { navController.navigate("favoritos") },
                    onNavigateToPerfil = { navController.navigate("perfil") }
                )
            } else {
                navController.navigate("login") { popUpTo("home") { inclusive = true } }
            }
        }

        composable("detalleProducto/{productoId}") { backStackEntry ->
            val productoId = backStackEntry.arguments
                ?.getString("productoId")
                ?.toIntOrNull() ?: 0
            DetalleProductoScreen(
                db = db,
                productoId = productoId,
                usuarioActual = usuarioLogueado.value ?: "",
                onBack = { navController.popBackStack() },
                onVerMapa = { navController.navigate("mapa/$productoId") }
            )
        }

        composable("mapa/{productoId}") { backStackEntry ->
            val productoId = backStackEntry.arguments
                ?.getString("productoId")
                ?.toIntOrNull() ?: 0
            MapaScreen(
                db = db,
                productoId = productoId,
                onBack = { navController.popBackStack() }
            )
        }

        composable("carrito") {
            CarritoScreen(
                db = db,
                usuarioActual = usuarioLogueado.value ?: "",
                onBack = { navController.popBackStack() }
            )
        }

        composable("favoritos") {
            FavoritosScreen(
                db = db,
                usuarioActual = usuarioLogueado.value ?: "",
                onBack = { navController.popBackStack() }
            )
        }

        composable("perfil") {
            PerfilScreen(
                usuarioEmail = usuarioLogueado.value ?: "",
                onBack = { navController.popBackStack() },
                onLogout = {
                    usuarioLogueado.value = null
                    navController.navigate("login") { popUpTo("home") { inclusive = true } }
                }
            )
        }
    }
}
