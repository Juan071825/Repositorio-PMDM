package com.example.appnavegate

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appnavegate.ui.FirstScreen
import com.example.appnavegate.ui.LastScreen
import com.example.appnavegate.ui.NavigateViewModel
import com.example.appnavegate.ui.SecondScreen
import com.example.appnavegate.ui.StartScreen

import kotlin.Unit;

enum class NavigateScreen(@StringRes val title: Int){
    Start(title = R.string.title),
    First(title = R.string.first),
    Second(title = R.string.second),
    Last(title = R.string.last)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigateBar(
    currentScreen: NavigateScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = {Text(stringResource(currentScreen.title))},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        }
    )
}


@Composable
fun AppNavigate(
    viewModel: NavigateViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = NavigateScreen.valueOf(
        backStackEntry?.destination?.route ?: NavigateScreen.Start.name
    )

    Scaffold(
        topBar = {
            AppNavigateBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    navController.popBackStack()
                    viewModel.onScreenChanged()
                }
            )
        }
    ) {innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = NavigateScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = NavigateScreen.Start.name) {
                StartScreen(
                    counter = uiState.counter,
                    onNextButtonClicked = {
                        navController.navigate(NavigateScreen.First.name)
                        viewModel.onScreenChanged()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                )
            }

            composable(route = NavigateScreen.First.name) {
                FirstScreen(
                    counter = uiState.counter,
                    onNextButtonClicked = {
                        navController.navigate(NavigateScreen.Second.name)
                        viewModel.onScreenChanged()
                    },
                    onBackButtonClicked = {
                        navController.popBackStack()
                        viewModel.onScreenChanged()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                )
            }

            composable(route = NavigateScreen.Second.name) {
                SecondScreen(
                    counter = uiState.counter,
                    onNextButtonClicked = {
                        navController.navigate(NavigateScreen.Last.name)
                        viewModel.onScreenChanged()
                    },
                    onBackButtonClicked = {
                        navController.popBackStack()
                        viewModel.onScreenChanged()
                    },
                )
            }
            composable(route = NavigateScreen.Last.name) {
                LastScreen(
                    counter = uiState.counter,
                    onBackButtonClicked = {
                        navController.popBackStack()
                        viewModel.onScreenChanged()
                    },
                )
            }
        }
    }
}


/*@Preview(showBackground = true)
@Composable
fun AppNavigateBarPreview() {
    AppNavegateTheme {
        AppNavigateBar(NavigateScreen.Start, true)
    }
} */
