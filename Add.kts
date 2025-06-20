package com.example.tasks.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenu
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.tasks.model.Kobani
import com.example.tasks.model.Raqa
import com.example.tasks.model.Tabqa
import com.example.tasks.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(viewModel: SearchViewModel) {
    val alpha = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        alpha.animateTo(1f, animationSpec = tween(600))
    }

    var name by remember { mutableStateOf("") }
    var ip by remember { mutableStateOf("") }
    var selectedTable by remember { mutableStateOf("الطبقة") }
    val options = listOf("الطبقة", "الرقة", "كوباني")
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .alpha(alpha.value),
    ) {
        Text(
            "➕ إضافة موقع جديد",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("الاسم") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = ip,
            onValueChange = { ip = it },
            label = { Text("IP") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors()
        )

        Spacer(modifier = Modifier.height(12.dp))

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
                if (name.isNotBlank() && ip.isNotBlank()) {
                    when (selectedTable) {
                        "الطبقة" -> viewModel.insertTabqa(Tabqa(name = name, ip = ip))
                        "الرقة" -> viewModel.insertRaqa(Raqa(name = name, ip = ip))
                        "كوباني" -> viewModel.insertKobani(Kobani(name = name, ip = ip))
                    }
                    name = ""
                    ip = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("إضافة", style = MaterialTheme.typography.titleMedium)
        }
    }
}
