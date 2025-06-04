package com.example.vamz_tison.database

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.vamz_tison.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
object DatabaseInitializer {
    fun initFoodTypes(context: Context, db: AppDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val existing = db.foodTypeDao().getAll().first()
                if (existing.isEmpty()) {
                    db.foodTypeDao().insert(FoodType(name = "Polievka"))
                    db.foodTypeDao().insert(FoodType(name = "Predjedlo"))
                    db.foodTypeDao().insert(FoodType(name = "Hlavné jedlo"))
                    db.foodTypeDao().insert(FoodType(name = "Dezert"))
                    db.foodTypeDao().insert(FoodType(name = "Príloha"))

                    val bitmap =
                        BitmapFactory.decodeResource(
                            context.resources,
                            R.drawable.paradajkovapolievka
                        )
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    val imageBytes = stream.toByteArray()

                    db.foodDao().insert(
                        Food(
                            name = "Talianska paradajková polievka",
                            typeId = 1,
                            cookingTime = 30,
                            preparingTime = 10,
                            portions = 5,
                            calories = 90,
                            description = "Talianska paradajková polievka si zaslúži miesto na stole pre svoju jednoduchosť, výraznú chuť z čerstvých surovín.",
                            image = imageBytes
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = "paradajky drvené",
                            quantity = 400.0,
                            unit = "g"
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = "korenie talianske",
                            quantity = 1.0,
                            unit = "čl"
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = "olej",
                            quantity = 1.0,
                            unit = "PL"
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = "cibuľka jarná",
                            quantity = 1.0,
                            unit = "ks"
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = "voda",
                            quantity = 250.0,
                            unit = "ml"
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = "soľ",
                            quantity = 0.5,
                            unit = "čl"
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = "jogurt biely",
                            quantity = 2.0,
                            unit = "PL"
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = "korenie čierne mleté",
                            quantity = 0.3,
                            unit = "čl"
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = "omáčka sójová",
                            quantity = 1.0,
                            unit = "čl"
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = "vločky ovsené",
                            quantity = 30.0,
                            unit = "g"
                        )
                    )

                    db.processDao().insert(
                        FoodProcess(
                            food = 1,
                            description = "Na horúcom oleji orestujeme nakrájanú cibuľku, potom drvené paradajky. Zalejeme vodou, osolíme, okoreníme talianskym korením, vsypeme vločky a občas premiešavame.",
                            step = 1
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 1,
                            description = "Keď je všetko uvarené, môžeme rozmixovať. Vrátime na oheň a podľa vlastnej chuti pridáme cukor, sójovú omáčku, mleté čierne korenie a necháme prevrieť.",
                            step = 2
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 1,
                            description = "Biely jogurt na zjemnenie si do polievky dáme až na tanieri. Kto chce, môže si nastrúhať aj tvrdý syr.",
                            step = 3
                        )
                    )
                } else {
                    Log.d(
                        "DB_INIT",
                        "FoodType už obsahuje ${existing.size} záznamov – nevkladám nič."
                    )
                }
            } catch (e: Exception) {
                Log.e("DB_INIT", "Chyba pri inicializácii databázy: ${e.message}", e)
            }
        }
    }
}
