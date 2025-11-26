package cl.duoc.basico.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun rememberCameraPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
): () -> Unit {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            onPermissionGranted() // Callback en concesión del permiso.
        } else {
            onPermissionDenied() // Callback en denegación del permiso.
        }
    }

    return {
        val permissionCheckResult = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) // Chequeo previo para evitar pedir permiso si ya está concedido.

        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted() // Evita relanzar el diálogo si ya está otorgado.
        } else {
            launcher.launch(Manifest.permission.CAMERA) // Lanza diálogo de permiso de cámara.
        }
    }
}

@Composable
fun rememberLocationPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
): () -> Unit {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.values.all { it }
        if (granted) {
            onPermissionGranted() // Todas las ubicaciones concedidas.
        } else {
            onPermissionDenied() // Alguna ubicación fue denegada.
        }
    }

    return {
        val fineLocationGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarseLocationGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (fineLocationGranted && coarseLocationGranted) {
            onPermissionGranted() // Evita pedir si ya están ambas.
        } else {
            launcher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) // Solicita permisos de ubicación en lote.
        }
    }
}

fun Context.hasCameraPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED // Helper para consultar permiso de cámara.
}

fun Context.hasLocationPermission(): Boolean {
    val fineLocation = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    val coarseLocation = ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    return fineLocation && coarseLocation // Helper para saber si ambas ubicaciones están concedidas.
}
