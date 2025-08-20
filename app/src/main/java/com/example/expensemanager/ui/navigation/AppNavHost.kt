package com.example.expensemanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensemanager.ui.screens.detail.ExpenseDetailScreen
import com.example.expensemanager.ui.screens.detail.ExpenseDetailViewModel
import com.example.expensemanager.ui.screens.edit.AddEditExpenseScreen
import com.example.expensemanager.ui.screens.edit.AddEditExpenseViewModel
import com.example.expensemanager.ui.screens.list.ExpenseListScreen
import com.example.expensemanager.ui.screens.list.ExpenseListViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.LIST
    ) {
        composable(NavRoutes.LIST) {
            val vm: ExpenseListViewModel = hiltViewModel()
            val listState = vm.state.collectAsStateWithLifecycle()
            ExpenseListScreen(
                state = listState,
                onQueryChange = vm::onQueryChange,
                onCategoryChange = vm::onCategoryChange,
                onAddClick = { navController.navigate(NavRoutes.addEditRoute()) },
                onItemClick = { id -> navController.navigate(NavRoutes.detailRoute(id)) },
                onEditClick = { id -> navController.navigate(NavRoutes.addEditRoute(id)) },
                onDeleteClick = vm::onDelete
            )
        }

        composable(
            route = "${NavRoutes.ADD_EDIT}?${NavRoutes.ARG_EXPENSE_ID}={${NavRoutes.ARG_EXPENSE_ID}}",
            arguments = listOf(
                navArgument(NavRoutes.ARG_EXPENSE_ID) {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val vm: AddEditExpenseViewModel = hiltViewModel()
            val editState = vm.state.collectAsStateWithLifecycle()
            AddEditExpenseScreen(
                state = editState,
                onTitleChange = vm::onTitleChange,
                onAmountChange = vm::onAmountChange,
                onCategoryChange = vm::onCategoryChange,
                onDateClick = vm::onDateClick,
                onDismissDate = vm::onDismissDate,
                onConfirmDate = vm::onConfirmDate,
                onSave = {
                    vm.onSave { navController.popBackStack() }
                },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(
            route = "${NavRoutes.DETAIL}/{${NavRoutes.ARG_EXPENSE_ID}}",
            arguments = listOf(navArgument(NavRoutes.ARG_EXPENSE_ID) { type = NavType.LongType })
        ) {
            val vm: ExpenseDetailViewModel = hiltViewModel()
            val detailState = vm.state.collectAsStateWithLifecycle()
            ExpenseDetailScreen(state = detailState) { navController.popBackStack() }
        }
    }
}
