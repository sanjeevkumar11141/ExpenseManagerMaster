package com.example.expensemanager.data.repository

import com.example.expensemanager.data.local.ExpenseDao
import com.example.expensemanager.data.mapper.toDomain
import com.example.expensemanager.data.mapper.toEntity
import com.example.expensemanager.domain.model.Expense
import com.example.expensemanager.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val dao: ExpenseDao
) : ExpenseRepository {

    override fun observeExpenses(query: String, category: String?): Flow<List<Expense>> =
        dao.observeExpenses(query = query, category = category).map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun getExpense(id: Long): Expense? = dao.getById(id)?.toDomain()

    override suspend fun addExpense(expense: Expense): Long = dao.insert(expense.toEntity())

    override suspend fun updateExpense(expense: Expense) = dao.update(expense.toEntity())

    override suspend fun deleteExpense(expense: Expense) = dao.delete(expense.toEntity())
}
