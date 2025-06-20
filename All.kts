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
import kotlinx.coroutines.launch

@Composable
fun AllDataScreen(viewModel: SearchViewModel) {
    val alpha = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        alpha.animateTo(1f, animationSpec = tween(600))
    }

    val scope = rememberCoroutineScope()
    var tabqaList by remember { mutableStateOf(emptyList<com.example.tasks.model.Tabqa>()) }
    var raqaList by remember { mutableStateOf(emptyList<com.example.tasks.model.Raqa>()) }
    var kobaniList by remember { mutableStateOf(emptyList<com.example.tasks.model.Kobani>()) }

    LaunchedEffect(Unit) {
        scope.launch {
            tabqaList = viewModel.getAllTabqa()
            raqaList = viewModel.getAllRaqa()
            kobaniList = viewModel.getAllKobani()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .alpha(alpha.value)
    ) {
        Text(
            "📊 عرض جميع البيانات",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        SectionList(title = "📍 الطبقة", items = tabqaList.map { "${it.name} - ${it.ip}" })
        SectionList(title = "📍 الرقة", items = raqaList.map { "${it.name} - ${it.ip}" })
        SectionList(title = "📍 كوباني", items = kobaniList.map { "${it.name} - ${it.ip}" })
    }
}

@Composable
fun SectionList(title: String, items: List<String>) {
    if (items.isNotEmpty()) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        items.forEach { item ->
            Text("🔹 $item", style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
