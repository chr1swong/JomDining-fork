package com.example.jomdining.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface StockDao {
    // Add a new row to the stock table
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStock(stock: Stock)

    // Remove a row from the stock table
    @Delete
    suspend fun removeStock(stock: Stock)

    // Fetch all rows of stock
    @Query("SELECT * FROM stock ORDER BY stockItemID")
    fun getAllStock(): Flow<List<Stock>>

    @Query("""
        UPDATE stock
        SET stockItemQuantity = :newStockItemQuantity
        WHERE stockItemID = :stockItemID
    """)
    fun updateStockItemQuantity(newStockItemQuantity: Int, stockItemID: Int)
}