package com.technic.spendtrace.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PURCHASE_HISTORY")
data class PurchaseItem(
    @PrimaryKey val entryID: Int,
    @ColumnInfo(name = "dateStamp") val stamp: Int,
    @ColumnInfo(name = "purchaseDate") val date: String,
    @ColumnInfo(name = "purchaseVendor") val vendor: String,
    @ColumnInfo(name = "purchasePrice") val price: Double,
    @ColumnInfo(name = "recurringPurchase") val recurring: Boolean?
)
