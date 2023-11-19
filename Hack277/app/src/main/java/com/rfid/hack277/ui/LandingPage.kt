package com.rfid.hack277.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions

enum class Category {
    MACROECONOMIC, AGRICULTURE, TRADE
}

enum class Option {
    GDP, FDI_INFLOWS, FDI_OUTFLOWS, IMPORT_EXPORT_FLOW, CONTRIBUTION_TO_GDP,
    CREDIT, FERTILIZERS, FERTILIZERS_PRODUCTION, RESERVES, GNI, TOTAL_DEBT, GNI_CURRENT
}

data class LandingPageData(
    val category: Category,
    val categoryList: List<String>,
)

val categoryMap = mapOf(
    Pair(
        Category.MACROECONOMIC,
        mapOf(
            Pair(Option.GDP, "GDP (USD)"),
            Pair(Option.FDI_INFLOWS, "FDI Inflows (USD)"),
            Pair(Option.FDI_OUTFLOWS, "FDI Outflows (USD)"),
            Pair(Option.IMPORT_EXPORT_FLOW, "Import/Export Flow"),
        )
    ),
    Pair(
        Category.AGRICULTURE,
        mapOf(
            Pair(Option.CONTRIBUTION_TO_GDP, "Contribution to GDP"),
            Pair(Option.CREDIT, "Credit"),
            Pair(Option.FERTILIZERS, "Fertilizers"),
            Pair(Option.FERTILIZERS_PRODUCTION, "Fertilizer Production"),
        )
    ),
    Pair(
        Category.TRADE,
        mapOf(
            Pair(Option.RESERVES, "Reserves"),
            Pair(Option.GNI, "GNI"),
            Pair(Option.TOTAL_DEBT, "Total Debt"),
            Pair(Option.GNI_CURRENT, "GNI (current US$)"),
        )
    ),
)

@Composable
fun LandingPage(
    navController: NavController,
    category: Category,
    title: String,
    options: Map<Option, String>
) {
    val checkedStates = remember { options.map { mutableStateOf(false) } }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(40.dp, 0.dp).weight(3f, true),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        ) {
            Text(
                text = title,
                fontSize = TextUnit(20f, TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            )
            options.values.forEachIndexed { i, option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = checkedStates[i].value,
                        onCheckedChange = { checkedStates[i].value = it }
                    )
                    Text(text = option)
                }
            }
        }

        Column(
            modifier = Modifier.padding(40.dp, 0.dp).weight(1f, true),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        ) {
            Button(
                onClick = {
                    navController.apply {
                        navigate(navController.currentBackStackEntry!!.destination.route + "-chart/$category")
                    }
                }
            ) {
                Text(text = "Show")
            }
        }
    }
}