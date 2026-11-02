package com.infinity.browser.ui.screens

import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.infinity.browser.ui.components.*
import com.infinity.browser.ui.theme.*

@Composable
fun BrowserScreen(
    initialUrl: String,
    onBackToHome: () -> Unit,
    onOpenTabs: () -> Unit,
    onOpenMenu: () -> Unit
) {
    var currentUrl by remember { mutableStateOf(initialUrl) }
    var urlInput by remember { mutableStateOf(initialUrl) }
    var isLoading by remember { mutableStateOf(false) }
    var progress by remember { mutableIntStateOf(0) }
    var canGoBack by remember { mutableStateOf(false) }
    var canGoForward by remember { mutableStateOf(false) }
    var webViewInstance by remember { mutableStateOf<WebView?>(null) }
    var showToolbars by remember { mutableStateOf(true) }
    var lastScrollY by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlack)
    ) {
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.builtInZoomControls = true
                    settings.displayZoomControls = false

                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            url?.let {
                                currentUrl = it
                                urlInput = it
                            }
                            isLoading = true
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading = false
                            canGoBack = canGoBack()
                            canGoForward = canGoForward()
                        }
                    }

                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            super.onProgressChanged(view, newProgress)
                            progress = newProgress
                        }
                    }

                    setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                        val diff = scrollY - oldScrollY
                        if (diff > 10 && scrollY > 50) {
                            showToolbars = false
                        } else if (diff < -10 || scrollY < 20) {
                            showToolbars = true
                        }
                        lastScrollY = scrollY
                    }

                    loadUrl(initialUrl)
                    webViewInstance = this
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = if (showToolbars) 70.dp else 0.dp, bottom = if (showToolbars) 80.dp else 0.dp)
        )

        // Top Address Bar
        AnimatedVisibility(
            visible = showToolbars,
            enter = slideInVertically(initialOffsetY = { -it }, animationSpec = tween(300)),
            exit = slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(300)),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        ) {
            GlassAddressBar(
                url = urlInput,
                onUrlChange = { urlInput = it },
                onSearch = {
                    val target = if (urlInput.startsWith("http://") || urlInput.startsWith("https://")) {
                        urlInput
                    } else {
                        "https://www.google.com/search?q=$urlInput"
                    }
                    webViewInstance?.loadUrl(target)
                },
                isLoading = isLoading,
                progress = progress
            )
        }

        // Bottom Navigation Bar
        AnimatedVisibility(
            visible = showToolbars,
            enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(300)),
            exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(300)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            GlassBottomNavigation(
                onBack = { webViewInstance?.goBack() },
                onForward = { webViewInstance?.goForward() },
                onHome = onBackToHome,
                onTabs = onOpenTabs,
                onMenu = onOpenMenu,
                canGoBack = canGoBack,
                canGoForward = canGoForward
            )
        }
    }
}
