package com.example.jomdining.data

import com.example.jomdining.daos.MenuDao
import com.example.jomdining.daos.OrderItemDao

class OfflineRepository(
//    private val accountDao: AccountDao,
    private val menuDao: MenuDao,
//    private val menuItemIngredientDao: MenuItemIngredientDao,
    private val orderItemDao: OrderItemDao,
//    private val stockDao: StockDao,
//    private val transactionsDao: TransactionsDao
) : JomDiningRepository {
    /*
        ALL ITEMS UNDER MenuDao
     */
    override fun getAllMenuItems() =
        menuDao.getAllMenuItems()

    /*
        ALL ITEMS UNDER orderItemDao
     */
    override fun getOrderItemByID(transactionID: Int, menuItemID: Int) =
        orderItemDao.getOrderItemByID(transactionID, menuItemID)

    override fun getAllOrderItemsByTransactionID(transactionID: Int) =
        orderItemDao.getAllOrderItemsByTransactionID(transactionID)

    override suspend fun increaseOrderItemQuantity(transactionID: Int, menuItemID: Int) =
        orderItemDao.increaseOrderItemQuantity(transactionID, menuItemID)

    override suspend fun decreaseOrderItemQuantity(transactionID: Int, menuItemID: Int) {
        orderItemDao.decreaseOrderItemQuantity(transactionID, menuItemID)
    }
}
