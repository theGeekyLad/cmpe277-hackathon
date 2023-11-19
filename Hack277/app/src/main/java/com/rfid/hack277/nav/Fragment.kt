package com.rfid.hack277.nav

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.ui.graphics.vector.ImageVector
import com.rfid.hack277.R

sealed class Fragment(
    val title: String,
    val route: String,
    val icon: ImageVector,
    @StringRes val resourceId: Int
) {
    object Macroeconomic: Fragment(
        "Macroeconomic",
        "macroeconomic",
        Icons.Filled.BarChart,
        R.string.bottom_nav_item_macroeconomic)

    object Agriculture: Fragment(
        "Agriculture",
        "agriculture",
        Icons.Filled.Agriculture,
        R.string.bottom_nav_item_agriculture)

    object Trade: Fragment(
        "Trade",
        "trade",
        Icons.Filled.CurrencyExchange,
        R.string.bottom_nav_item_trade)

    object ChatGpt: Fragment(
        "ChatGPT",
        "chatgpt",
        Icons.Filled.Cloud,
        R.string.bottom_nav_item_chatgpt)
}