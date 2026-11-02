package com.infinity.browser.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.default.Add
import androidx.compose.material.icons.default.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.infinity.browser.domain.BrowserTab
import com.infinity.browser.ui.components.GlassTabCard
import com.infinity.browser.ui.theme.*

@Composable
fun TabManagerScreen(
    tabs: List<BrowserTab>,
    activeTabId: String,
    onTabSelected: (String) -> Unit,
    onTabClosed: (String) -> Unit,
    onNewTab: () -> Unit,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(DarkCharcoal, DeepBlack)
                )
            )
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = PureWhite)
                }
                Text(
                    text = "Tabs (${tabs.size})",
                    color = PureWhite,
                    style = MaterialTheme.typography.titleLarge
                )
                IconButton(onClick = onNewTab) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "New Tab", tint = ElectricBlue)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(tabs) { tab ->
                    GlassTabCard(
                        title = tab.title,
                        url = tab.url,
                        isActive = tab.id == activeTabId,
                        onClose = { onTabClosed(tab.id) },
                        onClick = { onTabSelected(tab.id) }
                    )
                }
            }
        }
    }
}
