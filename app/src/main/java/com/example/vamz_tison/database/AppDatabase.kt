package com.example.vamz_tison.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        FoodType::class,
        Food::class,
        FoodStuff::class,
        FoodProcess::class,
        ShoppingList::class,
        ListItems::class,
        FavoriteFood::class
    ],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun foodTypeDao(): FoodTypeDao
    abstract fun foodDao(): FoodDao
    abstract fun foodStuffDao(): FoodStuffDao
    abstract fun processDao(): ProcessDao
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun listItemsDao(): ListItemsDao
    abstract fun favoriteFoodDao(): FavoriteFoodDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val callback = object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        // Spustíme insert iba pri vytvorení databázy
                        CoroutineScope(Dispatchers.IO).launch {
                            INSTANCE?.let { insertInitialData(it) }
                        }
                    }
                }

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vamz_tison_db_v2" // zmenený názov databázy = čistý štart
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build()

                instance.openHelper.writableDatabase
                INSTANCE = instance
                instance
            }
        }

        private suspend fun insertInitialData(db: AppDatabase) {
            // Preddefinované dáta
            db.foodTypeDao().insert(FoodType(name = "Polievka"))
            db.foodTypeDao().insert(FoodType(name = "Predjedlo"))
            db.foodTypeDao().insert(FoodType(name = "Hlavné jedlo"))
            db.foodTypeDao().insert(FoodType(name = "Dezert"))
            db.foodTypeDao().insert(FoodType(name = "Príloha"))
        }
    }
}
