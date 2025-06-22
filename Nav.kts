sealed class BottomNavItem(val title: String, val icon: ImageVector, val route: String) {
    object Home : BottomNavItem("الرئيسية", Icons.Default.Home, "home")
    object Search : BottomNavItem("بحث", Icons.Default.Search, "search")
    object Settings : BottomNavItem("الإعدادات", Icons.Default.Settings, "settings")
}

@Composable
fun MainScreen() {
    val navController = rememberNavController() // يتحكم في التنقل

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController) // شريط التنقل يظهر هنا
        }
    ) { padding ->
        NavHost(
            navController,
            startDestination = BottomNavItem.Home.route, // أول شاشة تظهر
            Modifier.padding(padding)
        ) {
            composable(BottomNavItem.Home.route) { HomeScreen() }
            composable(BottomNavItem.Search.route) { SearchScreen() }
            composable(BottomNavItem.Settings.route) { SettingsScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Settings
    )
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route)
                    }
                }
            )
        }
    }
}
