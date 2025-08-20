package com.example.expensemanager.ui.navigation

object NavRoutes {
    const val LIST = "list"
    const val ADD_EDIT = "add_edit"
    const val DETAIL = "detail"

    const val ARG_EXPENSE_ID = "expenseId"

    fun addEditRoute(expenseId: Long? = null): String =
        if (expenseId == null) ADD_EDIT else "$ADD_EDIT?$ARG_EXPENSE_ID=$expenseId"

    fun detailRoute(expenseId: Long): String = "$DETAIL/$expenseId"
}
