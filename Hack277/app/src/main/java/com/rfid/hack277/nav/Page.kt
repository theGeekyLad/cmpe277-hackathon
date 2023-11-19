package com.rfid.hack277.nav


sealed class Page(
    val title: String,
    val route: String
) {
    object corePage: Page(
        "Food Security App",
        "core-page"
    )
}