package com.infinity.browser.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.infinity.browser.ui.theme.*

@Composable
fun GlassContainer(
    modifier: Modifier = Modifier,
    cornerRadius: Int = 24,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0x33202028),
                        Color(0x11101014)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x55FFFFFF),
                        Color(0x11FFFFFF)
                    )
                ),
                shape = RoundedCornerShape(cornerRadius.dp)
            ),
        content = content
    )
}

@Composable
fun GlassButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    GlassContainer(modifier = modifier.clickable { onClick() }, cornerRadius = 16) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            content = content
        )
    }
}

@Composable
fun GlassAddressBar(
    url: String,
    onUrlChange: (String) -> Unit,
    onSearch: () -> Unit,
    isLoading: Boolean,
    progress: Int,
    modifier: Modifier = Modifier
) {
    GlassContainer(modifier = modifier.fillMaxWidth(), cornerRadius = 28) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Lock,
                    contentDescription = "Secure",
                    tint = ElectricBlue,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = url,
                    onValueChange = onUrlChange,
                    modifier = Modifier.weight(1.2f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = PureWhite,
                        unfocusedTextColor = SoftSilver
                    ),
                    singleLine = true
                )
                if (isLoading) {
                    CircularProgressIndicator(
                        progress = progress / 100f,
                        modifier = Modifier.size(20.dp),
                        color = ElectricBlue,
                        strokeWidth = 2.dp
                    )
                } else {
                    IconButton(onClick = onSearch) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.Refresh,
                            contentDescription = "Reload",
                            tint = SoftSilver,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            if (isLoading && progress > 0) {
                LinearProgressIndicator(
                    progress = progress / 100f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp),
                    color = ElectricBlue,
                    trackColor = Color.Transparent
                )
            }
        }
    }
}

@Composable
fun GlassBottomNavigation(
    onBack: () -> Unit,
    onForward: () -> Unit,
    onHome: () -> Unit,
    onTabs: () -> Unit,
    onMenu: () -> Unit,
    canGoBack: Boolean,
    canGoForward: Boolean,
    modifier: Modifier = Modifier
) {
    GlassContainer(modifier = modifier.fillMaxWidth(), cornerRadius = 32) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack, enabled = canGoBack) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = if (canGoBack) PureWhite else SoftSilver.copy(alpha = 0.4f)
                )
            }
            IconButton(onClick = onForward, enabled = canGoForward) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.ArrowForward,
                    contentDescription = "Forward",
                    tint = if (canGoForward) PureWhite else SoftSilver.copy(alpha = 0.4f)
                )
            }
            IconButton(onClick = onHome) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Home,
                    contentDescription = "Home",
                    tint = PureWhite
                )
            }
            IconButton(onClick = onTabs) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Tab,
                    contentDescription = "Tabs",
                    tint = PureWhite
                )
            }
            IconButton(onClick = onMenu) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = PureWhite
                )
            }
        }
    }
}

@Composable
fun GlassTabCard(
    title: String,
    url: String,
    isActive: Boolean,
    onClose: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GlassContainer(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable { onClick() },
        cornerRadius = 20
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = PureWhite,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Close,
                        contentDescription = "Close Tab",
                        tint = SoftSilver,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Text(
                text = url,
                color = SoftSilver,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1
            )
        }
    }
}

@Composable
fun GlassSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    GlassContainer(modifier = modifier.fillMaxWidth(), cornerRadius = 32) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.Search,
                contentDescription = "Search",
                tint = ElectricBlue
            )
            Spacer(modifier = Modifier.width(12.dp))
            TextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Search or type URL", color = SoftSilver) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = PureWhite,
                    unfocusedTextColor = PureWhite
                ),
                singleLine = true
            )
            IconButton(onClick = onSearch) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.ArrowForward,
                    contentDescription = "Go",
                    tint = ElectricBlue
                )
            }
        }
    }
}

@Composable
fun GlassSettingsCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GlassContainer(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        cornerRadius = 16
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = ElectricBlue)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, color = PureWhite, style = MaterialTheme.typography.titleMedium)
                Text(text = subtitle, color = SoftSilver, style = MaterialTheme.typography.bodySmall)
            }
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.ChevronRight,
                contentDescription = "Open",
                tint = SoftSilver
            )
        }
    }
}

@Composable
fun GlassShortcutCard(
    title: String,
    url: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    GlassContainer(
        modifier = modifier
            .size(80.dp)
            .clickable { onClick() },
        cornerRadius = 20
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(ElectricBlue.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title.take(1).uppercase(),
                    color = ElectricBlue,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                color = PureWhite,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1
            )
        }
    }
}
