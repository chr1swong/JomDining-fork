package com.example.jomdining.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
@Entity(
    tableName = "menu_item_ingredient",
    primaryKeys = ["menuItemID", "stockItemID"],
    foreignKeys = [
        // FOREIGN KEY ("menuItemID") REFERENCES "menu"("menuItemID")
        ForeignKey(
            entity = Menu::class,
            parentColumns = ["menuItemID"],
            childColumns = ["menuItemID"]
        ),
        // FOREIGN KEY ("stockItemID") REFERENCES "stock"("stockItemID")
        ForeignKey(
            entity = Stock::class,
            parentColumns = ["stockItemID"],
            childColumns = ["stockItemID"]
        )
    ]
)
data class MenuItemIngredient(
    val menuItemID: Int,
    val stockItemID: Int,
    val ingredientQuantity: Int
)

@ProvidedTypeConverter
class MenuItemIngredientConverter {
    @TypeConverter
    fun stringToMenuItemIngredient(menuItemIngredientJson: String?): MenuItemIngredient? {
        return menuItemIngredientJson?.let {
            Json.decodeFromString(it)
        }
    }

    @TypeConverter
    fun menuItemIngredientToString(menuItemIngredient: MenuItemIngredient?): String {
        return Json.encodeToString(menuItemIngredient)
    }
}