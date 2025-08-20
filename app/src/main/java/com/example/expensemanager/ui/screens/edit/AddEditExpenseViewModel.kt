package com.example.expensemanager.ui.screens.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensemanager.domain.model.Expense
import com.example.expensemanager.domain.repository.ExpenseRepository
import com.example.expensemanager.ui.navigation.NavRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class AddEditExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val expenseId: Long? = savedStateHandle.get<Long>(NavRoutes.ARG_EXPENSE_ID)?.takeIf { it != -1L }

    private val _state = MutableStateFlow(AddEditState())
    val state: StateFlow<AddEditState> = _state.asStateFlow()

    init {
        if (expenseId != null) {
            viewModelScope.launch {
                repository.getExpense(expenseId)?.let { e ->
                    _state.update {
                        it.copy(
                            id = e.id,
                            title = e.title,
                            amount = e.amount.toString(),
                            category = e.category,
                            dateMillis = e.dateMillis
                        )
                    }
                }
            }
        } else {
            _state.update { it.copy(dateMillis = todayMillis()) }
        }
    }

    fun onTitleChange(new: String) { _state.update { it.copy(title = new) } }
    fun onAmountChange(new: String) { _state.update { it.copy(amount = new) } }
    fun onCategoryChange(new: String) { _state.update { it.copy(category = new) } }

    fun onDateClick() { _state.update { it.copy(showDatePicker = true) } }
    fun onDismissDate() { _state.update { it.copy(showDatePicker = false) } }
    fun onConfirmDate(millis: Long) { _state.update { it.copy(dateMillis = millis, showDatePicker = false) } }

    fun onSave(onDone: () -> Unit) {
        val current = _state.value
        val amountDouble = current.amount.toDoubleOrNull()
        val errors = mutableListOf<String>()
        if (current.title.isBlank()) errors.add("Title cannot be empty")
        if (amountDouble == null || amountDouble <= 0.0) errors.add("Amount must be > 0")
        if (current.category.isBlank()) errors.add("Category is required")

        if (errors.isNotEmpty()) {
            _state.update { it.copy(error = errors.joinToString("\n")) }
            return
        }

        viewModelScope.launch {
            val expense = Expense(
                id = current.id ?: 0L,
                title = current.title.trim(),
                amount = amountDouble!!,
                category = current.category,
                dateMillis = current.dateMillis
            )
            if (current.id == null) repository.addExpense(expense) else repository.updateExpense(expense)
            onDone()
        }
    }

    private fun todayMillis(): Long =
        LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

data class AddEditState(
    val id: Long? = null,
    val title: String = "",
    val amount: String = "",
    val category: String = "",
    val dateMillis: Long = 0L,
    val showDatePicker: Boolean = false,
    val error: String? = null
)
