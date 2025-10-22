package cl.duoc.basico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import cl.duoc.basico.repository.AppDatabase
import cl.duoc.basico.repository.DatabaseProvider
import cl.duoc.basico.ui.navigation.AppNavigation
import cl.duoc.basico.ui.theme.AppSupermarketTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val db = remember {
                DatabaseProvider.getDatabase(this@MainActivity)
            }
            val navController = rememberNavController()

            AppSupermarketTheme {
                AppNavigation(
                    navController = navController,
                    db = db
                )
            }
        }
    }
}

@Composable
fun rememberDatabase(): AppDatabase {
    val context = LocalContext.current
    return remember {
        DatabaseProvider.getDatabase(context)
    }
}
