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

@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val alpha = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        alpha.animateTo(1f, animationSpec = tween(600))
    }

    var ipInput by remember { mutableStateOf("") }
    val results by viewModel.results.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .alpha(alpha.value)
    ) {
        Text(
            "🔍 البحث عن IP",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = ipInput,
            onValueChange = { ipInput = it },
            label = { Text("أدخل IPs مفصولة بمسافات") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.searchByIps(ipInput) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("بحث", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(24.dp))

        results?.let { result ->
            if (result.tabqa.isNotEmpty()) {
                ResultSection("📍 نتائج من جدول الطبقة", result.tabqa.map { "${it.name} - ${it.ip}" })
            }

            if (result.raqa.isNotEmpty()) {
                ResultSection("📍 نتائج من جدول الرقة", result.raqa.map { "${it.name} - ${it.ip}" })
            }

            if (result.kobani.isNotEmpty()) {
                ResultSection("📍 نتائج من جدول كوباني", result.kobani.map { "${it.name} - ${it.ip}" })
            }

            if (result.tabqa.isEmpty() && result.raqa.isEmpty() && result.kobani.isEmpty()) {
                Text("❌ لا توجد نتائج", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun ResultSection(title: String, items: List<String>) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(title, style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(4.dp))
    items.forEach { item ->
        Text("🔹 $item", style = MaterialTheme.typography.bodyLarge)
    }
}
