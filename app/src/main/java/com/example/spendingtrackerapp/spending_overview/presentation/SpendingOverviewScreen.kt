package com.example.spendingtrackerapp.spending_overview.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spendingtrackerapp.core.presentation.ui.theme.SpendingTrackerAppTheme
import com.example.spendingtrackerapp.core.presentation.ui.theme.montserrat
import com.example.spendingtrackerapp.core.presentation.util.Background
import org.koin.androidx.compose.koinViewModel

@Composable
fun SpendingOverviewScreenCore(
    viewModel: SpendingOverviewViewModel = koinViewModel(),
    onBalanceClick: () -> Unit,
    onAddSpendingClick: () -> Unit,
) {

    LaunchedEffect(true) {
        viewModel.onAction(SpendingOverviewAction.LoadSpendingOverviewAndBalance)
    }

    SpendingOverviewScreen(
        state = viewModel.state,
        onActon = viewModel::onAction,
        onBalanceClick = { onBalanceClick() },
        onAddSpendingClick = {},
        onDeleteSpendingClick = {
            viewModel.onAction(SpendingOverviewAction.OnDeleteSpending(it))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SpendingOverviewScreen(
    state: SpendingOverviewState,
    onActon: (SpendingOverviewAction) -> Unit,
    onBalanceClick: () -> Unit,
    onAddSpendingClick: () -> Unit,
    onDeleteSpendingClick: (Int) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = rememberTopAppBarState()
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    onClick = onAddSpendingClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Spending"
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        },
        topBar = {
            SpendingOverviewTopBar(
                modifier = Modifier.fillMaxWidth(),
                scrollBehavior = scrollBehavior,
                balance = state.balance,
                onBalanceClick = onBalanceClick
            )
        }
    ) { padding ->
        Background()

        Column(
            modifier = Modifier.padding(padding)
        ) { }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpendingOverviewTopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    balance: Double,
    onBalanceClick: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "$${balance}",
                fontSize = 35.sp,
                maxLines = 1,
                fontFamily = montserrat,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.primary,
                )
        },
        modifier = modifier.padding(start = 12.dp, end = 16.dp),
        actions = {
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary.copy(0.6f),
                        shape = RoundedCornerShape(13.dp)
                    )
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(0.3f))
                    .clickable { onBalanceClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$",
                    fontSize = 26.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    )

}

@Preview
@Composable
fun SpendingOverviewScreenPreview() {
    SpendingTrackerAppTheme {
        SpendingOverviewScreen(
            state = SpendingOverviewState(),
            onActon = {},
            onBalanceClick = {},
            onAddSpendingClick = {},
            onDeleteSpendingClick = {}
        )
    }
}