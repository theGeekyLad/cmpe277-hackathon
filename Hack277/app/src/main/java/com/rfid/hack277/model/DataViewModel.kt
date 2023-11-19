package com.rfid.hack277.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class DataViewModel: ViewModel() {
    val isPersonaElevated = mutableStateOf(false)
    val country = mutableStateOf("")
    val showProgress = mutableStateOf(false)
}