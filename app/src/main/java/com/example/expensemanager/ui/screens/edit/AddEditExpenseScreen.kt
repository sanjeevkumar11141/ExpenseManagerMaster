package com.example.expensemanager.ui.screens.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.expensemanager.util.Category
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditExpenseScreen(
    state: State<AddEditState>,
    onTitleChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onDateClick: () -> Unit,
    onDismissDate: () -> Unit,
    onConfirmDate: (Long) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    val value by state

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(if (value.id == null) "Add Expense" else "Edit Expense") },
            navigationIcon = {
                IconButton(onClick = onCancel) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            })
    }) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            if (value.error != null) {
                Text(value.error!!, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(8.dp))
            }
            OutlinedTextField(
                value = value.title,
                onValueChange = onTitleChange,
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = value.amount,
                onValueChange = onAmountChange,
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            CategoryPicker(selected = value.category, onSelected = onCategoryChange)
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onDateClick) {
                val formatted = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    .withZone(ZoneId.systemDefault())
                    .format(Instant.ofEpochMilli(value.dateMillis))
                Text("Date: $formatted")
            }
            Spacer(Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp,Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onSave,
                    enabled = value.title.isNotBlank() && value.amount.toDoubleOrNull() ?: 0.0 > 0.0
                ) { Text("Save") }
                OutlinedButton(onClick = onCancel) { Text("Cancel") }
            }
        }
    }

    if (value.showDatePicker) {
        DatePickerDialog(
            onDismissRequest = onDismissDate,
            confirmButton = {
                TextButton(onClick = onDismissDate) { Text("Cancel") }
            },
            dismissButton = {}
        ) {
            val dateState = rememberDatePickerState(value.dateMillis)
            DatePicker(state = dateState)
            LaunchedEffect(dateState.selectedDateMillis) {
                val millis = dateState.selectedDateMillis
                if (millis != null) onConfirmDate(millis)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryPicker(selected: String, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = selected.ifBlank { "Select category" },
            onValueChange = {}, readOnly = true,
            label = { Text("Category") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            Category.all.forEach { c ->
                DropdownMenuItem(
                    text = { Text(c.displayName) },
                    onClick = { onSelected(c.displayName); expanded = false })
            }
        }
    }
}
