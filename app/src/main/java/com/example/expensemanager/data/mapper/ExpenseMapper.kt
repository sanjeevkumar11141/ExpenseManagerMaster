package com.example.expensemanager.data.mapper

import com.example.expensemanager.data.local.ExpenseEntity
import com.example.expensemanager.domain.model.Expense

fun ExpenseEntity.toDomain(): Expense =
    Expense(
        id = id,
        title = title,
        amount = amount,
        category = category,
        dateMillis = dateMillis
    )

fun Expense.toEntity(): ExpenseEntity =
    ExpenseEntity(
        id = id,
        title = title,
        amount = amount,
        category = category,
        dateMillis = dateMillis
    )
