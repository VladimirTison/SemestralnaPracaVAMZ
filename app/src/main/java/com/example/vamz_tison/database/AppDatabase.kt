package com.example.vamz_tison.database

import android.content.Context
import androidx.room.*
import com.example.vamz_tison.R

/**
 * Hlavná Room databáza aplikácie.
 *
 * @property foodTypeDao DAO pre typy jedál.
 * @property foodDao DAO pre jedlá.
 * @property foodStuffDao DAO pre suroviny.
 * @property processDao DAO pre kroky postupu.
 * @property shoppingListDao DAO pre nákupné zoznamy.
 * @property listItemsDao DAO pre položky v nákupnom zozname.
 * @property favoriteFoodDao DAO pre obľúbené jedlá.
 */
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
    version = 11,
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
        /**
         * Vráti singleton inštanciu databázy.
         * Ak ešte neexistuje, vytvorí sa nová.
         *
         * @param context Kontext aplikácie.
         * @return Inštancia databázy.
         */
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    context.getString(R.string.databaseName)
                )
                    .build()
                instance.openHelper.writableDatabase
                INSTANCE = instance
                instance
            }
        }
    }
}
