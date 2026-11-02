package com.infinity.browser.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.infinity.browser.ui.components.*
import com.infinity.browser.ui.theme.*

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    onOpenSettings: () -> Unit,
    onOpenHistory: () -> Unit,
    onOpenBookmarks: () -> Unit,
    onOpenDownloads: () -> Unit,
    onOpenTabs: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val shortcuts = listOf(
        Pair("Google", "https://www.google.com"),
        Pair("GitHub", "https://github.com"),
        Pair("Wikipedia", "https://www.wikipedia.org"),
        Pair("YouTube", "https://www.youtube.com"),
        Pair("Reddit", "https://www.reddit.com"),
        Pair("Twitter", "https://twitter.com")
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(DarkCharcoal, DeepBlack)
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "INFINITY",
                color = PureWhite,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 6.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            GlassSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = {
                    if (searchQuery.isNotBlank()) {
                        val url = if (searchQuery.startsWith("http://") || searchQuery.startsWith("https://")) {
                            searchQuery
                        } else if (searchQuery.contains(".") && !searchQuery.contains(" ")) {
                            "https://$searchQuery"
                        } else {
                            "https://www.google.com/search?q=$searchQuery"
                        }
                        onNavigate(url)
                    }
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Shortcuts",
                color = SoftSilver,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(shortcuts) { shortcut ->
                    GlassShortcutCard(
                        title = shortcut.first,
                        url = shortcut.second,
                        onClick = { onNavigate(shortcut.second) }
                    )
                }
            }
        }
    }
}
