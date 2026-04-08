package com.example.planyourevent.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.planyourevent.ui.screen.AddEditEventScreen
import com.example.planyourevent.ui.screen.EventListScreen
import com.example.planyourevent.viewmodel.EventViewModel

sealed class Screen(val route: String) { // A sealed class is a restricted class hierarchy, Only a fixed set of Subclasses are allowed
    // In simple words, sealed class = A class where ALL possible types are known in advance
    object EventList : Screen("event_list")
    object AddEdit : Screen("add_edit?eventId={eventId}") {

        fun createRoute(eventId: Int = -1): String {
            return "add_edit?eventId=$eventId"
        }
    }
} // over here we are creating a list of all screens in app, "Screen = All possible destinations in app"
// it is a central place that defines all screens

@Composable
fun AppNavGraph(viewModel: EventViewModel) { // it is the map of our APP NAVIGATION
    // It defines, FROM where → TO where → HOW to move

    val navController = rememberNavController() // it is the navigator of our app = DRIVER
    // handles movement between screens

    NavHost(
        navController = navController,
        startDestination = Screen.EventList.route // first screen of our app
    ) { // it is a container that holds all screens
        composable(Screen.EventList.route) {
            EventListScreen(
                viewModel = viewModel,
                onAddClick = {
                    navController.navigate(Screen.AddEdit.createRoute()) // ADD mode
                },
                onItemClick = { eventId ->
                    navController.navigate(
                        Screen.AddEdit.createRoute(eventId) // EDIT mode
                    )
                }
            )
        } // If route = event_list → show this UI

        composable(
            route = "add_edit?eventId={eventId}",
            arguments = listOf(
                navArgument("eventId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->

            val eventId = backStackEntry.arguments?.getInt("eventId") ?: -1

            AddEditEventScreen(
                viewModel = viewModel,
                eventId = eventId,
                onSave = { navController.popBackStack() }
            )
        }
    }
}