package com.example.lab4

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab4.screens.HomeScreen
import com.example.lab4.screens.ProfileScreen
import com.example.lab4.screens.SettingsScreen

@Composable
fun BottomNavGraph(navController: NavHostController){
NavHost(
    navController = navController,
    startDestination = BottomBarScreen.Home.route
){
    composable(route = BottomBarScreen.Home.route){
        HomeScreen()
    }
    composable(route = BottomBarScreen.Profile.route){
        ProfileScreen()
    }
    composable(route = BottomBarScreen.Settings.route){
        SettingsScreen()
    }
}
}