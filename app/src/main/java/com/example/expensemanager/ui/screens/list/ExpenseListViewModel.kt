package com.example.expensemanager.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensemanager.domain.model.Expense
import com.example.expensemanager.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val query = MutableStateFlow("")
    private val category = MutableStateFlow<String?>(null)

    private val expenses: StateFlow<List<Expense>> =
        combine(query, category) { q, c -> q to c }
            .flatMapLatest { (q, c) -> repository.observeExpenses(q, c) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val state: StateFlow<ExpenseListState> =
        combine(query, category, expenses) { q, c, items ->
            ExpenseListState(
                query = q,
                category = c,
                items = items,
                total = items.sumOf { it.amount }
            )
        }.stateIn(viewModelScope, SharingStarted.Eagerly, ExpenseListState())

    fun onQueryChange(new: String) { query.value = new }
    fun onCategoryChange(new: String?) { category.value = new }

    fun onDelete(expenseId: Long) {
        viewModelScope.launch {
            repository.getExpense(expenseId)?.let { repository.deleteExpense(it) }
        }
    }
}

data class ExpenseListState(
    val query: String = "",
    val category: String? = null,
    val items: List<Expense> = emptyList(),
    val total: Double = 0.0
)
