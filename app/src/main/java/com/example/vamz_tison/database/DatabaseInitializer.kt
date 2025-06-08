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
object DatabaseInitializer {

    fun initFoodTypes(context: Context, db: AppDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val existing = db.foodTypeDao().getAll().first()
                if (existing.isEmpty()) {
                    db.foodTypeDao().insert(FoodType(name = context.getString(R.string.polievka)))
                    db.foodTypeDao().insert(FoodType(name = context.getString(R.string.predjedlo)))
                    db.foodTypeDao().insert(FoodType(name = context.getString(R.string.hlavn_jedlo)))
                    db.foodTypeDao().insert(FoodType(name = context.getString(R.string.dezert)))
                    db.foodTypeDao().insert(FoodType(name = context.getString(R.string.pr_loha)))
                    db.shoppingListDao().insert(ShoppingList(name = context.getString(R.string.n_kupn_zoznam)))

                    //Paradajková polievka id = 1
                    val bitmap = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.paradajkovapolievka
                    )

                    var quality = 90
                    var byteArray: ByteArray

                    do {
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
                        byteArray = stream.toByteArray()
                        quality -= 5
                    } while (byteArray.size > 500_000 && quality > 10)


                    db.foodDao().insert(
                        Food(
                            id = 1,
                            name = context.getString(R.string.talianska_paradajkov_polievka),
                            typeId = 1,
                            cookingTime = 30,
                            preparingTime = 10,
                            portions = 5,
                            calories = 90,
                            description = context.getString(R.string.parpolievka),
                            image = byteArray
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = context.getString(R.string.paradajkovapolievkaingredience1),
                            quantity = 400.0,
                            unit = context.getString(R.string.unit_gram)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = context.getString(R.string.paradajkovapolievkaingredience2),
                            quantity = 1.0,
                            unit = context.getString(R.string.unit_teaspoon)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = context.getString(R.string.paradajkovapolievkaingredience3),
                            quantity = 1.0,
                            unit = context.getString(R.string.unit_tablespoon)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = context.getString(R.string.paradajkovapolievkaingredience4),
                            quantity = 1.0,
                            unit = context.getString(R.string.unit_piece)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = context.getString(R.string.paradajkovapolievkaingredience5),
                            quantity = 250.0,
                            unit = context.getString(R.string.unit_milliliter)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = context.getString(R.string.paradajkovapolievkaingredience6),
                            quantity = 0.5,
                            unit = context.getString(R.string.unit_teaspoon)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = context.getString(R.string.paradajkovapolievkaingredience7),
                            quantity = 2.0,
                            unit = context.getString(R.string.unit_tablespoon)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = context.getString(R.string.paradajkovapolievkaingredience8),
                            quantity = 0.3,
                            unit = context.getString(R.string.unit_teaspoon)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = context.getString(R.string.paradajkovapolievkaingredience9),
                            quantity = 1.0,
                            unit = context.getString(R.string.unit_teaspoon)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 1,
                            stuff = context.getString(R.string.paradajkovapolievkaingredience10),
                            quantity = 30.0,
                            unit = context.getString(R.string.unit_gram)
                        )
                    )

                    db.processDao().insert(
                        FoodProcess(
                            food = 1,
                            description = context.getString(R.string.paradajkovapolievkapopis1),
                            step = 1
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 1,
                            description = context.getString(R.string.paradajkovapolievkapopis2),
                            step = 2
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 1,
                            description = context.getString(R.string.paradajkovapolievkapopis3),
                            step = 3
                        )
                    )

                    // 2 ▸ Brokolicová polievka s guľôčkami
                    val bitmap1  = BitmapFactory.decodeResource(context.resources, R.drawable.brokolicovapolievka)
                    var quality1 = 90
                    lateinit var bytes1: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap1.compress(Bitmap.CompressFormat.JPEG, quality1, s)
                        bytes1 = s.toByteArray()
                        quality1 -= 5
                    } while (bytes1.size > 500_000 && quality1 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 2,
                            name          = context.getString(R.string.druhejjedlonazov),
                            typeId        = 1,
                            cookingTime   = 30,
                            preparingTime = 10,
                            portions      = 4,
                            calories      = 222,
                            description   = context.getString(R.string.druhejjedlopopis),
                            image         = bytes1
                        )
                    )
                    val foodId1 = 2
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId1,
                            stuff = context.getString(R.string.druhejedloingredience1),
                            quantity = 250.0,
                            unit = context.getString(R.string.druhejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId1,
                            stuff = context.getString(R.string.druhejedloingredience2),
                            quantity = 400.0,
                            unit = context.getString(R.string.druhejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId1,
                            stuff = context.getString(R.string.druhejedloingredience3),
                            quantity = 200.0,
                            unit = context.getString(R.string.druhejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId1,
                            stuff = context.getString(R.string.druhejedloingredience4),
                            quantity = 4.0,
                            unit = context.getString(R.string.druhejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId1,
                            stuff = context.getString(R.string.druhejedloingredience5),
                            quantity = 1.0,
                            unit = context.getString(R.string.druhejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId1,
                            stuff = context.getString(R.string.druhejedloingredience6),
                            quantity = 1.0,
                            unit = context.getString(R.string.druhejedlojednotka6)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId1,
                            stuff = context.getString(R.string.druhejedloingredience7),
                            quantity = 0.5,
                            unit = context.getString(R.string.druhejedlojednotka7)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId1,
                            stuff = context.getString(R.string.druhejedloingredience8),
                            quantity = 0.3,
                            unit = context.getString(R.string.druhejedlojednotka8)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId1,
                            stuff = context.getString(R.string.druhejedloingredience9),
                            quantity = 1.0,
                            unit = context.getString(R.string.druhejedlojednotka9)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId1,
                            stuff = context.getString(R.string.druhejedloingredience10),
                            quantity = 100.0,
                            unit = context.getString(R.string.druhejedlojednotka10)
                        )
                    )

                    db.processDao().insert(
                        FoodProcess(
                            food = foodId1,
                            description = context.getString(R.string.druhejedlopopis1),
                            step = 1
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId1,
                            description = context.getString(R.string.druhejedlopopis2),
                            step = 2
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId1,
                            description = context.getString(R.string.druhejedlopopis3),
                            step = 3
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId1,
                            description = context.getString(R.string.druhejedlopopis4),
                            step = 4
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId1,
                            description = context.getString(R.string.druhejedlopopis5),
                            step = 5
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId1,
                            description = context.getString(R.string.druhejedlopopis6),
                            step = 6
                        )
                    )

                    /* 3 ▸ Šošovicová polievka s párkom */
                    val bitmap2  = BitmapFactory.decodeResource(context.resources, R.drawable.sosovicovapolievka)
                    var quality2 = 90
                    lateinit var bytes2: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap2.compress(Bitmap.CompressFormat.JPEG, quality2, s)
                        bytes2 = s.toByteArray()
                        quality2 -= 5
                    } while (bytes2.size > 500_000 && quality2 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 3,
                            name          = context.getString(R.string.tretiejedlonazov),
                            typeId        = 1,
                            cookingTime   = 40,
                            preparingTime = 15,
                            portions      = 4,
                            calories      = 250,
                            description   = context.getString(R.string.tretiejedlopopis),
                            image         = bytes2
                        )
                    )


                    /* 4 ▸ Glazované kura s broskyňou a zázvorom */
                    val bitmap3  = BitmapFactory.decodeResource(context.resources, R.drawable.kurasbroskynou)
                    var quality3 = 90
                    lateinit var bytes3: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap3.compress(Bitmap.CompressFormat.JPEG, quality3, s)
                        bytes3 = s.toByteArray()
                        quality3 -= 5
                    } while (bytes3.size > 500_000 && quality3 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 4,
                            name          = context.getString(R.string.stvrtejjedlonazov),
                            typeId        = 3,
                            cookingTime   = 30,
                            preparingTime = 15,
                            portions      = 4,
                            calories      = 350,
                            description   = context.getString(R.string.stvrtejjedlopopis),
                            image         = bytes3
                        )
                    )


                    /* 5 ▸ Kuracie plátky v marináde */
                    val bitmap4  = BitmapFactory.decodeResource(context.resources, R.drawable.kuracieplatkynamarinade)
                    var quality4 = 90
                    lateinit var bytes4: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap4.compress(Bitmap.CompressFormat.JPEG, quality4, s)
                        bytes4 = s.toByteArray()
                        quality4 -= 5
                    } while (bytes4.size > 500_000 && quality4 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 5,
                            name          = context.getString(R.string.piatejedlonazov),
                            typeId        = 3,
                            cookingTime   = 6,
                            preparingTime = 10,
                            portions      = 4,
                            calories      = 300,
                            description   = context.getString(R.string.piatejedlopopis),
                            image         = bytes4
                        )
                    )


                    /* 6 ▸ Pečené bataty plnené fazuľou */
                    val bitmap5  = BitmapFactory.decodeResource(context.resources, R.drawable.pecenebataty)
                    var quality5 = 90
                    lateinit var bytes5: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap5.compress(Bitmap.CompressFormat.JPEG, quality5, s)
                        bytes5 = s.toByteArray()
                        quality5 -= 5
                    } while (bytes5.size > 500_000 && quality5 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 6,
                            name          = context.getString(R.string.siestejedlonazov),
                            typeId        = 3,
                            cookingTime   = 40,
                            preparingTime = 15,
                            portions      = 4,
                            calories      = 350,
                            description   = context.getString(R.string.siestejedlopopis),
                            image         = bytes5
                        )
                    )


                    /* 7 ▸ Pasta alla Norma */
                    val bitmap6  = BitmapFactory.decodeResource(context.resources, R.drawable.pastaalla)
                    var quality6 = 90
                    lateinit var bytes6: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap6.compress(Bitmap.CompressFormat.JPEG, quality6, s)
                        bytes6 = s.toByteArray()
                        quality6 -= 5
                    } while (bytes6.size > 500_000 && quality6 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 7,
                            name          = context.getString(R.string.siedmejedlonazov),
                            typeId        = 3,
                            cookingTime   = 20,
                            preparingTime = 30,
                            portions      = 4,
                            calories      = 400,
                            description   = context.getString(R.string.siedmejedlopopis),
                            image         = bytes6
                        )
                    )


                    /* 8 ▸ Sicílska caponata */
                    val bitmap7  = BitmapFactory.decodeResource(context.resources, R.drawable.sicilskacaponata)
                    var quality7 = 90
                    lateinit var bytes7: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap7.compress(Bitmap.CompressFormat.JPEG, quality7, s)
                        bytes7 = s.toByteArray()
                        quality7 -= 5
                    } while (bytes7.size > 500_000 && quality7 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 8,
                            name          = context.getString(R.string.osmejedlonazov),
                            typeId        = 2,
                            cookingTime   = 45,
                            preparingTime = 15,
                            portions      = 4,
                            calories      = 250,
                            description   = context.getString(R.string.osmejedlopopis),
                            image         = bytes7
                        )
                    )


                    /* 9 ▸ Kačacia pečeň s cibuľovým čatní */
                    val bitmap8  = BitmapFactory.decodeResource(context.resources, R.drawable.kacaciapecen)
                    var quality8 = 90
                    lateinit var bytes8: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap8.compress(Bitmap.CompressFormat.JPEG, quality8, s)
                        bytes8 = s.toByteArray()
                        quality8 -= 5
                    } while (bytes8.size > 500_000 && quality8 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 9,
                            name          = context.getString(R.string.deviatejedlonazov),
                            typeId        = 2,
                            cookingTime   = 30,
                            preparingTime = 15,
                            portions      = 4,
                            calories      = 350,
                            description   = context.getString(R.string.deviatejedlopopis),
                            image         = bytes8
                        )
                    )


                    /* 10 ▸ Zapekané figy s kozím syrom */
                    val bitmap9  = BitmapFactory.decodeResource(context.resources, R.drawable.zapekanefigy)
                    var quality9 = 90
                    lateinit var bytes9: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap9.compress(Bitmap.CompressFormat.JPEG, quality9, s)
                        bytes9 = s.toByteArray()
                        quality9 -= 5
                    } while (bytes9.size > 500_000 && quality9 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 10,
                            name          = context.getString(R.string.desiatejedlonazov),
                            typeId        = 2,
                            cookingTime   = 8,
                            preparingTime = 10,
                            portions      = 4,
                            calories      = 200,
                            description   = context.getString(R.string.desiatejedlopopis),
                            image         = bytes9
                        )
                    )


                    /* 11 ▸ Výborný tvarožník */
                    val bitmap10  = BitmapFactory.decodeResource(context.resources, R.drawable.tvarohoznik)
                    var quality10 = 90
                    lateinit var bytes10: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap10.compress(Bitmap.CompressFormat.JPEG, quality10, s)
                        bytes10 = s.toByteArray()
                        quality10 -= 5
                    } while (bytes10.size > 500_000 && quality10 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 11,
                            name          = context.getString(R.string.jedenastejedlonazov),
                            typeId        = 4,
                            cookingTime   = 35,
                            preparingTime = 15,
                            portions      = 9,
                            calories      = 250,
                            description   = context.getString(R.string.jedenastejedlopopis),
                            image         = bytes10
                        )
                    )


                    /* 12 ▸ Citrónovo‑makové makrónky */
                    val bitmap11  = BitmapFactory.decodeResource(context.resources, R.drawable.makronky)
                    var quality11 = 90
                    lateinit var bytes11: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap11.compress(Bitmap.CompressFormat.JPEG, quality11, s)
                        bytes11 = s.toByteArray()
                        quality11 -= 5
                    } while (bytes11.size > 500_000 && quality11 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 12,
                            name          = context.getString(R.string.dvanastejedlonazov),
                            typeId        = 4,
                            cookingTime   = 18,
                            preparingTime = 30,
                            portions      = 25,
                            calories      = 120,
                            description   = context.getString(R.string.dvanastejedlopopis),
                            image         = bytes11
                        )
                    )


                    /* 13 ▸ Mrkvový koláč bez lepku */
                    val bitmap12  = BitmapFactory.decodeResource(context.resources, R.drawable.mrkvovykolac)
                    var quality12 = 90
                    lateinit var bytes12: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap12.compress(Bitmap.CompressFormat.JPEG, quality12, s)
                        bytes12 = s.toByteArray()
                        quality12 -= 5
                    } while (bytes12.size > 500_000 && quality12 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 13,
                            name          = context.getString(R.string.trinastejedlonazov),
                            typeId        = 4,
                            cookingTime   = 30,
                            preparingTime = 20,
                            portions      = 15,
                            calories      = 220,
                            description   = context.getString(R.string.trinastejedlopopis),
                            image         = bytes12
                        )
                    )


                    /* 14 ▸ Orechovo‑vanilkové rezy */
                    val bitmap13  = BitmapFactory.decodeResource(context.resources, R.drawable.orechovy)
                    var quality13 = 90
                    lateinit var bytes13: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap13.compress(Bitmap.CompressFormat.JPEG, quality13, s)
                        bytes13 = s.toByteArray()
                        quality13 -= 5
                    } while (bytes13.size > 500_000 && quality13 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 14,
                            name          = context.getString(R.string.strnastejedlonazov),
                            typeId        = 4,
                            cookingTime   = 30,
                            preparingTime = 40,
                            portions      = 60,
                            calories      = 300,
                            description   = context.getString(R.string.strnastejedlopopis),
                            image         = bytes13
                        )
                    )
