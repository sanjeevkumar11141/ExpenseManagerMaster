package com.example.expensemanager.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.expensemanager.domain.model.Expense
import com.example.expensemanager.util.Category
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListScreen(
    state: State<ExpenseListState>,
    onQueryChange: (String) -> Unit,
    onCategoryChange: (String?) -> Unit,
    onAddClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    onEditClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit,
) {
    val value by state

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Expenses") }, actions = {
                TextButton(onClick = onAddClick) { Text("Add") }
            })
        },
        floatingActionButton = { FloatingActionButton(onClick = onAddClick) { Text("+") } }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            Text(text = "Total: ₹${String.format("%.2f", value.total)}", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = value.query,
                onValueChange = onQueryChange,
                label = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            CategoryFilter(selected = value.category, onSelected = onCategoryChange)
            Spacer(Modifier.height(8.dp))

            if (value.items.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("No expenses yet")
                }
            } else {
                LazyColumn {
                    items(value.items) { e ->
                        ExpenseRow(
                            expense = e,
                            onClick = { onItemClick(e.id) },
                            onEdit = { onEditClick(e.id) },
                            onDelete = { onDeleteClick(e.id) }
                        )
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CategoryFilter(selected: String?, onSelected: (String?) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = selected ?: "All",
            onValueChange = {},
            readOnly = true,
            label = { Text("Category") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("All") }, onClick = { onSelected(null); expanded = false })
            Category.all.forEach { c ->
                DropdownMenuItem(text = { Text(c.displayName) }, onClick = { onSelected(c.displayName); expanded = false })
            }
        }
    }
}

@Composable
private fun ExpenseRow(
    expense: Expense,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.weight(1f)) {
            Text(expense.title, style = MaterialTheme.typography.titleMedium)
            Text("${expense.category} • ${expense.localDate}")
        }
        Column(horizontalAlignment = Alignment.End) {
            Text("₹${String.format("%.2f", expense.amount)}")
            Row {
                TextButton(onClick = { showEditDialog = true }) { Text("Edit") }
                TextButton(onClick = { showDeleteDialog = true }) { Text("Delete") }
            }
        }
    }

    // Reusable dialogs
    ConfirmDialog(
        show = showEditDialog,
        onDismiss = { showEditDialog = false },
        title = "Edit Expense",
        message = "Are you sure you want to edit this expense?",
        confirmText = "Yes",
        onConfirm = onEdit
    )

    ConfirmDialog(
        show = showDeleteDialog,
        onDismiss = { showDeleteDialog = false },
        title = "Delete Expense",
        message = "Are you sure you want to delete this expense?",
        confirmText = "Delete",
        onConfirm = onDelete
    )
}



@Composable
fun ConfirmDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    title: String,
    message: String,
    confirmText: String,
    onConfirm: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(title) },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = {
                    onDismiss()
                    onConfirm()
                }) { Text(confirmText) }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) { Text("Cancel") }
            }
        )
    }
}

