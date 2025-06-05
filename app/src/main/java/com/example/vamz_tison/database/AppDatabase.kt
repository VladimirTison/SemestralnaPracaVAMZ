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
    version = 10,
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

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "vamz_tison_db_v2"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                instance.openHelper.writableDatabase
                INSTANCE = instance
                instance
            }
        }
    }
}