//---------------------------------------------------------------------------------------------------------------



                    /* 16 ▸ Pór s hrozienkami */
                    val bitmap16_1 = BitmapFactory.decodeResource(context.resources, R.drawable.porshrozienkami)
                    var quality16_1 = 90
                    lateinit var bytes16_1: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap16_1.compress(Bitmap.CompressFormat.JPEG, quality16_1, s)
                        bytes16_1 = s.toByteArray()
                        quality16_1 -= 5
                    } while (bytes16_1.size > 500_000 && quality16_1 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 15,
                            name            = context.getString(R.string.sestnastejedlonazov),
                            typeId          = 5,
                            cookingTime     = 15,
                            preparingTime   = 10,
                            portions        = 2,
                            calories        = 130,
                            description     = context.getString(R.string.sestnastejedlopopis),
                            image           = bytes16_1
                        )
                    )


                    /* 17 ▸ Zemiaky s kyslými uhorkami */
                    val bitmap17_1 = BitmapFactory.decodeResource(context.resources, R.drawable.zemiakysuhormai)
                    var quality17_1 = 90
                    lateinit var bytes17_1: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap17_1.compress(Bitmap.CompressFormat.JPEG, quality17_1, s)
                        bytes17_1 = s.toByteArray()
                        quality17_1 -= 5
                    } while (bytes17_1.size > 500_000 && quality17_1 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 16,
                            name            = context.getString(R.string.sedemnastejedlonazov),
                            typeId          = 5,
                            cookingTime     = 20,
                            preparingTime   = 10,
                            portions        = 3,
                            calories        = 160,
                            description     = context.getString(R.string.sedemnastejedlopopis),
                            image           = bytes17_1
                        )
                    )


                    /* 18 ▸ Omáčka Mojo Rocho */
                    val bitmap18_1 = BitmapFactory.decodeResource(context.resources, R.drawable.mojorocho)
                    var quality18_1 = 90
                    lateinit var bytes18_1: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap18_1.compress(Bitmap.CompressFormat.JPEG, quality18_1, s)
                        bytes18_1 = s.toByteArray()
                        quality18_1 -= 5
                    } while (bytes18_1.size > 500_000 && quality18_1 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 17,
                            name            = context.getString(R.string.osemnastejedlonazov),
                            typeId          = 5,
                            cookingTime     = 0,
                            preparingTime   = 10,
                            portions        = 6,
                            calories        = 90,
                            description     = context.getString(R.string.osemnastejedlopopis),
                            image           = bytes18_1
                        )
                    )


                    /* 19 ▸ Zapekané cestoviny so syrom a semienkami */
                    val bitmap19_1 = BitmapFactory.decodeResource(context.resources, R.drawable.zapekanecestoviny)
                    var quality19_1 = 90
                    lateinit var bytes19_1: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap19_1.compress(Bitmap.CompressFormat.JPEG, quality19_1, s)
                        bytes19_1 = s.toByteArray()
                        quality19_1 -= 5
                    } while (bytes19_1.size > 500_000 && quality19_1 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 18,
                            name            = context.getString(R.string.devatnastejedlonazov),
                            typeId          = 3,
                            cookingTime     = 25,
                            preparingTime   = 20,
                            portions        = 4,
                            calories        = 450,
                            description     = context.getString(R.string.devatnastejedlopopis),
                            image           = bytes19_1
                        )
                    )


                    /* 20 ▸ Čokoládové brownie */
                    val bitmap20_1 = BitmapFactory.decodeResource(context.resources, R.drawable.brownies)
                    var quality20_1 = 90
                    lateinit var bytes20_1: ByteArray
                    do {
                        val s = ByteArrayOutputStream()
                        bitmap20_1.compress(Bitmap.CompressFormat.JPEG, quality20_1, s)
                        bytes20_1 = s.toByteArray()
                        quality20_1 -= 5
                    } while (bytes20_1.size > 500_000 && quality20_1 > 10)

                    db.foodDao().insert(
                        Food(
                            id = 19,
                            name            = context.getString(R.string.dvadsatejedlonazov),
                            typeId          = 4,
                            cookingTime     = 30,
                            preparingTime   = 15,
                            portions        = 12,
                            calories        = 450,
                            description     = context.getString(R.string.dvadsatejedlopopis),
                            image           = bytes20_1
                        )
                    )

                    //tretie jedlo
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 3,
                            stuff = context.getString(R.string.tretiejedloingredience1),
                            quantity = 250.0,
                            unit = context.getString(R.string.tretiejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 3,
                            stuff = context.getString(R.string.tretiejedloingredience2),
                            quantity = 1.0,
                            unit = context.getString(R.string.tretiejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 3,
                            stuff = context.getString(R.string.tretiejedloingredience3),
                            quantity = 1.0,
                            unit = context.getString(R.string.tretiejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 3,
                            stuff = context.getString(R.string.tretiejedloingredience4),
                            quantity = 2.0,
                            unit = context.getString(R.string.tretiejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 3,
                            stuff = context.getString(R.string.tretiejedloingredience5),
                            quantity = 2.0,
                            unit = context.getString(R.string.tretiejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 3,
                            stuff = context.getString(R.string.tretiejedloingredience6),
                            quantity = 4.0,
                            unit = context.getString(R.string.tretiejedlojednotka6)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 3,
                            stuff = context.getString(R.string.tretiejedloingredience7),
                            quantity = 1.0,
                            unit = context.getString(R.string.tretiejedlojednotka7)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 3,
                            stuff = context.getString(R.string.tretiejedloingredience8),
                            quantity = 1.0,
                            unit = context.getString(R.string.tretiejedlojednotka8)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 3,
                            stuff = context.getString(R.string.tretiejedloingredience9),
                            quantity = 0.3,
                            unit = context.getString(R.string.tretiejedlojednotka9)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 3,
                            stuff = context.getString(R.string.tretiejedloingredience10),
                            quantity = 0.5,
                            unit = context.getString(R.string.tretiejedlojednotka10)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 3,
                            stuff = context.getString(R.string.tretiejedloingredience11),
                            quantity = 2000.0,
                            unit = context.getString(R.string.tretiejedlojednotka11)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 3,
                            stuff = context.getString(R.string.tretiejedloingredience12),
                            quantity = 1.0,
                            unit = context.getString(R.string.tretiejedlojednotka12)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 3,
                            stuff = context.getString(R.string.tretiejedloingredience13),
                            quantity = 3.0,
                            unit = context.getString(R.string.tretiejedlojednotka13)
                        )
                    )

                    db.processDao().insert(
                        FoodProcess(
                            food = 3,
                            step = 1,
                            description = context.getString(R.string.tretiejedlopopis1)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 3,
                            step = 2,
                            description = context.getString(R.string.tretiejedlopopis2)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 3,
                            step = 3,
                            description = context.getString(R.string.tretiejedlopopis3)
                        )
                    )


                    //4 jedlo
                    // FoodStuff – Glazované kura s broskyňou a zázvorom (food = 4)
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience1),
                            quantity = 500.0,
                            unit = context.getString(R.string.stvrtejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience2),
                            quantity = 0.5,
                            unit = context.getString(R.string.stvrtejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience3),
                            quantity = 0.3,
                            unit = context.getString(R.string.stvrtejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience4),
                            quantity = 1.0,
                            unit = context.getString(R.string.stvrtejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience5),
                            quantity = 1.0,
                            unit = context.getString(R.string.stvrtejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience6),
                            quantity = 2.0,
                            unit = context.getString(R.string.stvrtejedlojednotka6)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience7),
                            quantity = 2.0,
                            unit = context.getString(R.string.stvrtejedlojednotka7)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience8),
                            quantity = 2.0,
                            unit = context.getString(R.string.stvrtejedlojednotka8)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience9),
                            quantity = 3.0,
                            unit = context.getString(R.string.stvrtejedlojednotka9)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience10),
                            quantity = 3.0,
                            unit = context.getString(R.string.stvrtejedlojednotka10)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience11),
                            quantity = 2.0,
                            unit = context.getString(R.string.stvrtejedlojednotka11)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience12),
                            quantity = 200.0,
                            unit = context.getString(R.string.stvrtejedlojednotka12)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience13),
                            quantity = 150.0,
                            unit = context.getString(R.string.stvrtejedlojednotka13)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience14),
                            quantity = 0.5,
                            unit = context.getString(R.string.stvrtejedlojednotka14)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience15),
                            quantity = 2.0,
                            unit = context.getString(R.string.stvrtejedlojednotka15)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience16),
                            quantity = 1.0,
                            unit = context.getString(R.string.stvrtejedlojednotka16)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience17),
                            quantity = 1.0,
                            unit = context.getString(R.string.stvrtejedlojednotka17)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience18),
                            quantity = 1.5,
                            unit = context.getString(R.string.stvrtejedlojednotka18)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 4,
                            stuff = context.getString(R.string.stvrtejedloingredience19),
                            quantity = 1.0,
                            unit = context.getString(R.string.stvrtejedlojednotka19)
                        )
                    )

