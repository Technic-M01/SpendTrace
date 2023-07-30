package com.technic.spendtrace.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.technic.spendtrace.utils.Common
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Database(entities = [PurchaseItem::class], version = 1, exportSchema = false)
abstract class PurchaseDatabase: RoomDatabase() {

    abstract fun PurchaseDao(): PurchaseDao

    companion object {
        @Volatile
        private var INSTANCE: PurchaseDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PurchaseDatabase {
            //If INSTANCE is not null, then return it,
            // if it is, create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PurchaseDatabase::class.java,
                    "PURCHASE_HISTORY"
                )
                    .addCallback(PurchaseDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class PurchaseDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        /*
                        var purchaseDao = database.PurchaseDao()

                        //ToDo: Remove deletion after testing is done
                        purchaseDao.deleteAll()

                        val simpleDate = SimpleDateFormat("MM-dd-yyyy", Locale.US)
                        val currentDate = simpleDate.format(Date())
                        */

                        populateDatabase(database.PurchaseDao())


                    }
                }

            }

        }


        // Seems to run once, on first boot of app.
        suspend fun populateDatabase(purchaseDao: PurchaseDao) {
            purchaseDao.deleteAll()

            val sample0 = PurchaseItem(
                0,
                Common.getDateStamp(),
                Common.getCurrentDate(),
                "Test 0",
                10.25,
                false
            )
            purchaseDao.insert(sample0)

            val sample1 = PurchaseItem(
                1,
                Common.getDateStamp(),
                Common.getCurrentDate(),
                "Test 1",
                15.68,
                false
            )
            purchaseDao.insert(sample1)
        }


    }
}