package com.example.tasks.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.tasks.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteScreen(viewModel: SearchViewModel) {
    val alpha = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        alpha.animateTo(1f, animationSpec = tween(600))
    }

    var ipToDelete by remember { mutableStateOf("") }
    var selectedTable by remember { mutableStateOf("الطبقة") }
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("الطبقة", "الرقة", "كوباني")
    var showMessage by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .alpha(alpha.value)
    ) {
        Text(
            "🗑️ حذف IP من جدول",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = ipToDelete,
            onValueChange = { ipToDelete = it },
            label = { Text("IP المراد حذفه") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedTable,
                onValueChange = {},
                readOnly = true,
                label = { Text("اختر الجدول") },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedTable = option
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (ipToDelete.isNotBlank()) {
                    viewModel.deleteByIp(ipToDelete.trim(), selectedTable)
                    showMessage = true
                    ipToDelete = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("حذف", style = MaterialTheme.typography.titleMedium)
        }

        if (showMessage) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("✅ تم الحذف (إن وُجد)", color = MaterialTheme.colorScheme.secondary)
        }
    }
}
