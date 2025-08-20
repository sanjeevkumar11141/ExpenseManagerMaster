package com.example.expensemanager.di

import android.content.Context
import androidx.room.Room
import com.example.expensemanager.data.local.AppDatabase
import com.example.expensemanager.data.local.ExpenseDao
import com.example.expensemanager.data.repository.ExpenseRepositoryImpl
import com.example.expensemanager.domain.repository.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "expense_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideExpenseDao(db: AppDatabase): ExpenseDao = db.expenseDao()

    @Provides
    @Singleton
    fun provideExpenseRepository(dao: ExpenseDao): ExpenseRepository =
        ExpenseRepositoryImpl(dao)

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
