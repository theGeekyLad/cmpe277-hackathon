package com.rfid.hack277

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlagCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rfid.hack277.model.DataViewModel
import com.rfid.hack277.nav.Chart
import com.rfid.hack277.nav.Fragment
import com.rfid.hack277.nav.Page
import com.rfid.hack277.nav.getOptionKeySetForCategory
import com.rfid.hack277.ui.Category
import com.rfid.hack277.ui.ChartPage
import com.rfid.hack277.ui.CorePage
import com.rfid.hack277.ui.LandingPage
import com.rfid.hack277.ui.categoryMap
import com.rfid.hack277.ui.theme.Hack277Theme

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    val dataViewModel: DataViewModel by viewModels()

    private val navFragments = listOf(
        Fragment.Macroeconomic,
        Fragment.Agriculture,
        Fragment.Trade,
        Fragment.ChatGpt,
    )

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Hack277Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val appName = stringResource(id = R.string.app_name)

                    val navController = rememberNavController()
                    val currentBackStackEntry by navController.currentBackStackEntryAsState()
                    var pageTitle by remember { mutableStateOf("") }
                    var refreshBit by remember { mutableIntStateOf(0) }

                    var showCountrySelectionAlertDialog by remember { mutableStateOf(false) }

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TopAppBar(
                            title = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.logo_vector),
                                        contentDescription = ""
                                    )

                                    Column {
                                        Text(
                                            text =
                                            pageTitle.ifEmpty { appName },
                                            color = Color.White
                                        )

                                        if (dataViewModel.country.value.isNotEmpty())
                                            Text(
                                                text = dataViewModel.country.value,
                                                color = Color.White,
                                                fontSize = TextUnit(12f, TextUnitType.Sp)
                                            )
                                    }
                                }
                            },
                            backgroundColor = MaterialTheme.colorScheme.primary,
                            actions = {
                                IconButton(onClick = { showCountrySelectionAlertDialog = true }) {
                                    Icon(
                                        imageVector = Icons.Filled.FlagCircle,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }

                                if (currentBackStackEntry?.destination?.route?.indexOf("-chart") != -1)
                                    IconButton(onClick = { refreshBit = (refreshBit + 1) % 2 }) {
                                        Icon(
                                            imageVector = Icons.Filled.Refresh,
                                            contentDescription = null,
                                            tint = Color.White
                                        )
                                    }
                            }
                        )

                        Scaffold(
                            bottomBar = {
                                BottomNavigation(
                                    backgroundColor = MaterialTheme.colorScheme.primary,
                                ) {
                                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                                    val currentDestination = navBackStackEntry?.destination

                                    BottomNavigationItem(
                                        alwaysShowLabel = false,
                                        icon = {
                                            Icon(
                                                navFragments[0].icon,
                                                contentDescription = null,
                                                tint = Color.White
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = navFragments[0].title,
                                                color = Color.White,
                                                fontSize = TextUnit(14f, TextUnitType.Sp),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        },
                                        selected = currentDestination?.hierarchy?.any { it.route == navFragments[0].route } == true,
                                        onClick = {
                                            pageTitle = navFragments[0].title

                                            navController.navigate(navFragments[0].route) {
                                                // Pop up to the start destination of the graph to
                                                // avoid building up a large stack of destinations
                                                // on the back stack as users select items
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                // Avoid multiple copies of the same destination when
                                                // reselecting the same item
                                                launchSingleTop = true
                                                // Restore state when reselecting a previously selected item
                                                restoreState = true
                                            }
                                        }
                                    )

                                    BottomNavigationItem(
                                        alwaysShowLabel = false,
                                        icon = {
                                            Icon(
                                                navFragments[1].icon,
                                                contentDescription = null,
                                                tint = Color.White
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = navFragments[1].title,
                                                color = Color.White,
                                                fontSize = TextUnit(14f, TextUnitType.Sp),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        },
                                        selected = currentDestination?.hierarchy?.any { it.route == navFragments[1].route } == true,
                                        onClick = {
                                            pageTitle = navFragments[1].title

                                            navController.navigate(navFragments[1].route) {
                                                // Pop up to the start destination of the graph to
                                                // avoid building up a large stack of destinations
                                                // on the back stack as users select items
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                // Avoid multiple copies of the same destination when
                                                // reselecting the same item
                                                launchSingleTop = true
                                                // Restore state when reselecting a previously selected item
                                                restoreState = true
                                            }
                                        }
                                    )

                                    BottomNavigationItem(
                                        alwaysShowLabel = false,
                                        icon = {
                                            Icon(
                                                navFragments[2].icon,
                                                contentDescription = null,
                                                tint = Color.White
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = navFragments[2].title,
                                                color = Color.White,
                                                fontSize = TextUnit(14f, TextUnitType.Sp),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        },
                                        selected = currentDestination?.hierarchy?.any { it.route == navFragments[2].route } == true,
                                        onClick = {
                                            pageTitle = navFragments[2].title

                                            navController.navigate(navFragments[2].route) {
                                                // Pop up to the start destination of the graph to
                                                // avoid building up a large stack of destinations
                                                // on the back stack as users select items
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                // Avoid multiple copies of the same destination when
                                                // reselecting the same item
                                                launchSingleTop = true
                                                // Restore state when reselecting a previously selected item
                                                restoreState = true
                                            }
                                        }
                                    )

                                    BottomNavigationItem(
                                        alwaysShowLabel = false,
                                        icon = {
                                            Icon(
                                                navFragments[3].icon,
                                                contentDescription = null,
                                                tint = Color.White
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = navFragments[3].title,
                                                color = Color.White,
                                                fontSize = TextUnit(14f, TextUnitType.Sp),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        },
                                        selected = currentDestination?.hierarchy?.any { it.route == navFragments[3].route } == true,
                                        onClick = {
                                            pageTitle = navFragments[3].title

                                            navController.navigate(navFragments[3].route) {
                                                // Pop up to the start destination of the graph to
                                                // avoid building up a large stack of destinations
                                                // on the back stack as users select items
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                // Avoid multiple copies of the same destination when
                                                // reselecting the same item
                                                launchSingleTop = true
                                                // Restore state when reselecting a previously selected item
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        ) { innerPadding ->
                            NavHost(
                                navController = navController,
                                startDestination = Page.corePage.route,
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                composable(Page.corePage.route) {
                                    CorePage(
                                        viewModel = dataViewModel
                                    )
                                }
                                composable(Fragment.Macroeconomic.route) {
                                    LandingPage(
                                        navController = navController,
                                        category = Category.MACROECONOMIC,
                                        title = Fragment.Macroeconomic.title,
                                        options = categoryMap[Category.MACROECONOMIC]!!
                                    )
                                }
                                composable(
                                    route = Chart.Macroeconomic.route,
                                    arguments = Chart.Macroeconomic.arguments
                                ) { backStackEntry ->
                                    ChartPage(
                                        viewModel = dataViewModel,
                                        category = Category.valueOf(
                                            backStackEntry.arguments!!.getString(
                                                "category"
                                            )!!
                                        ),
                                        choices = getOptionKeySetForCategory(Category.MACROECONOMIC).associateWith { option ->
                                            backStackEntry.arguments!!.getString(option.name)!!
                                                .isNotEmpty()
                                        }
                                    )
                                }
                                composable(Fragment.Agriculture.route) {
                                    LandingPage(
                                        navController = navController,
                                        category = Category.AGRICULTURE,
                                        title = Fragment.Agriculture.title,
                                        options = categoryMap[Category.AGRICULTURE]!!
                                    )
                                }
                                composable(
                                    route = Chart.Agriculture.route,
                                    arguments = Chart.Agriculture.arguments
                                ) { backStackEntry ->
                                    ChartPage(
                                        viewModel = dataViewModel,
                                        category = Category.valueOf(
                                            backStackEntry.arguments!!.getString(
                                                "category"
                                            )!!
                                        ),
                                        choices = getOptionKeySetForCategory(Category.AGRICULTURE).associateWith { option ->
                                            backStackEntry.arguments!!.getString(option.name)!!
                                                .isNotEmpty()
                                        }
                                    )
                                }
                                composable(Fragment.Trade.route) {
                                    LandingPage(
                                        navController = navController,
                                        category = Category.TRADE,
                                        title = Fragment.Trade.title,
                                        options = categoryMap[Category.TRADE]!!
                                    )
                                }
                                composable(
                                    route = Chart.Trade.route,
                                    arguments = Chart.Trade.arguments
                                ) { backStackEntry ->
                                    ChartPage(
                                        viewModel = dataViewModel,
                                        category = Category.valueOf(
                                            backStackEntry.arguments!!.getString(
                                                "category"
                                            )!!
                                        ),
                                        choices = getOptionKeySetForCategory(Category.TRADE).associateWith { option ->
                                            backStackEntry.arguments!!.getString(option.name)!!
                                                .isNotEmpty()
                                        }
                                    )
                                }
                                composable(Fragment.ChatGpt.route) {
                                    Text("HelloWorld!")
                                }
                            }
                        }
                    }

                    if (showCountrySelectionAlertDialog)
                        AlertDialog(
                            onDismissRequest = {
                                showCountrySelectionAlertDialog = false
                            }
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.background
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(
                                        8.dp,
                                        Alignment.CenterVertically
                                    )
                                ) {
                                    ClickableText(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = AnnotatedString("India"),
                                        onClick = {
                                            dataViewModel.country.value = "India"
                                            showCountrySelectionAlertDialog = false
                                        }
                                    )
                                    Box(
                                        Modifier
                                            .height(1.dp)
                                            .fillMaxWidth()
                                            .background(Color.LightGray)
                                    )
                                    ClickableText(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = AnnotatedString("China"),
                                        onClick = {
                                            dataViewModel.country.value = "China"
                                            showCountrySelectionAlertDialog = false
                                        }
                                    )
                                    Box(
                                        Modifier
                                            .height(1.dp)
                                            .fillMaxWidth()
                                            .background(Color.LightGray)
                                    )
                                    ClickableText(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = AnnotatedString("Burma"),
                                        onClick = {
                                            dataViewModel.country.value = "Burma"
                                            showCountrySelectionAlertDialog = false
                                        }
                                    )
                                }
                            }
                        }

                    if (dataViewModel.showProgress.value)
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = Color(1f,1f,1f,0.5f)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Hack277Theme {
        Greeting("Android")
    }
}