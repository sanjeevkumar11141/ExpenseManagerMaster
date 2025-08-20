package com.example.expensemanager.domain.repository

import com.example.expensemanager.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun observeExpenses(query: String, category: String?): Flow<List<Expense>>
    suspend fun getExpense(id: Long): Expense?
    suspend fun addExpense(expense: Expense): Long
    suspend fun updateExpense(expense: Expense)
    suspend fun deleteExpense(expense: Expense)
}
