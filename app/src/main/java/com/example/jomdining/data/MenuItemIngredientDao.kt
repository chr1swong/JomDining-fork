package com.example.jomdining.data

import android.view.MenuItem
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
    OrderItem::class,
    Stock::class,
    Transaction::class
)
interface MenuItemIngredientDao {
    // Add a new row to the menu_item_ingredient table
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMenuItemIngredient(menuItemIngredient: MenuItemIngredient)

    @Delete
    suspend fun removeMenuItemIngredient(menuItemIngredient: MenuItemIngredient)

    // THIS NEEDS TO BE MODIFIED IN THE FUTURE. IT CURRENTLY PULLS DATA FOR ALL ORDERS!
    @Query("SELECT * FROM menu_item_ingredient ORDER BY menuItemID")
    fun getAllMenuItemIngredients(): Flow<List<MenuItemIngredient>>

    // THIS ALSO DOES NOT LOOK RIGHT!
    @Query("SELECT * FROM menu_item_ingredient WHERE menuItemID = :menuItemID")
    fun getIngredientsForSingleMenuItem(menuItemID: Menu): Flow<List<MenuItemIngredient>>
}