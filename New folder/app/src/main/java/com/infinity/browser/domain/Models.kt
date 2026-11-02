package com.infinity.browser.domain

import android.webkit.WebView

data class BrowserTab(
    val id: String,
    val title: String = "New Tab",
    val url: String = "",
    val originalUrl: String = "",
    val progress: Int = 0,
    val isLoading: Boolean = false,
    val canGoBack: Boolean = false,
    val canGoForward: Boolean = false,
    val webView: WebView? = null,
    val isIncognito: Boolean = false,
    val favicon: android.graphics.Bitmap? = null
)

data class Bookmark(
    val id: Long,
    val title: String,
    val url: String,
    val faviconUrl: String?,
    val timestamp: Long
)

data class HistoryItem(
    val id: Long,
    val title: String,
    val url: String,
    val timestamp: Long
)

data class DownloadItem(
    val id: Long,
    val fileName: String,
    val fileUri: String,
    val url: String,
    val fileSize: Long,
    val status: String,
    val timestamp: Long
)
