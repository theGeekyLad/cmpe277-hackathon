package com.rfid.hack277.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.marker.markerComponent
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.marker.Marker
import com.rfid.hack277.charting.rememberMarker
import com.rfid.hack277.model.DataViewModel

@ExperimentalMaterial3Api
@Composable
fun ChartPage(
    viewModel: DataViewModel,
    category: Category,
    choices: Map<Option, Boolean>
) {
    var startYear by remember { mutableStateOf("") }
    var endYear by remember { mutableStateOf("") }
    var mustAnnotate by remember { mutableStateOf(false) }

    val chartEntryModel = entryModelOf(4f, 12f, 8f, 16f)

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        Row(
            modifier = Modifier.weight(1f, true),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f, true),
                label = { Text("Start Year") },
                value = startYear,
                onValueChange = { startYear = it }
            )

            OutlinedTextField(
                modifier = Modifier.weight(1f, true),
                label = { Text("End Year") },
                value = endYear,
                onValueChange = { endYear = it }
            )
        }

        Column(
            modifier = Modifier.weight(3f, true),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Chart(
                modifier = Modifier.fillMaxSize(),
                chart = lineChart(),
                model = chartEntryModel,
                startAxis =
                if (mustAnnotate && viewModel.isPersonaElevated.value)
                    rememberStartAxis()
                else null,
                bottomAxis =
                if (mustAnnotate && viewModel.isPersonaElevated.value)
                    rememberBottomAxis()
                else null,
                marker =
                if (mustAnnotate && viewModel.isPersonaElevated.value)
                    rememberMarker()
                else null
            )
        }

        Column(
            modifier = Modifier.weight(1f, true),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Button(
                onClick = {
                    mustAnnotate = true
                },
                enabled = viewModel.isPersonaElevated.value
            ) {
                Text(text = "Annotate")
            }
        }
    }
}