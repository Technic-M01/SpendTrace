package com.technic.spendtrace.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.technic.spendtrace.utils.Common
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PurchaseViewModel(private val repository: PurchaseRepository): ViewModel() {

    //Repository is separated from UI through ViewModel
    val allPurchases: LiveData<List<PurchaseItem>> = repository.allPurchases.asLiveData()
    val currentMonthPurchases: LiveData<List<PurchaseItem>> = repository.currentMonthPurchases.asLiveData()

    //Launch new coroutine to insert data in non-blocking way
    fun insert(purchase: PurchaseItem) = viewModelScope.launch {
        repository.insert(purchase)
    }

    fun getHistoricalData(pastMonth: String, nextMonth: String): LiveData<List<PurchaseItem>> = repository.fetchHistory(pastMonth, nextMonth)


    fun populate() {
        val sample0 = PurchaseItem(
            0,
            Common.getDateStamp(),
            Common.getCurrentDate(),
            "Test 0",
            10.25,
            false
        )
        insert(sample0)

        val sample1 = PurchaseItem(
            1,
            Common.getDateStamp(),
            Common.getCurrentDate(),
            "Test 1",
            15.68,
            false
        )
        insert(sample1)

        val sample2 = PurchaseItem(
            2,
            202306,
            "06-11-2023",
            "Test 2",
            9.45,
            false
        )
        insert(sample2)

        val sample3 = PurchaseItem(
            3,
            202305,
            "05-03-2023",
            "Test 3",
            125.20,
            false
        )
        insert(sample3)

    }

}

class PurchaseViewModelFactory(private val repository: PurchaseRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PurchaseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PurchaseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}