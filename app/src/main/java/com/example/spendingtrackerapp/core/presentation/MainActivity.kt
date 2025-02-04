package com.example.spendingtrackerapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spendingtrackerapp.balance.presentation.BalanceScreen
import com.example.spendingtrackerapp.core.presentation.ui.theme.SpendingTrackerAppTheme
import com.example.spendingtrackerapp.core.presentation.util.Background
import com.example.spendingtrackerapp.core.presentation.util.Screen
import com.example.spendingtrackerapp.spending_details.presentation.SpendingDetailsScreenCore
import com.example.spendingtrackerapp.spending_overview.presentation.SpendingOverviewScreenCore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpendingTrackerAppTheme {
                Navigation(modifier = Modifier.fillMaxSize())
            }
        }
    }

    @Composable
    fun Navigation(modifier: Modifier = Modifier) {
        val navController = rememberNavController()

        Background()

        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = Screen.SpendingOverview
        ) {

            composable<Screen.SpendingOverview> {
                SpendingOverviewScreenCore(
                    onBalanceClick = {
                        navController.navigate(Screen.Balance)
                    },
                    onAddSpendingClick = {
                        navController.navigate(Screen.SpendingDetails(-1))
                    }
                )
            }

            composable<Screen.SpendingDetails> {
                SpendingDetailsScreenCore(
                    onSaveSpending = {
                        navController.popBackStack()
                    }
                )
            }

            composable<Screen.Balance> {
                BalanceScreen(
                    onSaveClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}