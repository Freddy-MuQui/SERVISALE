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
import cl.duoc.basico.model.Usuario
import cl.duoc.basico.repository.AppDatabase
import cl.duoc.basico.ui.components.ValidatedTextField
import cl.duoc.basico.utils.validarEmail
import cl.duoc.basico.utils.validarPassword
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    db: AppDatabase,
    onRegisterSuccess: () -> Unit,
    onBackToLogin: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    var errorNombre by remember { mutableStateOf(false) }
    var errorEmail by remember { mutableStateOf(false) }
    var errorPassword by remember { mutableStateOf(false) }
    var errorConfirmPassword by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro") },
                navigationIcon = {
                    IconButton(onClick = onBackToLogin) { // Vuelta a Login desde la barra.
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
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
            Text(
                "Crea tu cuenta",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            ValidatedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    errorNombre = false
                    errorMessage = "" // Limpia error al tipear.
                },
                label = "Nombre completo",
                isError = errorNombre,
                errorMessage = if (errorNombre) "El nombre es obligatorio" else "",
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                errorMessage = if (errorPassword) "Mínimo 6 caracteres" else "",
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

            Spacer(modifier = Modifier.height(16.dp))

            ValidatedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    errorConfirmPassword = false
                    errorMessage = "" // Limpia error al tipear.
                },
                label = "Confirmar contraseña",
                isError = errorConfirmPassword,
                errorMessage = if (errorConfirmPassword) "Las contraseñas no coinciden" else "",
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) { // Toggle visibilidad.
                        Icon(
                            if (confirmPasswordVisible) Icons.Default.Visibility
                            else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None
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
                        nombre.isBlank() -> {
                            errorNombre = true
                            errorMessage = "El nombre es obligatorio" // Regla requerida.
                        }
                        !validarEmail(email) -> {
                            errorEmail = true
                            errorMessage = "Email inválido" // Validación sintáctica.
                        }
                        !validarPassword(password) -> {
                            errorPassword = true
                            errorMessage = "La contraseña debe tener al menos 6 caracteres" // Regla mínima.
                        }
                        password != confirmPassword -> {
                            errorConfirmPassword = true
                            errorMessage = "Las contraseñas no coinciden" // Consistencia.
                        }
                        else -> {
                            isLoading = true
                            scope.launch {
                                val usuarioExistente = db.usuarioDao().getByEmail(email) // Verifica duplicado.
                                if (usuarioExistente != null) {
                                    isLoading = false
                                    errorEmail = true
                                    errorMessage = "Este email ya está registrado" // Bloquea duplicados.
                                } else {
                                    db.usuarioDao().insert(
                                        Usuario(
                                            nombre = nombre,
                                            email = email,
                                            password = password
                                        )
                                    ) // Inserta usuario.
                                    onRegisterSuccess() // Notifica éxito para navegar.
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading // Previene taps múltiples.
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Registrarse")
                }
            }
        }
    }
}
