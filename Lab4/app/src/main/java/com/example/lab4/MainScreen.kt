package com.example.lab4

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lab4.ui.theme.TealColor
import com.example.lab4.ui.theme.brownColor
import com.example.lab4.ui.theme.darkBrown
import com.example.lab4.ui.theme.transBrown

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        BottomNavGraph(navController = navController)
    }
}
@Composable
fun BottomBar(navController: NavController){
val screens = listOf(
    BottomBarScreen.Home,
    BottomBarScreen.Profile,
    BottomBarScreen.Settings
)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination=navBackStackEntry?.destination //The destination is always nullable ==>?

    BottomNavigation (
        backgroundColor = transBrown,
        contentColor =  darkBrown
            ){
        screens.forEach{ screen ->
            Additem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
        
    }
}

@Composable
fun RowScope.Additem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavController
){
    BottomNavigationItem(
        label ={
            Text(text = screen.title)
        },
        icon = {
            Icon(imageVector = screen.icon,
                contentDescription = "Nav Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any(){
            it.route == screen.route
        } == true,
//        Decrease the visibility of unselected items
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        //onclick lambda
        onClick = {
            navController.navigate(screen.route){
//                pop to our start destaition which is Home
                popUpTo(navController.graph.findStartDestination().id)
//           Avoid multiple copies of the same destination
                launchSingleTop=true
            }
        }
    )
}



