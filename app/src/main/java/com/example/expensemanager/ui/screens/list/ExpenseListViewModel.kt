package com.example.expensemanager.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensemanager.domain.model.Expense
import com.example.expensemanager.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExpenseListState())
    val state: StateFlow<ExpenseListState> = _state.asStateFlow()

    init {
        loadExpenses()
    }

    fun onQueryChange(newQuery: String) {
        _state.value = _state.value.copy(query = newQuery)
        loadExpenses()
    }

    fun onCategoryChange(newCategory: String?) {
        _state.value = _state.value.copy(category = newCategory)
        loadExpenses()
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            val currentState = _state.value
            repository.observeExpenses(currentState.query, currentState.category)
                .collect { expenses ->
                    _state.value = currentState.copy(
                        items = expenses,
                        total = expenses.sumOf { it.amount }
                    )
                }
        }
    }

    fun onDelete(expenseId: Long) {
        viewModelScope.launch {
            repository.getExpense(expenseId)?.let { expense ->
                repository.deleteExpense(expense)
                // Expenses will be automatically updated through the Flow
            }
        }
    }
}

data class ExpenseListState(
    val query: String = "",
    val category: String? = null,
    val items: List<Expense> = emptyList(),
    val total: Double = 0.0
)
