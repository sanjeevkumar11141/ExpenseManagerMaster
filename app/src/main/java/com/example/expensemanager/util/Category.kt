package com.example.expensemanager.util

enum class Category(val displayName: String) {
    FOOD("Food"),
    TRAVEL("Travel"),
    SHOPPING("Shopping"),
    BILLS("Bills"),
    OTHER("Other");

    companion object {
        val all = entries
        fun fromDisplay(value: String?): Category = entries.firstOrNull { it.displayName == value } ?: OTHER
    }
}
