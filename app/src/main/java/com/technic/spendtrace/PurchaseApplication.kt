package com.technic.spendtrace

import android.app.Application
import com.technic.spendtrace.database.PurchaseDatabase
import com.technic.spendtrace.database.PurchaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PurchaseApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { PurchaseDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { PurchaseRepository(database.PurchaseDao()) }
}