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
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorEmail by remember { mutableStateOf(false) }
    var errorPassword by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }


    val scope = rememberCoroutineScope()

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
                    errorMessage = ""
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
                    errorMessage = ""
                },
                label = "Contraseña",
                isError = errorPassword,
                errorMessage = if (errorPassword) "Contraseña incorrecta" else "",
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
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
                        !validarEmail(email) -> {
                            errorEmail = true
                            errorMessage = "Por favor ingresa un email válido"
                        }
                        password.length < 6 -> {
                            errorPassword = true
                            errorMessage = "La contraseña debe tener al menos 6 caracteres"
                        }
                        else -> {
                            isLoading = true
                            scope.launch {
                                val usuario = db.usuarioDao().getByEmail(email)
                                if (usuario != null && usuario.password == password) {
                                    onLoginSuccess(email)
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
                enabled = !isLoading
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

            TextButton(onClick = onNavigateToRegister) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}
