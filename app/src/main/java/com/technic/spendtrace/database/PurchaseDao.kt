package com.technic.spendtrace.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PurchaseDao {

    @Query("SELECT * FROM PURCHASE_HISTORY")
    fun getAll(): Flow<List<PurchaseItem>>

    @Query("SELECT * FROM PURCHASE_HISTORY WHERE purchaseDate >= :previousMonth AND purchaseDate < :nextMonth")
    fun getCurrentMonthEntries(previousMonth: String, nextMonth: String): Flow<List<PurchaseItem>>

    @Query("SELECT * FROM PURCHASE_HISTORY WHERE purchaseDate >= :previousMonth AND purchaseDate < :nextMonth")
    fun getHistoricalMonth(previousMonth: String, nextMonth: String): LiveData<List<PurchaseItem>>

/*    @Query("SELECT DISTINCT dateStamp FROM PURCHASE_HISTORY")
    fun getDatestamps(): List<Int>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(Purchases: List<PurchaseItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(purchaseItem: PurchaseItem)

    @Delete
    suspend fun delete(Purchase: PurchaseItem)

    @Query("DELETE FROM PURCHASE_HISTORY")
    suspend fun deleteAll()

}