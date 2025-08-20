package com.example.expensemanager.domain.model

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

data class Expense(
    val id: Long = 0L,
    val title: String,
    val amount: Double,
    val category: String,
    val dateMillis: Long
) {
    val localDate: LocalDate
        get() = Instant.ofEpochMilli(dateMillis).atZone(ZoneId.systemDefault()).toLocalDate()
}
