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
    Stock::class,
    Transaction::class
)
interface OrderItemDao {
    // Add a new row to the menu_item_ingredient table
    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun addOrderItem(orderItem: OrderItem)

    @Delete
    suspend fun removeOrderItem(orderItem: OrderItem)


    // THIS DOES NOT LOOK RIGHT YET!
    @Query("""
        UPDATE order_item
        SET orderItemQuantity = orderItemQuantity + 1
        WHERE transactionID = :transactionID
        AND menuItemID = :menuItemID
    """)
    fun increaseOrderItemQuantity(transactionID: Transaction, menuItemID: Menu)

    // THIS ALSO DOES NOT LOOK RIGHT YET!
    @Query("""
        UPDATE order_item
        SET orderItemQuantity = orderItemQuantity - 1
        WHERE transactionID = :transactionID
        AND menuItemID = :menuItemID
    """)
    fun decreaseOrderQuantity(transactionID: Transaction, menuItemID: Menu)

    // THIS ALSO DOES NOT LOOK RIGHT YET!
    @Query("""
        SELECT * FROM order_item
        WHERE transactionID = :transactionID
    """)
    fun fetchAllOrderItemsByTransaction(transactionID: String): Flow<List<OrderItem>>
}