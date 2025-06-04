package com.example.vamz_tison.database

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

object DatabaseInitializer {
    fun initFoodTypes(db: AppDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            val existing = db.foodTypeDao().getAll().first()
            if (existing.isEmpty()) {
                Log.d("DB_INIT", "Tabuľka FoodType je prázdna – vkladám predvolené dáta.")
                db.foodTypeDao().insert(FoodType(name = "Polievka"))
                db.foodTypeDao().insert(FoodType(name = "Predjedlo"))
                db.foodTypeDao().insert(FoodType(name = "Hlavné jedlo"))
                db.foodTypeDao().insert(FoodType(name = "Dezert"))
                db.foodTypeDao().insert(FoodType(name = "Príloha"))
            } else {
                Log.d("DB_INIT", "FoodType už obsahuje ${existing.size} záznamov – nevkladám nič.")
            }
        }
    }
}
