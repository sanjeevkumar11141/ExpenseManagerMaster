package com.example.expensemanager.ui.screens.detail

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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val repository: ExpenseRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(ExpenseDetailState())
    val state: StateFlow<ExpenseDetailState> = _state.asStateFlow()

    init {
        val id: Long = checkNotNull(savedStateHandle[NavRoutes.ARG_EXPENSE_ID])
        viewModelScope.launch {
            repository.getExpense(id)?.let { expense ->
                _state.update { ExpenseDetailState(expense = expense) }
            }
        }
    }
}

data class ExpenseDetailState(val expense: Expense? = null)
