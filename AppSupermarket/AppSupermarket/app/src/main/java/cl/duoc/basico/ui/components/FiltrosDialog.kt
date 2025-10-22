package cl.duoc.basico.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltrosDialog(
    regiones: List<String>,
    comunas: List<String>,
    regionSeleccionada: String?,
    comunaSeleccionada: String?,
    onRegionSelected: (String) -> Unit,
    onComunaSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    onAplicar: () -> Unit
) {
    var expandedRegion by remember { mutableStateOf(false) }
    var expandedComuna by remember { mutableStateOf(false) }
    var regionTemp by remember { mutableStateOf(regionSeleccionada) }
    var comunaTemp by remember { mutableStateOf(comunaSeleccionada) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Filtrar por ubicación",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Dropdown de Región
                ExposedDropdownMenuBox(
                    expanded = expandedRegion,
                    onExpandedChange = { expandedRegion = !expandedRegion }
                ) {
                    OutlinedTextField(
                        value = regionTemp ?: "Seleccionar región",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Región") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedRegion)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedRegion,
                        onDismissRequest = { expandedRegion = false }
                    ) {
                        regiones.forEach { region ->
                            DropdownMenuItem(
                                text = { Text(region) },
                                onClick = {
                                    regionTemp = region
                                    onRegionSelected(region)
                                    expandedRegion = false
                                }
                            )
                        }
                    }
                }

                // Dropdown de Comuna
                ExposedDropdownMenuBox(
                    expanded = expandedComuna,
                    onExpandedChange = { expandedComuna = !expandedComuna }
                ) {
                    OutlinedTextField(
                        value = comunaTemp ?: "Seleccionar comuna",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Comuna") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedComuna)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = expandedComuna,
                        onDismissRequest = { expandedComuna = false }
                    ) {
                        comunas.forEach { comuna ->
                            DropdownMenuItem(
                                text = { Text(comuna) },
                                onClick = {
                                    comunaTemp = comuna
                                    onComunaSelected(comuna)
                                    expandedComuna = false
                                }
                            )
                        }
                    }
                }

                if (regionTemp != null || comunaTemp != null) {
                    TextButton(
                        onClick = {
                            regionTemp = null
                            comunaTemp = null
                            onRegionSelected("")
                            onComunaSelected("")
                        }
                    ) {
                        Text("Limpiar filtros")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onAplicar()
                    onDismiss()
                }
            ) {
                Text("Aplicar filtros")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
