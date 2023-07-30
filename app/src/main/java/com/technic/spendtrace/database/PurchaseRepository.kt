package com.technic.spendtrace.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.technic.spendtrace.utils.Common
import kotlinx.coroutines.flow.Flow

class PurchaseRepository(private val purchaseDao: PurchaseDao) {
    val allPurchases: Flow<List<PurchaseItem>> = purchaseDao.getAll()

    val currentMonthPurchases: Flow<List<PurchaseItem>> = purchaseDao.getCurrentMonthEntries(Common.getCurrentDate(), Common.determineNextMonth())

    var historicalData: MutableList<PurchaseItem> = emptyList<PurchaseItem>().toMutableList()

//    val dateStamps: List<Int> = purchaseDao.getDatestamps()

    @WorkerThread
    suspend fun insert(purchase: PurchaseItem) {
        purchaseDao.insert(purchase)
    }

    fun fetchHistory(pastMonth: String, nextMonth: String): LiveData<List<PurchaseItem>> {
        return purchaseDao.getHistoricalMonth(pastMonth, nextMonth)
    }

}