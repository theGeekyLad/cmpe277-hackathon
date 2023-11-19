package com.rfid.hack277.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.rfid.hack277.model.DataViewModel

@Composable
fun CorePage(
    viewModel: DataViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to Macroeconomic Food Security App",
                fontSize = TextUnit(24f, TextUnitType.Sp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f, true),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(
                    modifier = Modifier.fillMaxHeight(),
                    onClick = {
                        viewModel.isPersonaElevated.value = true
                    }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)) {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                            )
                        }

                        Text(
                            text = "Macroeconomic Researcher",
                            fontSize = TextUnit(20f, TextUnitType.Sp),
                            textAlign = TextAlign.Center,
                            fontWeight =
                            if (viewModel.isPersonaElevated.value)
                                FontWeight.Black
                            else
                                FontWeight.Normal
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.weight(1f, true),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(
                    modifier = Modifier.fillMaxHeight(),
                    onClick = {
                        viewModel.isPersonaElevated.value = false
                    }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                        ) {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                            )
                        }

                        Text(
                            text = "Government Official",
                            fontSize = TextUnit(20f, TextUnitType.Sp),
                            textAlign = TextAlign.Center,
                            fontWeight =
                            if (!viewModel.isPersonaElevated.value)
                                FontWeight.Black
                            else
                                FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}