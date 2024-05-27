package com.example.jomdining.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow

@Dao
@TypeConverters(
    Account::class,
    Menu::class,
    MenuItemIngredient::class,
    OrderItem::class,
    Stock::class
)
interface TransactionDao {
    // Add a new row to the transaction table
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTransaction(transaction: Transaction)

    @Delete
    suspend fun removeTransaction(transaction: Transaction)

    // THERE IS MORE TO BE ADDED LATER
}