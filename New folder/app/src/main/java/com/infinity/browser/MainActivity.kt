package com.infinity.browser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.infinity.browser.domain.BrowserTab
import com.infinity.browser.ui.screens.*
import com.infinity.browser.ui.theme.*
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InfinityTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DeepBlack
                ) {
                    val navController = rememberNavController()
                    var currentScreen by remember { mutableStateOf("splash") }

                    var tabs by remember {
                        mutableStateOf(
                            listOf(
                                BrowserTab(id = UUID.randomUUID().toString(), title = "Home", url = "https://www.google.com")
                            )
                        )
                    }
                    var activeTabId by remember { mutableStateOf(tabs.first().id) }

                    NavHost(navController = navController, startDestination = "splash") {
                        composable("splash") {
                            SplashScreen(
                                onSplashFinished = {
                                    navController.navigate("home") {
                                        popUpTo("splash") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("home") {
                            HomeScreen(
                                onNavigate = { url ->
                                    val newTab = BrowserTab(id = UUID.randomUUID().toString(), title = url, url = url)
                                    tabs = tabs + newTab
                                    activeTabId = newTab.id
                                    navController.navigate("browser?url=$url")
                                },
                                onOpenSettings = { navController.navigate("settings") },
                                onOpenHistory = {},
                                onOpenBookmarks = {},
                                onOpenDownloads = {},
                                onOpenTabs = { navController.navigate("tabs") }
                            )
                        }
                        composable("browser?url={url}") { backStackEntry ->
                            val url = backStackEntry.arguments?.getString("url") ?: "https://www.google.com"
                            BrowserScreen(
                                initialUrl = url,
                                onBackToHome = { navController.popBackStack("home", inclusive = false) },
                                onOpenTabs = { navController.navigate("tabs") },
                                onOpenMenu = { navController.navigate("settings") }
                            )
                        }
                        composable("tabs") {
                            TabManagerScreen(
                                tabs = tabs,
                                activeTabId = activeTabId,
                                onTabSelected = { id ->
                                    activeTabId = id
                                    val tab = tabs.find { it.id == id }
                                    val url = tab?.url ?: "https://www.google.com"
                                    navController.navigate("browser?url=$url")
                                },
                                onTabClosed = { id ->
                                    tabs = tabs.filter { it.id != id }
                                    if (tabs.isEmpty()) {
                                        val newTab = BrowserTab(id = UUID.randomUUID().toString(), title = "Home", url = "https://www.google.com")
                                        tabs = listOf(newTab)
                                        activeTabId = newTab.id
                                        navController.navigate("home")
                                    }
                                },
                                onNewTab = {
                                    val newTab = BrowserTab(id = UUID.randomUUID().toString(), title = "Google", url = "https://www.google.com")
                                    tabs = tabs + newTab
                                    activeTabId = newTab.id
                                    navController.navigate("browser?url=https://www.google.com")
                                },
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("settings") {
                            SettingsScreen(
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