// FoodProcess – kroky prípravy
                    db.processDao().insert(
                        FoodProcess(
                            food = 4,
                            step = 1,
                            description = context.getString(R.string.stvrtejedlopopis1)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 4,
                            step = 2,
                            description = context.getString(R.string.stvrtejedlopopis2)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 4,
                            step = 3,
                            description = context.getString(R.string.stvrtejedlopopis3)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 4,
                            step = 4,
                            description = context.getString(R.string.stvrtejedlopopis4)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 4,
                            step = 5,
                            description = context.getString(R.string.stvrtejedlopopis5)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 4,
                            step = 6,
                            description = context.getString(R.string.stvrtejedlopopis6)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 4,
                            step = 7,
                            description = context.getString(R.string.stvrtejedlopopis7)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 4,
                            step = 8,
                            description = context.getString(R.string.stvrtejedlopopis8)
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 5,
                            stuff = context.getString(R.string.piatejedloingredience1),
                            quantity = 4.0,
                            unit = context.getString(R.string.piatejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 5,
                            stuff = context.getString(R.string.piatejedloingredience2),
                            quantity = 1.0,
                            unit = context.getString(R.string.piatejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 5,
                            stuff = context.getString(R.string.piatejedloingredience3),
                            quantity = 1.0,
                            unit = context.getString(R.string.piatejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 5,
                            stuff = context.getString(R.string.piatejedloingredience4),
                            quantity = 0.5,
                            unit = context.getString(R.string.piatejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 5,
                            stuff = context.getString(R.string.piatejedloingredience5),
                            quantity = 1.0,
                            unit = context.getString(R.string.piatejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 5,
                            stuff = context.getString(R.string.piatejedloingredience6),
                            quantity = 0.5,
                            unit = context.getString(R.string.piatejedlojednotka6)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 5,
                            stuff = context.getString(R.string.piatejedloingredience7),
                            quantity = 2.0,
                            unit = context.getString(R.string.piatejedlojednotka7)
                        )
                    )

                    db.processDao().insert(
                        FoodProcess(
                            food = 5,
                            step = 1,
                            description = context.getString(R.string.piatejedlopopis1)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 5,
                            step = 2,
                            description = context.getString(R.string.piatejedlopopis2)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 5,
                            step = 3,
                            description = context.getString(R.string.piatejedlopopis3)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 5,
                            step = 4,
                            description = context.getString(R.string.piatejedlopopis4)
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 6,
                            stuff = context.getString(R.string.siestejedloingredience1),
                            quantity = 4.0,
                            unit = context.getString(R.string.siestejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 6,
                            stuff = context.getString(R.string.siestejedloingredience2),
                            quantity = 400.0,
                            unit = context.getString(R.string.siestejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 6,
                            stuff = context.getString(R.string.siestejedloingredience3),
                            quantity = 400.0,
                            unit = context.getString(R.string.siestejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 6,
                            stuff = context.getString(R.string.siestejedloingredience4),
                            quantity = 3.0,
                            unit = context.getString(R.string.siestejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 6,
                            stuff = context.getString(R.string.siestejedloingredience5),
                            quantity = 1.0,
                            unit = context.getString(R.string.siestejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 6,
                            stuff = context.getString(R.string.siestejedloingredience6),
                            quantity = 4.0,
                            unit = context.getString(R.string.siestejedlojednotka6)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 6,
                            stuff = context.getString(R.string.siestejedloingredience7),
                            quantity = 4.0,
                            unit = context.getString(R.string.siestejedlojednotka7)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 6,
                            stuff = context.getString(R.string.siestejedloingredience8),
                            quantity = 1.0,
                            unit = context.getString(R.string.siestejedlojednotka8)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 6,
                            stuff = context.getString(R.string.siestejedloingredience9),
                            quantity = 1.0,
                            unit = context.getString(R.string.siestejedlojednotka9)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 6,
                            stuff = context.getString(R.string.siestejedloingredience10),
                            quantity = 1.0,
                            unit = context.getString(R.string.siestejedlojednotka10)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 6,
                            stuff = context.getString(R.string.siestejedloingredience11),
                            quantity = 0.5,
                            unit = context.getString(R.string.siestejedlojednotka11)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = 6,
                            stuff = context.getString(R.string.siestejedloingredience12),
                            quantity = 0.3,
                            unit = context.getString(R.string.siestejedlojednotka12)
                        )
                    )

                    db.processDao().insert(
                        FoodProcess(
                            food = 6,
                            step = 1,
                            description = context.getString(R.string.siestejedlopopis1)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 6,
                            step = 2,
                            description = context.getString(R.string.siestejedlopopis2)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = 6,
                            step = 3,
                            description = context.getString(R.string.siestejedlopopis3)
                        )
                    )

                    //6.jedlo
                    // 7. Pasta alla Norma
                    val foodId6 = 7
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId6,
                            stuff = context.getString(R.string.siedmejedloingredience1),
                            quantity = 750.0,
                            unit = context.getString(R.string.siedmejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId6,
                            stuff = context.getString(R.string.siedmejedloingredience2),
                            quantity = 1.0,
                            unit = context.getString(R.string.siedmejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId6,
                            stuff = context.getString(R.string.siedmejedloingredience3),
                            quantity = 2.0,
                            unit = context.getString(R.string.siedmejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId6,
                            stuff = context.getString(R.string.siedmejedloingredience4),
                            quantity = 3.0,
                            unit = context.getString(R.string.siedmejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId6,
                            stuff = context.getString(R.string.siedmejedloingredience5),
                            quantity = 3.0,
                            unit = context.getString(R.string.siedmejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId6,
                            stuff = context.getString(R.string.siedmejedloingredience6),
                            quantity = 700.0,
                            unit = context.getString(R.string.siedmejedlojednotka6)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId6,
                            stuff = context.getString(R.string.siedmejedloingredience7),
                            quantity = 0.5,
                            unit = context.getString(R.string.siedmejedlojednotka7)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId6,
                            stuff = context.getString(R.string.siedmejedloingredience8),
                            quantity = 0.5,
                            unit = context.getString(R.string.siedmejedlojednotka8)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId6,
                            stuff = context.getString(R.string.siedmejedloingredience9),
                            quantity = 0.5,
                            unit = context.getString(R.string.siedmejedlojednotka9)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId6,
                            stuff = context.getString(R.string.siedmejedloingredience10),
                            quantity = 0.5,
                            unit = context.getString(R.string.siedmejedlojednotka10)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId6,
                            stuff = context.getString(R.string.siedmejedloingredience11),
                            quantity = 500.0,
                            unit = context.getString(R.string.siedmejedlojednotka11)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId6,
                            stuff = context.getString(R.string.siedmejedloingredience12),
                            quantity = 150.0,
                            unit = context.getString(R.string.siedmejedlojednotka12)
                        )
                    )

                    db.processDao().insert(
                        FoodProcess(
                            food = foodId6,
                            step = 1,
                            description = context.getString(R.string.siedmejedlopopis1)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId6,
                            step = 2,
                            description = context.getString(R.string.siedmejedlopopis2)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId6,
                            step = 3,
                            description = context.getString(R.string.siedmejedlopopis3)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId6,
                            step = 4,
                            description = context.getString(R.string.siedmejedlopopis4)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId6,
                            step = 5,
                            description = context.getString(R.string.siedmejedlopopis5)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId6,
                            step = 6,
                            description = context.getString(R.string.siedmejedlopopis6)
                        )
                    )
val foodId7 = 8

// 8. Sicílska caponata
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId7,
                            stuff = context.getString(R.string.osmejedloingredience1),
                            quantity = 400.0,
                            unit = context.getString(R.string.osmejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId7,
                            stuff = context.getString(R.string.osmejedloingredience2),
                            quantity = 3.0,
                            unit = context.getString(R.string.osmejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId7,
                            stuff = context.getString(R.string.osmejedloingredience3),
                            quantity = 1.0,
                            unit = context.getString(R.string.osmejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId7,
                            stuff = context.getString(R.string.osmejedloingredience4),
                            quantity = 2.0,
                            unit = context.getString(R.string.osmejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId7,
                            stuff = context.getString(R.string.osmejedloingredience5),
                            quantity = 300.0,
                            unit = context.getString(R.string.osmejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId7,
                            stuff = context.getString(R.string.osmejedloingredience6),
                            quantity = 2.0,
                            unit = context.getString(R.string.osmejedlojednotka6)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId7,
                            stuff = context.getString(R.string.osmejedloingredience7),
                            quantity = 2.0,
                            unit = context.getString(R.string.osmejedlojednotka7)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId7,
                            stuff = context.getString(R.string.osmejedloingredience8),
                            quantity = 0.5,
                            unit = context.getString(R.string.osmejedlojednotka8)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId7,
                            stuff = context.getString(R.string.osmejedloingredience9),
                            quantity = 2.5,
                            unit = context.getString(R.string.osmejedlojednotka9)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId7,
                            stuff = context.getString(R.string.osmejedloingredience10),
                            quantity = 0.5,
                            unit = context.getString(R.string.osmejedlojednotka10)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId7,
                            stuff = context.getString(R.string.osmejedloingredience11),
                            quantity = 2.0,
                            unit = context.getString(R.string.osmejedlojednotka11)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId7,
                            stuff = context.getString(R.string.osmejedloingredience12),
                            quantity = 6.0,
                            unit = context.getString(R.string.osmejedlojednotka12)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId7,
                            stuff = context.getString(R.string.osmejedloingredience13),
                            quantity = 0.3,
                            unit = context.getString(R.string.osmejedlojednotka13)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId7,
                            stuff = context.getString(R.string.osmejedloingredience14),
                            quantity = 0.5,
                            unit = context.getString(R.string.osmejedlojednotka14)
                        )
                    )

                    db.processDao().insert(
                        FoodProcess(
                            food = foodId7,
                            step = 1,
                            description = context.getString(R.string.osmejedlopopis1)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId7,
                            step = 2,
                            description = context.getString(R.string.osmejedlopopis2)
                        )
                    )
                    val foodId8 = 8
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId7,
                            step = 3,
                            description = context.getString(R.string.osmejedlopopis3)
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId8,
                            stuff = context.getString(R.string.deviatejedloingredience1),
                            quantity = 300.0,
                            unit = context.getString(R.string.deviatejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId8,
                            stuff = context.getString(R.string.deviatejedloingredience2),
                            quantity = 0.5,
                            unit = context.getString(R.string.deviatejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId8,
                            stuff = context.getString(R.string.deviatejedloingredience3),
                            quantity = 0.5,
                            unit = context.getString(R.string.deviatejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId8,
                            stuff = context.getString(R.string.deviatejedloingredience4),
                            quantity = 2.0,
                            unit = context.getString(R.string.deviatejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId8,
                            stuff = context.getString(R.string.deviatejedloingredience5),
                            quantity = 100.0,
                            unit = context.getString(R.string.deviatejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId8,
                            stuff = context.getString(R.string.deviatejedloingredience6),
                            quantity = 300.0,
                            unit = context.getString(R.string.deviatejedlojednotka6)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId8,
                            stuff = context.getString(R.string.deviatejedloingredience7),
                            quantity = 1.0,
                            unit = context.getString(R.string.deviatejedlojednotka7)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId8,
                            stuff = context.getString(R.string.deviatejedloingredience8),
                            quantity = 50.0,
                            unit = context.getString(R.string.deviatejedlojednotka8)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId8,
                            stuff = context.getString(R.string.deviatejedloingredience9),
                            quantity = 2.0,
                            unit = context.getString(R.string.deviatejedlojednotka9)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId8,
                            stuff = context.getString(R.string.deviatejedloingredience10),
                            quantity = 1.0,
                            unit = context.getString(R.string.deviatejedlojednotka10)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId8,
                            stuff = context.getString(R.string.deviatejedloingredience11),
                            quantity = 1.0,
                            unit = context.getString(R.string.deviatejedlojednotka11)
                        )
                    )

                    db.processDao().insert(
                        FoodProcess(
                            food = foodId8,
                            step = 1,
                            description = context.getString(R.string.deviatejedlopopis1)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId8,
                            step = 2,
                            description = context.getString(R.string.deviatejedlopopis2)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId8,
                            step = 3,
                            description = context.getString(R.string.deviatejedlopopis3)
                        )
                    )
                    val foodId9 = 9
                    val foodId10 = 10
                    val foodId11 = 11
                    val foodId12 = 12
                    val foodId13 = 13
                    val foodId14 = 14
                    val foodId15 = 15
                    val foodId16 = 16
                    val foodId17 = 17
                    val foodId18 = 18
                    val foodId19 = 19
                    //9. jedlo
                    /* ----------------------------- 10. Zapekané figy ----------------------------- */
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId9,
                            stuff = context.getString(R.string.desiatejedloingredience1),
                            quantity = 8.0,
                            unit = context.getString(R.string.desiatejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId9,
                            stuff = context.getString(R.string.desiatejedloingredience2),
                            quantity = 100.0,
                            unit = context.getString(R.string.desiatejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId9,
                            stuff = context.getString(R.string.desiatejedloingredience3),
                            quantity = 2.0,
                            unit = context.getString(R.string.desiatejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId9,
                            stuff = context.getString(R.string.desiatejedloingredience4),
                            quantity = 2.0,
                            unit = context.getString(R.string.desiatejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId9,
                            stuff = context.getString(R.string.desiatejedloingredience5),
                            quantity = 0.5,
                            unit = context.getString(R.string.desiatejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId9,
                            stuff = context.getString(R.string.desiatejedloingredience6),
                            quantity = 0.3,
                            unit = context.getString(R.string.desiatejedlojednotka6)
                        )
                    )

                    db.processDao().insert(
                        FoodProcess(
                            food = foodId9,
                            step = 1,
                            description = context.getString(R.string.desiatejedlopopis1)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId9,
                            step = 2,
                            description = context.getString(R.string.desiatejedlopopis2)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId9,
                            step = 3,
                            description = context.getString(R.string.desiatejedlopopis3)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId9,
                            step = 4,
                            description = context.getString(R.string.desiatejedlopopis4)
                        )
                    )


                    /* ------------------------- 11. Výborný tvarožník ------------------------- */
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId10,
                            stuff = context.getString(R.string.jedenastejedloingredience1),
                            quantity = 600.0,
                            unit = context.getString(R.string.jedenastejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId10,
                            stuff = context.getString(R.string.jedenastejedloingredience2),
                            quantity = 3.0,
                            unit = context.getString(R.string.jedenastejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId10,
                            stuff = context.getString(R.string.jedenastejedloingredience3),
                            quantity = 3.0,
                            unit = context.getString(R.string.jedenastejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId10,
                            stuff = context.getString(R.string.jedenastejedloingredience4),
                            quantity = 3.0,
                            unit = context.getString(R.string.jedenastejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId10,
                            stuff = context.getString(R.string.jedenastejedloingredience5),
                            quantity = 3.0,
                            unit = context.getString(R.string.jedenastejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId10,
                            stuff = context.getString(R.string.jedenastejedloingredience6),
                            quantity = 1.0,
                            unit = context.getString(R.string.jedenastejedlojednotka6)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId10,
                            stuff = context.getString(R.string.jedenastejedloingredience7),
                            quantity = 0.5,
                            unit = context.getString(R.string.jedenastejedlojednotka7)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId10,
                            stuff = context.getString(R.string.jedenastejedloingredience8),
                            quantity = 3.0,
                            unit = context.getString(R.string.jedenastejedlojednotka8)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId10,
                            stuff = context.getString(R.string.jedenastejedloingredience9),
                            quantity = 3.0,
                            unit = context.getString(R.string.jedenastejedlojednotka9)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId10,
                            stuff = context.getString(R.string.jedenastejedloingredience10),
                            quantity = 1.0,
                            unit = context.getString(R.string.jedenastejedlojednotka10)
                        )
                    )

                    db.processDao().insert(
                        FoodProcess(
                            food = foodId10,
                            step = 1,
                            description = context.getString(R.string.jedenastejedlopopis1)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId10,
                            step = 2,
                            description = context.getString(R.string.jedenastejedlopopis2)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId10,
                            step = 3,
                            description = context.getString(R.string.jedenastejedlopopis3)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId10,
                            step = 4,
                            description = context.getString(R.string.jedenastejedlopopis4)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId10,
                            step = 5,
                            description = context.getString(R.string.jedenastejedlopopis5)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId10,
                            step = 6,
                            description = context.getString(R.string.jedenastejedlopopis6)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId10,
                            step = 7,
                            description = context.getString(R.string.jedenastejedlopopis7)
                        )
                    )


                    /* -------------------- 12. Citrónovo‑makové makrónky -------------------- */
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId11,
                            stuff = context.getString(R.string.dvanastejedloingredience1),
                            quantity = 85.0,
                            unit = context.getString(R.string.dvanastejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId11,
                            stuff = context.getString(R.string.dvanastejedloingredience2),
                            quantity = 10.0,
                            unit = context.getString(R.string.dvanastejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId11,
                            stuff = context.getString(R.string.dvanastejedloingredience3),
                            quantity = 95.0,
                            unit = context.getString(R.string.dvanastejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId11,
                            stuff = context.getString(R.string.dvanastejedloingredience4),
                            quantity = 95.0,
                            unit = context.getString(R.string.dvanastejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId11,
                            stuff = context.getString(R.string.dvanastejedloingredience5),
                            quantity = 35.0,
                            unit = context.getString(R.string.dvanastejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId11,
                            stuff = context.getString(R.string.dvanastejedloingredience6),
                            quantity = 70.0,
                            unit = context.getString(R.string.dvanastejedlojednotka6)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId11,
                            stuff = context.getString(R.string.dvanastejedloingredience7),
                            quantity = 0.5,
                            unit = context.getString(R.string.dvanastejedlojednotka7)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId11,
                            stuff = context.getString(R.string.dvanastejedloingredience8),
                            quantity = 120.0,
                            unit = context.getString(R.string.dvanastejedlojednotka8)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId11,
                            stuff = context.getString(R.string.dvanastejedloingredience9),
                            quantity = 90.0,
                            unit = context.getString(R.string.dvanastejedlojednotka9)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId11,
                            stuff = context.getString(R.string.dvanastejedloingredience10),
                            quantity = 1.5,
                            unit = context.getString(R.string.dvanastejedlojednotka10)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId11,
                            stuff = context.getString(R.string.dvanastejedloingredience11),
                            quantity = 6.0,
                            unit = context.getString(R.string.dvanastejedlojednotka11)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId11,
                            stuff = context.getString(R.string.dvanastejedloingredience12),
                            quantity = 105.0,
                            unit = context.getString(R.string.dvanastejedlojednotka12)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId11,
                            stuff = context.getString(R.string.dvanastejedloingredience13),
                            quantity = 100.0,
                            unit = context.getString(R.string.dvanastejedlojednotka13)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId11,
                            stuff = context.getString(R.string.dvanastejedloingredience14),
                            quantity = 75.0,
                            unit = context.getString(R.string.dvanastejedlojednotka14)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId11,
                            stuff = context.getString(R.string.dvanastejedloingredience15),
                            quantity = 2.0,
                            unit = context.getString(R.string.dvanastejedlojednotka15)
                        )
                    )

                    db.processDao().insert(
                        FoodProcess(
                            food = foodId11,
                            step = 1,
                            description = context.getString(R.string.dvanastejedlopopis1)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId11,
                            step = 2,
                            description = context.getString(R.string.dvanastejedlopopis2)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId11,
                            step = 3,
                            description = context.getString(R.string.dvanastejedlopopis3)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId11,
                            step = 4,
                            description = context.getString(R.string.dvanastejedlopopis4)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId11,
                            step = 5,
                            description = context.getString(R.string.dvanastejedlopopis5)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId11,
                            step = 6,
                            description = context.getString(R.string.dvanastejedlopopis6)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId11,
                            step = 7,
                            description = context.getString(R.string.dvanastejedlopopis7)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId11,
                            step = 8,
                            description = context.getString(R.string.dvanastejedlopopis8)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId11,
                            step = 9,
                            description = context.getString(R.string.dvanastejedlopopis9)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId11,
                            step = 10,
                            description = context.getString(R.string.dvanastejedlopopis10)
                        )
                    )


                    /* -------------- 13. Mrkvový koláč bez lepku (foodId12 = 13) -------------- */
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId12,
                            stuff = context.getString(R.string.trinastejedloingredience1),
                            quantity = 140.0,
                            unit = context.getString(R.string.trinastejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId12,
                            stuff = context.getString(R.string.trinastejedloingredience2),
                            quantity = 4.0,
                            unit = context.getString(R.string.trinastejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId12,
                            stuff = context.getString(R.string.trinastejedloingredience3),
                            quantity = 30.0,
                            unit = context.getString(R.string.trinastejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId12,
                            stuff = context.getString(R.string.trinastejedloingredience4),
                            quantity = 160.0,
                            unit = context.getString(R.string.trinastejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId12,
                            stuff = context.getString(R.string.trinastejedloingredience5),
                            quantity = 60.0,
                            unit = context.getString(R.string.trinastejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId12,
                            stuff = context.getString(R.string.trinastejedloingredience6),
                            quantity = 120.0,
                            unit = context.getString(R.string.trinastejedlojednotka6)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId12,
                            stuff = context.getString(R.string.trinastejedloingredience7),
                            quantity = 85.0,
                            unit = context.getString(R.string.trinastejedlojednotka7)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId12,
                            stuff = context.getString(R.string.trinastejedloingredience8),
                            quantity = 1.0,
                            unit = context.getString(R.string.trinastejedlojednotka8)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId12,
                            stuff = context.getString(R.string.trinastejedloingredience9),
                            quantity = 2.0,
                            unit = context.getString(R.string.trinastejedlojednotka9)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId12,
                            stuff = context.getString(R.string.trinastejedloingredience10),
                            quantity = 300.0,
                            unit = context.getString(R.string.trinastejedlojednotka10)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId12,
                            stuff = context.getString(R.string.trinastejedloingredience11),
                            quantity = 3.0,
                            unit = context.getString(R.string.trinastejedlojednotka11)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId12,
                            stuff = context.getString(R.string.trinastejedloingredience12),
                            quantity = 1.0,
                            unit = context.getString(R.string.trinastejedlojednotka12)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId12,
                            stuff = context.getString(R.string.trinastejedloingredience13),
                            quantity = 1.0,
                            unit = context.getString(R.string.trinastejedlojednotka13)
                        )
                    )

                    db.processDao().insert(
                        FoodProcess(
                            food = foodId12,
                            step = 1,
                            description = context.getString(R.string.trinastejedlopopis1)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId12,
                            step = 2,
                            description = context.getString(R.string.trinastejedlopopis2)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId12,
                            step = 3,
                            description = context.getString(R.string.trinastejedlopopis3)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId12,
                            step = 4,
                            description = context.getString(R.string.trinastejedlopopis4)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId12,
                            step = 5,
                            description = context.getString(R.string.trinastejedlopopis5)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId12,
                            step = 6,
                            description = context.getString(R.string.trinastejedlopopis6)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId12,
                            step = 7,
                            description = context.getString(R.string.trinastejedlopopis7)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId12,
                            step = 8,
                            description = context.getString(R.string.trinastejedlopopis8)
                        )
                    )


                    /* -------------- 14. Orechovo‑vanilkové rezy (foodId13 = 14) -------------- */
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId13,
                            stuff = context.getString(R.string.strnastejedloingredience1),
                            quantity = 12.0,
                            unit = context.getString(R.string.strnastejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId13,
                            stuff = context.getString(R.string.strnastejedloingredience2),
                            quantity = 9.0,
                            unit = context.getString(R.string.strnastejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId13,
                            stuff = context.getString(R.string.strnastejedloingredience3),
                            quantity = 2.0,
                            unit = context.getString(R.string.strnastejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId13,
                            stuff = context.getString(R.string.strnastejedloingredience4),
                            quantity = 12.0,
                            unit = context.getString(R.string.strnastejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId13,
                            stuff = context.getString(R.string.strnastejedloingredience5),
                            quantity = 10.0,
                            unit = context.getString(R.string.strnastejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId13,
                            stuff = context.getString(R.string.strnastejedloingredience6),
                            quantity = 15.0,
                            unit = context.getString(R.string.strnastejedlojednotka6)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId13,
                            stuff = context.getString(R.string.strnastejedloingredience7),
                            quantity = 3.0,
                            unit = context.getString(R.string.strnastejedlojednotka7)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId13,
                            stuff = context.getString(R.string.strnastejedloingredience8),
                            quantity = 5.0,
                            unit = context.getString(R.string.strnastejedlojednotka8)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId13,
                            stuff = context.getString(R.string.strnastejedloingredience9),
                            quantity = 1000.0,
                            unit = context.getString(R.string.strnastejedlojednotka9)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId13,
                            stuff = context.getString(R.string.strnastejedloingredience10),
                            quantity = 4.0,
                            unit = context.getString(R.string.strnastejedlojednotka10)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId13,
                            stuff = context.getString(R.string.strnastejedloingredience11),
                            quantity = 500.0,
                            unit = context.getString(R.string.strnastejedlojednotka11)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId13,
                            stuff = context.getString(R.string.strnastejedloingredience12),
                            quantity = 50.0,
                            unit = context.getString(R.string.strnastejedlojednotka12)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId13,
                            stuff = context.getString(R.string.strnastejedloingredience13),
                            quantity = 1.0,
                            unit = context.getString(R.string.strnastejedlojednotka13)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId13,
                            stuff = context.getString(R.string.strnastejedloingredience14),
                            quantity = 200.0,
                            unit = context.getString(R.string.strnastejedlojednotka14)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId13,
                            stuff = context.getString(R.string.strnastejedloingredience15),
                            quantity = 80.0,
                            unit = context.getString(R.string.strnastejedlojednotka15)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId13,
                            stuff = context.getString(R.string.strnastejedloingredience16),
                            quantity = 1.0,
                            unit = context.getString(R.string.strnastejedlojednotka16)
                        )
                    )

                    db.processDao().insert(
                        FoodProcess(
                            food = foodId13,
                            step = 1,
                            description = context.getString(R.string.strnastejedlopopis1)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId13,
                            step = 2,
                            description = context.getString(R.string.strnastejedlopopis2)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId13,
                            step = 3,
                            description = context.getString(R.string.strnastejedlopopis3)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId13,
                            step = 4,
                            description = context.getString(R.string.strnastejedlopopis4)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId13,
                            step = 5,
                            description = context.getString(R.string.strnastejedlopopis5)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId13,
                            step = 6,
                            description = context.getString(R.string.strnastejedlopopis6)
                        )
                    )


                    /* ------------- 15. Zemiaky pečené s tekvicou (foodId14 = 15) ------------- */
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId14,
                            stuff = context.getString(R.string.patnastejedloingredience1),
                            quantity = 600.0,
                            unit = context.getString(R.string.patnastejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId14,
                            stuff = context.getString(R.string.patnastejedloingredience2),
                            quantity = 400.0,
                            unit = context.getString(R.string.patnastejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId14,
                            stuff = context.getString(R.string.patnastejedloingredience3),
                            quantity = 1.0,
                            unit = context.getString(R.string.patnastejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId14,
                            stuff = context.getString(R.string.patnastejedloingredience4),
                            quantity = 0.5,
                            unit = context.getString(R.string.patnastejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId14,
                            stuff = context.getString(R.string.patnastejedloingredience5),
                            quantity = 0.5,
                            unit = context.getString(R.string.patnastejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId14,
                            stuff = context.getString(R.string.patnastejedloingredience6),
                            quantity = 1.0,
                            unit = context.getString(R.string.patnastejedlojednotka6)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId14,
                            stuff = context.getString(R.string.patnastejedloingredience7),
                            quantity = 1.0,
                            unit = context.getString(R.string.patnastejedlojednotka7)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId14,
                            stuff = context.getString(R.string.patnastejedloingredience8),
                            quantity = 2.0,
                            unit = context.getString(R.string.patnastejedlojednotka8)
                        )
                    )

                    db.processDao().insert(
                        FoodProcess(
                            food = foodId14,
                            step = 1,
                            description = context.getString(R.string.patnastejedlopopis1)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId14,
                            step = 2,
                            description = context.getString(R.string.patnastejedlopopis2)
                        )
                    )
                    db.processDao().insert(
                        FoodProcess(
                            food = foodId14,
                            step = 3,
                            description = context.getString(R.string.patnastejedlopopis3)
                        )
                    )

                    //15
                    /* ------------------------- 16. Pór s hrozienkami ------------------------- */
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId15,
                            stuff = context.getString(R.string.sestnastejedloingredience1),
                            quantity = 2.0,
                            unit = context.getString(R.string.sestnastejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId15,
                            stuff = context.getString(R.string.sestnastejedloingredience2),
                            quantity = 1.0,
                            unit = context.getString(R.string.sestnastejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId15,
                            stuff = context.getString(R.string.sestnastejedloingredience3),
                            quantity = 2.0,
                            unit = context.getString(R.string.sestnastejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId15,
                            stuff = context.getString(R.string.sestnastejedloingredience4),
                            quantity = 2.0,
                            unit = context.getString(R.string.sestnastejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId15,
                            stuff = context.getString(R.string.sestnastejedloingredience5),
                            quantity = 0.5,
                            unit = context.getString(R.string.sestnastejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId15,
                            stuff = context.getString(R.string.sestnastejedloingredience6),
                            quantity = 1.0,
                            unit = context.getString(R.string.sestnastejedlojednotka6)
                        )
                    )

                    db.processDao().insert(FoodProcess(food = foodId15, step = 1, description = context.getString(R.string.sestnastejedlopopis1)))
                    db.processDao().insert(FoodProcess(food = foodId15, step = 2, description = context.getString(R.string.sestnastejedlopopis2)))
                    db.processDao().insert(FoodProcess(food = foodId15, step = 3, description = context.getString(R.string.sestnastejedlopopis3)))



                    /* ---------------------- 17. Zemiaky s kyslými uhorkami ---------------------- */
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId16,
                            stuff = context.getString(R.string.sedemnastejedloingredience1),
                            quantity = 500.0,
                            unit = context.getString(R.string.sedemnastejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId16,
                            stuff = context.getString(R.string.sedemnastejedloingredience2),
                            quantity = 3.0,
                            unit = context.getString(R.string.sedemnastejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId16,
                            stuff = context.getString(R.string.sedemnastejedloingredience3),
                            quantity = 1.0,
                            unit = context.getString(R.string.sedemnastejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId16,
                            stuff = context.getString(R.string.sedemnastejedloingredience4),
                            quantity = 1.0,
                            unit = context.getString(R.string.sedemnastejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId16,
                            stuff = context.getString(R.string.sedemnastejedloingredience5),
                            quantity = 0.5,
                            unit = context.getString(R.string.sedemnastejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId16,
                            stuff = context.getString(R.string.sedemnastejedloingredience6),
                            quantity = 0.3,
                            unit = context.getString(R.string.sedemnastejedlojednotka6)
                        )
                    )

                    db.processDao().insert(FoodProcess(food = foodId16, step = 1, description = context.getString(R.string.sedemnastejedlopopis1)))
                    db.processDao().insert(FoodProcess(food = foodId16, step = 2, description = context.getString(R.string.sedemnastejedlopopis2)))
                    db.processDao().insert(FoodProcess(food = foodId16, step = 3, description = context.getString(R.string.sedemnastejedlopopis3)))



                    /* --------------------------- 18. Mojo Rocho omáčka --------------------------- */
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId17,
                            stuff = context.getString(R.string.osemnastejedloingredience1),
                            quantity = 2.0,
                            unit = context.getString(R.string.osemnastejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId17,
                            stuff = context.getString(R.string.osemnastejedloingredience2),
                            quantity = 3.0,
                            unit = context.getString(R.string.osemnastejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId17,
                            stuff = context.getString(R.string.osemnastejedloingredience3),
                            quantity = 1.0,
                            unit = context.getString(R.string.osemnastejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId17,
                            stuff = context.getString(R.string.osemnastejedloingredience4),
                            quantity = 2.0,
                            unit = context.getString(R.string.osemnastejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId17,
                            stuff = context.getString(R.string.osemnastejedloingredience5),
                            quantity = 100.0,
                            unit = context.getString(R.string.osemnastejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId17,
                            stuff = context.getString(R.string.osemnastejedloingredience6),
                            quantity = 0.3,
                            unit = context.getString(R.string.osemnastejedlojednotka6)
                        )
                    )

                    db.processDao().insert(FoodProcess(food = foodId17, step = 1, description = context.getString(R.string.osemnastejedlopopis1)))
                    db.processDao().insert(FoodProcess(food = foodId17, step = 2, description = context.getString(R.string.osemnastejedlopopis2)))
                    db.processDao().insert(FoodProcess(food = foodId17, step = 3, description = context.getString(R.string.osemnastejedlopopis3)))



                    /* -------------------- 19. Zapekané cestoviny so syrom -------------------- */
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId18,
                            stuff = context.getString(R.string.devatnastejedloingredience1),
                            quantity = 400.0,
                            unit = context.getString(R.string.devatnastejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId18,
                            stuff = context.getString(R.string.devatnastejedloingredience2),
                            quantity = 200.0,
                            unit = context.getString(R.string.devatnastejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId18,
                            stuff = context.getString(R.string.devatnastejedloingredience3),
                            quantity = 150.0,
                            unit = context.getString(R.string.devatnastejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId18,
                            stuff = context.getString(R.string.devatnastejedloingredience4),
                            quantity = 1.0,
                            unit = context.getString(R.string.devatnastejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId18,
                            stuff = context.getString(R.string.devatnastejedloingredience5),
                            quantity = 1.0,
                            unit = context.getString(R.string.devatnastejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId18,
                            stuff = context.getString(R.string.devatnastejedloingredience6),
                            quantity = 1.0,
                            unit = context.getString(R.string.devatnastejedlojednotka6)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId18,
                            stuff = context.getString(R.string.devatnastejedloingredience7),
                            quantity = 2.0,
                            unit = context.getString(R.string.devatnastejedlojednotka7)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId18,
                            stuff = context.getString(R.string.devatnastejedloingredience8),
                            quantity = 1.0,
                            unit = context.getString(R.string.devatnastejedlojednotka8)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId18,
                            stuff = context.getString(R.string.devatnastejedloingredience9),
                            quantity = 80.0,
                            unit = context.getString(R.string.devatnastejedlojednotka9)
                        )
                    )

                    db.processDao().insert(FoodProcess(food = foodId18, step = 1, description = context.getString(R.string.devatnastejedlopopis1)))
                    db.processDao().insert(FoodProcess(food = foodId18, step = 2, description = context.getString(R.string.devatnastejedlopopis2)))
                    db.processDao().insert(FoodProcess(food = foodId18, step = 3, description = context.getString(R.string.devatnastejedlopopis3)))
                    db.processDao().insert(FoodProcess(food = foodId18, step = 4, description = context.getString(R.string.devatnastejedlopopis4)))
                    db.processDao().insert(FoodProcess(food = foodId18, step = 5, description = context.getString(R.string.devatnastejedlopopis5)))



                    /* ---------------------- 20. Čokoládové brownie ---------------------- */
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId19,
                            stuff = context.getString(R.string.dvadsatejedloingredience1),
                            quantity = 200.0,
                            unit = context.getString(R.string.dvadsatejedlojednotka1)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId19,
                            stuff = context.getString(R.string.dvadsatejedloingredience2),
                            quantity = 200.0,
                            unit = context.getString(R.string.dvadsatejedlojednotka2)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId19,
                            stuff = context.getString(R.string.dvadsatejedloingredience3),
                            quantity = 1.0,
                            unit = context.getString(R.string.dvadsatejedlojednotka3)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId19,
                            stuff = context.getString(R.string.dvadsatejedloingredience4),
                            quantity = 200.0,
                            unit = context.getString(R.string.dvadsatejedlojednotka4)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId19,
                            stuff = context.getString(R.string.dvadsatejedloingredience5),
                            quantity = 3.0,
                            unit = context.getString(R.string.dvadsatejedlojednotka5)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId19,
                            stuff = context.getString(R.string.dvadsatejedloingredience6),
                            quantity = 3.0,
                            unit = context.getString(R.string.dvadsatejedlojednotka6)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId19,
                            stuff = context.getString(R.string.dvadsatejedloingredience7),
                            quantity = 1.0,
                            unit = context.getString(R.string.dvadsatejedlojednotka7)
                        )
                    )
                    db.foodStuffDao().insertFoodStuff(
                        FoodStuff(
                            food = foodId19,
                            stuff = context.getString(R.string.dvadsatejedloingredience8),
                            quantity = 2.0,
                            unit = context.getString(R.string.dvadsatejedlojednotka8)
                        )
                    )

                    db.processDao().insert(FoodProcess(food = foodId19, step = 1, description = context.getString(R.string.dvadsatejedlopopis1)))
                    db.processDao().insert(FoodProcess(food = foodId19, step = 2, description = context.getString(R.string.dvadsatejedlopopis2)))
                    db.processDao().insert(FoodProcess(food = foodId19, step = 3, description = context.getString(R.string.dvadsatejedlopopis3)))
                    db.processDao().insert(FoodProcess(food = foodId19, step = 4, description = context.getString(R.string.dvadsatejedlopopis4)))
                    db.processDao().insert(FoodProcess(food = foodId19, step = 5, description = context.getString(R.string.dvadsatejedlopopis5)))
                    db.processDao().insert(FoodProcess(food = foodId19, step = 6, description = context.getString(R.string.dvadsatejedlopopis6)))
                    db.processDao().insert(FoodProcess(food = foodId19, step = 7, description = context.getString(R.string.dvadsatejedlopopis7)))
                    db.processDao().insert(FoodProcess(food = foodId19, step = 8, description = context.getString(R.string.dvadsatejedlopopis8)))
                    db.processDao().insert(FoodProcess(food = foodId19, step = 9, description = context.getString(R.string.dvadsatejedlopopis9)))
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
