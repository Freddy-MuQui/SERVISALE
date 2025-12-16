package cl.duoc.basico.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cl.duoc.basico.repository.AppDatabase
import cl.duoc.basico.ui.screens.*

@Composable
fun AppNavigation(navController: NavHostController, db: AppDatabase) {
    var usuarioActual by remember { mutableStateOf<String?>(null) }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
        }

        composable(Screen.Login.route) {
            LoginScreen(
                db = db,
                onLoginSuccess = { email ->
                    usuarioActual = email
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Registro.route)
                }
            )
        }

        composable(Screen.Registro.route) {
            RegistroScreen(
                db = db,
                onRegisterSuccess = {
                    navController.popBackStack()
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                db = db,
                usuarioActual = usuarioActual ?: "",
                onNavigateToDetalle = { productoId ->
                    navController.navigate(Screen.DetalleProducto.createRoute(productoId))
                },
                onNavigateToCarrito = {
                    navController.navigate(Screen.Carrito.route)
                },
                onNavigateToFavoritos = {
                    navController.navigate(Screen.Favoritos.route)
                },
                onNavigateToPerfil = {
                    navController.navigate(Screen.Perfil.route)
                }
            )
        }

        composable(
            route = Screen.DetalleProducto.route,
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
            DetalleProductoScreen(
                db = db,
                productoId = productoId,
                usuarioActual = usuarioActual ?: "",
                onBack = { navController.popBackStack() },
                onVerMapa = {
                    navController.navigate(Screen.Mapa.createRoute(productoId))
                }
            )
        }

        composable(Screen.Carrito.route) {
            CarritoScreen(
                db = db,
                usuarioActual = usuarioActual ?: "",
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Favoritos.route) {
            FavoritosScreen(
                db = db,
                usuarioActual = usuarioActual ?: "",
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Perfil.route) {
            PerfilScreen(
                usuarioEmail = usuarioActual ?: "",
                onBack = { navController.popBackStack() },
                onLogout = {
                    usuarioActual = null
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.Mapa.route,
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
            MapaScreen(
                db = db,
                productoId = productoId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
