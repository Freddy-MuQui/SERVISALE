package cl.duoc.basico.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cl.duoc.basico.repository.AppDatabase
import cl.duoc.basico.ui.components.ValidatedTextField
import cl.duoc.basico.utils.validarEmail
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    db: AppDatabase,
    onLoginSuccess: (String) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") } // Entrada de usuario.
    var password by remember { mutableStateOf("") } // Contraseña.
    var passwordVisible by remember { mutableStateOf(false) } // Toggle visibilidad.
    var errorEmail by remember { mutableStateOf(false) } // Flag error email.
    var errorPassword by remember { mutableStateOf(false) } // Flag error pass.
    var errorMessage by remember { mutableStateOf("") } // Texto de error visible.
    var isLoading by remember { mutableStateOf(false) } // Estado de carga.
    val scope = rememberCoroutineScope() // Operaciones suspend y DB.

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Iniciar Sesión") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Compara Precio",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Encuentra los mejores precios",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(48.dp))

            ValidatedTextField(
                value = email,
                onValueChange = {
                    email = it
                    errorEmail = false
                    errorMessage = "" // Limpia error al tipear.
                },
                label = "Correo electrónico",
                isError = errorEmail,
                errorMessage = if (errorEmail) "Email inválido" else "",
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ValidatedTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorPassword = false
                    errorMessage = "" // Limpia error al tipear.
                },
                label = "Contraseña",
                isError = errorPassword,
                errorMessage = if (errorPassword) "Contraseña incorrecta" else "",
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) { // Toggle visibilidad.
                        Icon(
                            if (passwordVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation()
            )

            AnimatedVisibility(
                visible = errorMessage.isNotEmpty(),
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    when {
                        !validarEmail(email) -> { // Validación sintáctica de correo.
                            errorEmail = true
                            errorMessage = "Por favor ingresa un email válido"
                        }
                        password.length < 6 -> { // Regla mínima de contraseña.
                            errorPassword = true
                            errorMessage = "La contraseña debe tener al menos 6 caracteres"
                        }
                        else -> {
                            isLoading = true // Bloquea inputs y muestra loading.
                            scope.launch {
                                val usuario = db.usuarioDao().getByEmail(email) // Consulta DB local.
                                if (usuario != null && usuario.password == password) {
                                    onLoginSuccess(email) // Notifica éxito a navegación superior.
                                } else {
                                    isLoading = false
                                    errorMessage = "Credenciales incorrectas"
                                    errorEmail = true
                                    errorPassword = true
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading // Evita taps múltiples.
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Iniciar Sesión")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onNavigateToRegister) { // Navega a registro.
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}
