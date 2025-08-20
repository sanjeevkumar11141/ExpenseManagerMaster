package com.example.expensemanager.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ExpenseDetailScreen(state: State<ExpenseDetailState>, onBack: () -> Unit) {
    val value = state.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expense Detail", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            value.expense?.let { e ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Title: ${e.title}",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = "Amount: ₹${String.format("%.2f", e.amount)}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4CAF50) // Green for amount
                            )
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Category,
                                contentDescription = "Category",
                                tint = Color(0xFF673AB7)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Category: ${e.category}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = "Date",
                                tint = Color(0xFF2196F3)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Date: ${e.localDate}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            } ?: Text("Not found")

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(0.5f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Back", style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF2196F3)))
            }
        }
    }
}

