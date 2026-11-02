package com.infinity.browser.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.default.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.infinity.browser.ui.components.GlassSettingsCard
import com.infinity.browser.ui.theme.*

@Composable
fun SettingsScreen(
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = PureWhite)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Settings",
                    color = PureWhite,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    GlassSettingsCard(
                        title = "Appearance",
                        subtitle = "Liquid Glass effects, theme, wallpapers",
                        icon = Icons.Default.Palette,
                        onClick = {}
                    )
                }
                item {
                    GlassSettingsCard(
                        title = "Privacy & Security",
                        subtitle = "Ad blocking, tracking protection, clear data",
                        icon = Icons.Default.Security,
                        onClick = {}
                    )
                }
                item {
                    GlassSettingsCard(
                        title = "Downloads",
                        subtitle = "Download location, history",
                        icon = Icons.Default.Download,
                        onClick = {}
                    )
                }
                item {
                    GlassSettingsCard(
                        title = "About INFINITY",
                        subtitle = "Version 1.0.0, open-source licenses",
                        icon = Icons.Default.Info,
                        onClick = {}
                    )
                }
            }
        }
    }
}
