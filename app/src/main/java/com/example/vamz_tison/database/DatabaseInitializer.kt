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
                    db.shoppingListDao().insert(ShoppingList(name = "Testovaci"))

                    //Prve jedlo
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
                            name = "Talianska paradajková polievka",
                            typeId = 1,
                            cookingTime = 30,
                            preparingTime = 10,
                            portions = 5,
                            calories = 90,
                            description = "Talianska paradajková polievka si zaslúži miesto na stole pre svoju jednoduchosť, výraznú chuť z čerstvých surovín.",
                            image = byteArray
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

                    //druhe jedlo
                    val bitmap1 = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.brokolicovapolievka
                    )

                    var quality1 = 90
                    var byteArray1: ByteArray

                    do {
                        val stream1 = ByteArrayOutputStream()
                        bitmap1.compress(Bitmap.CompressFormat.JPEG, quality1, stream1)
                        byteArray1 = stream1.toByteArray()
                        quality1 -= 5
                    } while (byteArray1.size > 500_000 && quality1 > 10)

                    db.foodDao().insert(
                        Food(
                            name = "Brokolicová polievka s guľôčkami",
                            typeId = 1,
                            cookingTime = 30,
                            preparingTime = 10,
                            portions = 4,
                            calories = 222,
                            description = "Výborná krémová brokolicová polievka, inú už nerobím.",
                            image = byteArray1
                        )
                    )

                    val foodId1 = 2

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId1, stuff = "brokolica", quantity = 250.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId1, stuff = "zemiaky", quantity = 400.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId1, stuff = "smotana na varenie", quantity = 200.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId1, stuff = "cesnak", quantity = 4.0, unit = "strúčiky"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId1, stuff = "cibuľa", quantity = 1.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId1, stuff = "bujón zeleninový", quantity = 1.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId1, stuff = "soľ", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId1, stuff = "korenie čierne mleté", quantity = 0.3, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId1, stuff = "olej", quantity = 1.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId1, stuff = "guľôčky do polievky", quantity = 100.0, unit = "g"))

                    db.processDao().insert(FoodProcess(food = foodId1, step = 1, description = "Vezmeme si polovicu brokolice, umyjeme ju a rozdelíme na ružičky. Ošúpeme, nakrájame cibuľu na drobno a zemiaky na malé kocky."))
                    db.processDao().insert(FoodProcess(food = foodId1, step = 2, description = "Cibuľu orestujeme na oleji. Pridáme zemiaky a ružičky brokolice. Smažíme 5–10 minút, troška môžeme podliať vodou."))
                    db.processDao().insert(FoodProcess(food = foodId1, step = 3, description = "Pridáme pretlačený cesnak a smažíme všetko spolu. Zalejeme vodou, cca 1 liter. Pridáme soľ, čierne korenie, zeleninový bujón."))
                    db.processDao().insert(FoodProcess(food = foodId1, step = 4, description = "Varíme do zmäknutia zemiakov a brokolice. Keď je všetko mäkké, rozmixujeme všetko ponorným mixérom."))
                    db.processDao().insert(FoodProcess(food = foodId1, step = 5, description = "Pridáme smotanu na varenie a necháme ešte prevariť. Dochutíme podľa potreby a odstavíme zo sporáka."))
                    db.processDao().insert(FoodProcess(food = foodId1, step = 6, description = "Podávame s kúpenými guľôčkami do polievky. Dobrú chuť."))

                    //tretie jedlo
                    val bitmap2 = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.sosovicovapolievka
                    )

                    var quality2 = 90
                    var byteArray2: ByteArray

                    do {
                        val stream2 = ByteArrayOutputStream()
                        bitmap2.compress(Bitmap.CompressFormat.JPEG, quality2, stream2)
                        byteArray2 = stream2.toByteArray()
                        quality2 -= 5
                    } while (byteArray2.size > 500_000 && quality2 > 10)

                    val foodId2 = 3

                    db.foodDao().insert(
                        Food(
                            name = "Šošovicová polievka s párkom",
                            typeId = 1,
                            cookingTime = 40,
                            preparingTime = 15,
                            portions = 4,
                            calories = 250,
                            description = "Klasická šošovicová polievka s párkom, hustá a výdatná, ideálna na chladné dni.",
                            image = byteArray2
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId2, stuff = "šošovica", quantity = 250.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId2, stuff = "mrkva", quantity = 1.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId2, stuff = "cibuľa", quantity = 1.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId2, stuff = "maslo", quantity = 2.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId2, stuff = "múka hladká", quantity = 2.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId2, stuff = "cesnak", quantity = 4.0, unit = "strúčiky"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId2, stuff = "bobkový list", quantity = 1.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId2, stuff = "paprika červená sladká mletá", quantity = 1.0, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId2, stuff = "korenie čierne mleté", quantity = 0.3, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId2, stuff = "soľ", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId2, stuff = "vývar zeleninový", quantity = 2000.0, unit = "ml"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId2, stuff = "majorán", quantity = 1.0, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId2, stuff = "párky", quantity = 3.0, unit = "ks"))

                    db.processDao().insert(FoodProcess(food = foodId2, step = 1, description = "Šošovicu prepláchneme a namočíme na hodinu do vody. Cibuľu nakrájame nadrobno a orestujeme na masle do sklovita. Pridáme múku a pripravíme zápražku. Do hotovej zápražky pridáme mletú červenú papriku, dávame pozor, aby neprihorela."))
                    db.processDao().insert(FoodProcess(food = foodId2, step = 2, description = "Zápražku pomaly zalievame vývarom a miešame metličkou, aby nevznikli hrudky. Pridáme namočenú šošovicu, nakrájanú mrkvu, bobkový list, čierne korenie a 2 strúčiky cesnaku. Varíme, občas premiešame."))
                    db.processDao().insert(FoodProcess(food = foodId2, step = 3, description = "Keď je šošovica mäkká, osolíme, pridáme majoránku, nakrájané párky a zvyšné 2 strúčiky cesnaku. Chvíľu povaríme a odstavíme."))

                    //4 jedlo
                    val bitmap3 = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.kurasbroskynou
                    )

                    var quality3 = 90
                    var byteArray3: ByteArray

                    do {
                        val stream3 = ByteArrayOutputStream()
                        bitmap3.compress(Bitmap.CompressFormat.JPEG, quality3, stream3)
                        byteArray3 = stream3.toByteArray()
                        quality3 -= 5
                    } while (byteArray3.size > 500_000 && quality3 > 10)

                    val foodId3 = 4

                    db.foodDao().insert(
                        Food(
                            name = "Glazované kura s broskyňou a zázvorom",
                            typeId = 3,
                            cookingTime = 30,
                            preparingTime = 15,
                            portions = 4,
                            calories = 350,
                            description = "Rýchle a chutné ázijské jedlo s kombináciou sladkých broskýň a pikantného zázvoru.",
                            image = byteArray3
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "kuracie prsia", quantity = 500.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "soľ", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "korenie čierne mleté", quantity = 0.3, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "škrob kukuričný", quantity = 1.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "múka hladká", quantity = 1.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "olej rastlinný", quantity = 2.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "zázvor čerstvý", quantity = 2.0, unit = "cm"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "cesnak", quantity = 2.0, unit = "strúčiky"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "broskyne v náleve", quantity = 3.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "omáčka sójová", quantity = 3.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "džem broskyňový", quantity = 2.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "vývar zeleninový", quantity = 200.0, unit = "ml"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "nálev z broskýň", quantity = 150.0, unit = "ml"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "čili (voliteľné)", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "cibuľka jarná", quantity = 2.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "sezam", quantity = 1.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "ryža jazmínová", quantity = 1.0, unit = "hrnček"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "voda", quantity = 1.5, unit = "hrnček"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId3, stuff = "list bobkový", quantity = 1.0, unit = "ks"))

                    db.processDao().insert(FoodProcess(food = foodId3, step = 1, description = "Kuracie prsia nakrájame na kocky, osolíme, okoreníme a obalíme v zmesi škrobu a múky."))
                    db.processDao().insert(FoodProcess(food = foodId3, step = 2, description = "Na panvici rozohrejeme olej a mäso opečieme dozlatista. Opečené mäso odložíme bokom."))
                    db.processDao().insert(FoodProcess(food = foodId3, step = 3, description = "Na tej istej panvici orestujeme nastrúhaný zázvor a pretlačený cesnak. Pridáme na plátky nakrájané broskyne, sójovú omáčku, džem, vývar a nálev z broskýň."))
                    db.processDao().insert(FoodProcess(food = foodId3, step = 4, description = "Omáčku krátko povaríme, vrátime mäso do panvice a necháme niekoľko minút variť, kým omáčka nezhustne a mäso nebude obalené lesklou glazúrou."))
                    db.processDao().insert(FoodProcess(food = foodId3, step = 5, description = "Ryžu dôkladne prepláchneme a necháme odkvapkať."))
                    db.processDao().insert(FoodProcess(food = foodId3, step = 6, description = "V hrnci vhodnom do rúry rozohrejeme olej, pridáme ryžu a krátko opečieme. Zalejeme vodou, pridáme soľ a bobkový list."))
                    db.processDao().insert(FoodProcess(food = foodId3, step = 7, description = "Varíme, kým sa voda takmer neodparí. Potom hrniec prikryjeme a vložíme do predhriatej rúry na 180 °C na 12 minút."))
                    db.processDao().insert(FoodProcess(food = foodId3, step = 8, description = "Podávame glazované kura s ryžou, posypané nasekanou jarnou cibuľkou a sezamom."))

                    //5 jedlo
                    val bitmap4 = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.kuracieplatkynamarinade
                    )

                    var quality4 = 90
                    var byteArray4: ByteArray

                    do {
                        val stream4 = ByteArrayOutputStream()
                        bitmap4.compress(Bitmap.CompressFormat.JPEG, quality4, stream4)
                        byteArray4 = stream4.toByteArray()
                        quality4 -= 5
                    } while (byteArray4.size > 500_000 && quality4 > 10)

                    val foodId4 = 5

                    db.foodDao().insert(
                        Food(
                            name = "Kuracie plátky v marináde",
                            typeId = 3,
                            cookingTime = 6,
                            preparingTime = 10,
                            portions = 4,
                            calories = 300,
                            description = "Rýchle a chutné kuracie plátky marinované v zmesi vajíčka, korenín a worcesterskej omáčky.",
                            image = byteArray4
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId4, stuff = "kuracie prsia", quantity = 4.0, unit = "plátky"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId4, stuff = "vajce", quantity = 1.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId4, stuff = "korenie karí", quantity = 1.0, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId4, stuff = "korenie biele mleté", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId4, stuff = "omáčka worcesterská", quantity = 1.0, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId4, stuff = "soľ", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId4, stuff = "olej", quantity = 2.0, unit = "PL"))

                    db.processDao().insert(FoodProcess(food = foodId4, step = 1, description = "Všetky prísady na marinádu dáme do misky a prešľaháme."))
                    db.processDao().insert(FoodProcess(food = foodId4, step = 2, description = "Do tejto marinády vložíme plátky mäsa a necháme marinovať 2–3 hodiny."))
                    db.processDao().insert(FoodProcess(food = foodId4, step = 3, description = "Potom ich na oleji na panvici opekáme z oboch strán asi po 3 minúty. Dlhšie netreba."))
                    db.processDao().insert(FoodProcess(food = foodId4, step = 4, description = "Podávame s ryžou, ktorú prelejeme trochou výpeku (ale nemusíme), a zeleninovým šalátom."))

                    //6 jedlo
                    val bitmap5 = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.pecenebataty
                    )

                    var quality5 = 90
                    var byteArray5: ByteArray

                    do {
                        val stream5 = ByteArrayOutputStream()
                        bitmap5.compress(Bitmap.CompressFormat.JPEG, quality5, stream5)
                        byteArray5 = stream5.toByteArray()
                        quality5 -= 5
                    } while (byteArray5.size > 500_000 && quality5 > 10)

                    val foodId5 = 6

                    db.foodDao().insert(
                        Food(
                            name = "Pečené bataty plnené fazuľou",
                            typeId = 3,
                            cookingTime = 40,
                            preparingTime = 15,
                            portions = 4,
                            calories = 350,
                            description = "Pečené sladké zemiaky sú dobré samy osebe. My sme k nim pridali fazuľu v paradajkovej omáčke. Vznikla tak výživná a chutná kombinácia.",
                            image = byteArray5
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId5, stuff = "bataty", quantity = 4.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId5, stuff = "fazuľa sterilizovaná", quantity = 400.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId5, stuff = "paradajky lúpané krájané", quantity = 400.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId5, stuff = "olej", quantity = 3.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId5, stuff = "cibuľa", quantity = 1.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId5, stuff = "cesnak", quantity = 4.0, unit = "strúčiky"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId5, stuff = "smotana kyslá", quantity = 4.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId5, stuff = "špenát baby", quantity = 1.0, unit = "hrsť"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId5, stuff = "vňať petržlenová", quantity = 1.0, unit = "hrsť"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId5, stuff = "paprička jalapeno", quantity = 1.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId5, stuff = "soľ", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId5, stuff = "korenie čierne mleté", quantity = 0.3, unit = "čl"))

                    db.processDao().insert(FoodProcess(food = foodId5, step = 1, description = "Umyté bataty rozložíme na plech, pokvapkáme 1 PL oleja a osolíme. Vložíme do rúry vyhriatej na 220 °C a pečieme 40 minút domäkka."))
                    db.processDao().insert(FoodProcess(food = foodId5, step = 2, description = "Medzitým ošúpeme cibuľu a cesnak, nakrájame ich nadrobno a restujeme na zvyšnom oleji, kým zosklovatejú. Potom pridáme lúpané krájané paradajky a prepláchnutú sterilizovanú fazuľu. Osolíme, okoreníme a varíme do zhustnutia."))
                    db.processDao().insert(FoodProcess(food = foodId5, step = 3, description = "Pečené bataty rozkrojíme a naplníme fazuľovou zmesou. Pridáme kyslú smotanu, špenát s petržlenovou vňaťou a kolieska papričky."))

                    //6.jedlo
                    val bitmap6 = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.paradajkovapolievka
                    )

                    var quality6 = 90
                    var byteArray6: ByteArray

                    do {
                        val stream6 = ByteArrayOutputStream()
                        bitmap6.compress(Bitmap.CompressFormat.JPEG, quality6, stream6)
                        byteArray6 = stream6.toByteArray()
                        quality6 -= 5
                    } while (byteArray6.size > 500_000 && quality6 > 10)

                    val foodId6 = 7

                    db.foodDao().insert(
                        Food(
                            name = "Pasta alla Norma",
                            typeId = 3,
                            cookingTime = 20,
                            preparingTime = 30,
                            portions = 4,
                            calories = 400,
                            description = "Jedna zo sicílskych kulinárskych klasík. Názov dostala po rodákovi z Catanie, Vinzenzovi Bellinim, ktorý zložil operu Norma.",
                            image = byteArray6
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId6, stuff = "baklažán", quantity = 750.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId6, stuff = "soľ", quantity = 1.0, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId6, stuff = "cesnak", quantity = 2.0, unit = "strúčiky"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId6, stuff = "tymian", quantity = 3.0, unit = "vetvičky"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId6, stuff = "olej olivový", quantity = 3.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId6, stuff = "passata paradajková", quantity = 700.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId6, stuff = "korenie čierne mleté", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId6, stuff = "cukor", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId6, stuff = "vločky čili", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId6, stuff = "bazalka", quantity = 0.5, unit = "zväzok"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId6, stuff = "cestoviny rigatoni alebo penne", quantity = 500.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId6, stuff = "ricotta salata alebo pecorino", quantity = 150.0, unit = "g"))

                    db.processDao().insert(FoodProcess(food = foodId6, step = 1, description = "Baklažány umyjeme, necháme odkvapkať a nakrájame na hrubé kocky. Kocky baklažánu dáme do sitka, bohato posypeme soľou a necháme asi 20 minút pustiť šťavu."))
                    db.processDao().insert(FoodProcess(food = foodId6, step = 2, description = "Cesnak ošúpeme a jemne nasekáme. Umyjeme tymian, osušíme a otrháme lístky zo stoniek. Rozohrejeme olivový olej v hrnci a cesnak spolu s tymianovými lístkami v ňom restujeme asi 2 minúty na nízkom až strednom ohni."))
                    db.processDao().insert(FoodProcess(food = foodId6, step = 3, description = "Zalejeme paradajkovou passatou a opatrne dochutíme štipkou soli, korenia a cukru. Prípadne pridáme čili. Umyjeme bazalku, osušíme, asi polovicu z nej aj so stonkami pridáme do paradajkovej zmesi a varíme v otvorenom hrnci asi 20–25 minút za občasného miešania."))
                    db.processDao().insert(FoodProcess(food = foodId6, step = 4, description = "Kocky baklažánu krátko opláchneme a osušíme kuchynskou utierkou. V panvici rozohrejeme dostatok olivového oleja na vyprážanie a baklažán v ňom po častiach 5–7 minút opekáme do chrumkava. Dochutíme štipkou soli a opekáme ďalšie 2–3 minúty. Opečené kocky baklažánu vyberieme z panvice a necháme odkvapkať na kuchynskom papieri."))
                    db.processDao().insert(FoodProcess(food = foodId6, step = 5, description = "Keď paradajková omáčka zhustne, pridáme do nej kocky baklažánu, všetko premiešame a ešte raz dochutíme soľou, korením a podľa chuti cukrom. Všetko spolu povaríme ďalších 5 minút."))
                    db.processDao().insert(FoodProcess(food = foodId6, step = 6, description = "Medzitým uvaríme cestoviny v osolenej vode al dente. Zvyšnú bazalku nasekáme na ozdobu a ricottu nahrubo nastrúhame. Keď sú cestoviny uvarené, scedíme ich, dáme priamo do baklažánovo-paradajkovej omáčky a všetko premiešame. Rozdelíme na taniere, ozdobíme strúhaným syrom ricotta a bazalkou a podávame."))


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
