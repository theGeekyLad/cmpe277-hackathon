package com.rfid.hack277.nav

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.navArgument
import com.rfid.hack277.ui.Category
import com.rfid.hack277.ui.Option
import com.rfid.hack277.ui.categoryMap

sealed class Chart(
    val route: String,
    val arguments: List<NamedNavArgument>
) {
    object Macroeconomic : Chart(
        "macroeconomic-chart/{category}?${getOptionsList(Category.MACROECONOMIC)}",
        getNavArgsList(Category.MACROECONOMIC)
    )
    object Agriculture : Chart(
        "agriculture-chart/{category}?${getOptionsList(Category.AGRICULTURE)}",
        getNavArgsList(Category.AGRICULTURE)
    )
    object Trade : Chart(
        "trade-chart/{category}?${getOptionsList(Category.TRADE)}",
        getNavArgsList(Category.TRADE)
    )
//    object ChatGpt : Chart("chatgpt-chart/{category}")
}

private fun getOptionsList(category: Category): String =
    categoryMap[category]!!.keys.joinToString(
        "&"
    ) { "${it.name}={${it.name}}" }

private fun getNavArgsList(category: Category): List<NamedNavArgument> =
    categoryMap[category]!!.keys.map {
        navArgument(it.name) { defaultValue = "" }
    }

fun getOptionKeySetForCategory(category: Category) = categoryMap[category]!!.keys