@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.spendingtrackerapp.spending_details.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spendingtrackerapp.core.presentation.ui.theme.montserrat
import com.example.spendingtrackerapp.core.presentation.util.Background
import org.koin.androidx.compose.koinViewModel

@Composable
fun SpendingDetailsScreenCore(
    viewModel: SpendingDetailsViewModel = koinViewModel(),
    onSaveSpending: () -> Unit
) {

    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                SpendingDetailsEvent.SaveFailed -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }

                SpendingDetailsEvent.SaveSuccess -> onSaveSpending()
            }
        }
    }

    SpendingDetailsScreen(
        state = viewModel.state,
        onActions = viewModel::onAction
    )

}

@Composable
fun SpendingDetailsScreen(
    state: SpendingDetailsState,
    onActions: (SpendingDetailsActions) -> Unit
) {

    Scaffold(
        modifier = Modifier.padding(start = 8.dp, end = 16.dp),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = "Add Spending",
                        fontFamily = montserrat,
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
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
                            .clickable {
                                onActions(SpendingDetailsActions.SaveSpending)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save spending",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Background()

        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            OutlinedTextField(
                value = state.name,
                onValueChange = {
                    onActions(SpendingDetailsActions.UpdateName(it))
                },
                label = {
                    Text(text = "Name", fontWeight = FontWeight.Medium)
                },
                textStyle = TextStyle(
                    fontFamily = montserrat,
                    fontSize = 17.sp
                ),
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = state.price.toString(),
                onValueChange = {
                    onActions(
                        SpendingDetailsActions.UpdatePrice(
                            it.toDoubleOrNull() ?: 0.0
                        )
                    )
                },
                label = {
                    Text(text = "Price", fontWeight = FontWeight.Medium)
                },
                textStyle = TextStyle(
                    fontFamily = montserrat,
                    fontSize = 17.sp
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = state.kilograms.toString(),
                    onValueChange = {
                        onActions(
                            SpendingDetailsActions.UpdateKilograms(
                                it.toDoubleOrNull() ?: 0.0
                            )
                        )
                    },
                    label = {
                        Text(text = "Kilograms", fontWeight = FontWeight.Medium)
                    },
                    textStyle = TextStyle(
                        fontFamily = montserrat,
                        fontSize = 17.sp
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = state.quantity.toString(),
                    onValueChange = {
                        onActions(
                            SpendingDetailsActions.UpdateQuantity(
                                it.toDoubleOrNull() ?: 0.0
                            )
                        )
                    },
                    label = {
                        Text(text = "Quantity", fontWeight = FontWeight.Medium)
                    },
                    textStyle = TextStyle(
                        fontFamily = montserrat,
                        fontSize = 17.sp
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    modifier = Modifier.weight(1f)
                )
            }

        }
    }

}







