package com.example.expensemanager.ui.screens.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensemanager.domain.model.Expense
import com.example.expensemanager.domain.repository.ExpenseRepository
import com.example.expensemanager.ui.navigation.NavRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            loadExpenseForEdit()
        } else {
            // Set today's date for new expense
            _state.value = _state.value.copy(dateMillis = todayMillis())
        }
    }

    private fun loadExpenseForEdit() {
        viewModelScope.launch {
            expenseId?.let { id ->
                repository.getExpense(id)?.let { expense ->
                    _state.value = _state.value.copy(
                        id = expense.id,
                        title = expense.title,
                        amount = expense.amount.toString(),
                        category = expense.category,
                        dateMillis = expense.dateMillis
                    )
                }
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _state.value = _state.value.copy(title = newTitle)
    }

    fun onAmountChange(newAmount: String) {
        _state.value = _state.value.copy(amount = newAmount)
    }

    fun onCategoryChange(newCategory: String) {
        _state.value = _state.value.copy(category = newCategory)
    }

    fun onDateClick() {
        _state.value = _state.value.copy(showDatePicker = true)
    }

    fun onDismissDate() {
        _state.value = _state.value.copy(showDatePicker = false)
    }

    fun onConfirmDate(millis: Long) {
        _state.value = _state.value.copy(
            dateMillis = millis,
            showDatePicker = false
        )
    }

    fun onSave(onDone: () -> Unit) {
        val currentState = _state.value
        val validationErrors = validateInput(currentState)

        if (validationErrors.isNotEmpty()) {
            _state.value = currentState.copy(error = validationErrors.joinToString("\n"))
            return
        }

        // Clear any previous errors
        _state.value = currentState.copy(error = null)

        viewModelScope.launch {
            val expense = createExpenseFromState(currentState)
            
            if (currentState.id == null) {
                repository.addExpense(expense)
            } else {
                repository.updateExpense(expense)
            }
            
            onDone()
        }
    }

    private fun validateInput(state: AddEditState): List<String> {
        val errors = mutableListOf<String>()
        
        if (state.title.isBlank()) {
            errors.add("Title cannot be empty")
        }
        
        val amountDouble = state.amount.toDoubleOrNull()
        if (amountDouble == null || amountDouble <= 0.0) {
            errors.add("Amount must be > 0")
        }
        
        if (state.category.isBlank()) {
            errors.add("Category is required")
        }
        
        return errors
    }

    private fun createExpenseFromState(state: AddEditState): Expense {
        return Expense(
            id = state.id ?: 0L,
            title = state.title.trim(),
            amount = state.amount.toDouble(),
            category = state.category,
            dateMillis = state.dateMillis
        )
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
