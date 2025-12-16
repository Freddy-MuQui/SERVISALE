package cl.duoc.basico.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun BottomNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedIndex == 0,
            onClick = { onItemSelected(0) },
            icon = { Icon(Icons.Default.Home, "Inicio") },
            label = { Text("Inicio") }
        )
        NavigationBarItem(
            selected = selectedIndex == 1,
            onClick = { onItemSelected(1) },
            icon = { Icon(Icons.Default.ShoppingCart, "Carrito") },
            label = { Text("Carrito") }
        )
        NavigationBarItem(
            selected = selectedIndex == 2,
            onClick = { onItemSelected(2) },
            icon = { Icon(Icons.Default.Favorite, "Favoritos") },
            label = { Text("Favoritos") }
        )
        NavigationBarItem(
            selected = selectedIndex == 3,
            onClick = { onItemSelected(3) },
            icon = { Icon(Icons.Default.Person, "Perfil") },
            label = { Text("Perfil") }
        )
    }
}
