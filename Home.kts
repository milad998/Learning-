package com.example.tasks.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tasks.navigation.NavRoutes

@Composable
fun HomeScreen(navController: NavController) {
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(1f, animationSpec = tween(durationMillis = 600))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .alpha(alpha.value),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "🌐 نظام إدارة المواقع الشبكية",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "• إضافة مواقع مخصصة حسب المنطقة\n" +
                "• البحث عن الأجهزة عبر IP\n" +
                "• الحذف والعرض بسهولة",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(32.dp))

            NavigationButton("➕ إضافة موقع") {
                navController.navigate(NavRoutes.Add)
            }
            NavigationButton("🔍 بحث IP") {
                navController.navigate(NavRoutes.Search)
            }
            NavigationButton("🗑️ حذف IP") {
                navController.navigate(NavRoutes.Delete)
            }
            NavigationButton("📋 عرض الكل") {
                navController.navigate(NavRoutes.AllData)
            }
        }
    }
}

@Composable
fun NavigationButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(text, style = MaterialTheme.typography.titleMedium)
    }
}
