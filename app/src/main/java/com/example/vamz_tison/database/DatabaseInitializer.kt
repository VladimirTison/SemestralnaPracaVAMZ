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
                    db.foodTypeDao().insert(FoodType(name = context.getString(R.string.polievka)))
                    db.foodTypeDao().insert(FoodType(name = context.getString(R.string.predjedlo)))
                    db.foodTypeDao().insert(FoodType(name = context.getString(R.string.hlavn_jedlo)))
                    db.foodTypeDao().insert(FoodType(name = "Dezert"))
                    db.foodTypeDao().insert(FoodType(name = "Príloha"))
                    db.shoppingListDao().insert(ShoppingList(name = "Nákupný zoznam"))

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

                    //7 jedlo
                    val bitmap7 = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.sicilskacaponata
                    )

                    var quality7 = 90
                    var byteArray7: ByteArray

                    do {
                        val stream7 = ByteArrayOutputStream()
                        bitmap7.compress(Bitmap.CompressFormat.JPEG, quality7, stream7)
                        byteArray7 = stream7.toByteArray()
                        quality7 -= 5
                    } while (byteArray7.size > 500_000 && quality7 > 10)

                    val foodId7 = 8

                    db.foodDao().insert(
                        Food(
                            name = "Sicílska caponata",
                            typeId = 2,
                            cookingTime = 45,
                            preparingTime = 15,
                            portions = 4,
                            calories = 250,
                            description = "Caponata sa na talianskom ostrove Sicília podáva ako zeleninové predjedlo alebo ako príloha k mäsu aj rybám.",
                            image = byteArray7
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId7, stuff = "baklažán", quantity = 400.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId7, stuff = "zeler stonkový", quantity = 3.0, unit = "stonky"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId7, stuff = "cibuľa červená", quantity = 1.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId7, stuff = "cesnak", quantity = 2.0, unit = "strúčiky"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId7, stuff = "paradajky koktailové", quantity = 300.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId7, stuff = "olivy čierne", quantity = 2.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId7, stuff = "kapary", quantity = 2.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId7, stuff = "bazalka", quantity = 0.5, unit = "hrnček"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId7, stuff = "ocot vínny", quantity = 2.5, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId7, stuff = "cukor", quantity = 0.5, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId7, stuff = "oriešky píniové", quantity = 2.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId7, stuff = "olej olivový", quantity = 6.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId7, stuff = "korenie čierne mleté", quantity = 0.3, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId7, stuff = "soľ", quantity = 0.5, unit = "čl"))

                    db.processDao().insert(FoodProcess(food = foodId7, step = 1, description = "Zeler umyjeme, odrežeme horné a dolné konce a nakrájame na jemné pásiky. Olúpanú cibuľu a cesnak nakrájame nadrobno. Baklažán umyjeme, nakrájame na tenké plátky a rozrežeme na osminky. Paradajky nakrájame na malé kocky."))
                    db.processDao().insert(FoodProcess(food = foodId7, step = 2, description = "Baklažány opečieme vo dvoch dávkach vždy na 2 PL olivového oleja. Vyberieme ich z panvice, rozohrejeme zvyšné 2 PL oleja, krátko opečieme cibuľu a cesnak, pridáme zeler, paradajky a baklažány, osolíme a okoreníme a na miernom ohni budeme dusiť 5 minút zakryté pokrievkou."))
                    db.processDao().insert(FoodProcess(food = foodId7, step = 3, description = "Tento čas využijeme na rozpolenie olív, nasekanie bazalky a opláchnutie kapár. Pridáme tieto prísady, ako aj ocot a cukor a budeme variť ďalších 15 minút, občas premiešame. Na malej panvici nasucho opražíme píniové oriešky a pred podávaním nimi jedlo posypeme. Podávame s bielym chlebom."))


                    //8. jedlo
                    val bitmap8 = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.kacaciapecen
                    )

                    var quality8 = 90
                    var byteArray8: ByteArray

                    do {
                        val stream8 = ByteArrayOutputStream()
                        bitmap8.compress(Bitmap.CompressFormat.JPEG, quality8, stream8)
                        byteArray8 = stream8.toByteArray()
                        quality8 -= 5
                    } while (byteArray8.size > 500_000 && quality8 > 10)

                    val foodId8 = 9

                    db.foodDao().insert(
                        Food(
                            name = "Kačacia pečeň s cibuľovým čatní",
                            typeId = 2,
                            cookingTime = 30,
                            preparingTime = 15,
                            portions = 4,
                            calories = 350,
                            description = "Kačacia pečeň s cibuľovým čatní je lahodné predjedlo, ktoré kombinuje bohatú chuť pečene s výrazným sladkokyslým čatní.",
                            image = byteArray8
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId8, stuff = "pečeň kačacia", quantity = 300.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId8, stuff = "korenie čierne mleté", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId8, stuff = "soľ", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId8, stuff = "cibuľa červená", quantity = 2.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId8, stuff = "ocot balzamikový", quantity = 100.0, unit = "ml"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId8, stuff = "víno červené", quantity = 300.0, unit = "ml"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId8, stuff = "omáčka worcesterská", quantity = 1.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId8, stuff = "med", quantity = 50.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId8, stuff = "olej", quantity = 2.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId8, stuff = "bageta", quantity = 1.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId8, stuff = "poľníček", quantity = 1.0, unit = "hrsť"))

                    db.processDao().insert(FoodProcess(food = foodId8, step = 1, description = "Kačaciu pečeň nakrájame na centimetrové plátky, na silnom ohni prudko nasucho opečieme z oboch strán asi 2 minúty. Dochutíme soľou a korením."))
                    db.processDao().insert(FoodProcess(food = foodId8, step = 2, description = "Červenú cibuľu nakrájame na väčšie kusy a zľahka orestujeme na oleji. Podlejeme balzamikovým octom, vínom, pridáme med a worcesterskú omáčku. Zredukujeme tekutinu na minimum, až kým nedostaneme husté sladkokyslé čatní."))
                    db.processDao().insert(FoodProcess(food = foodId8, step = 3, description = "Bagetu nakrájame na plátky a nasucho ich opečieme. Na každý tanier dáme dva plátky bagety, kačaciu pečeň a pokvapkáme výpekom z nej."))
                    db.processDao().insert(FoodProcess(food = foodId8, step = 4, description = "Servírujeme s cibuľovým čatní a lístkami poľníčka."))

                    //9. jedlo
                    val bitmap9 = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.zapekanefigy
                    )

                    var quality9 = 90
                    var byteArray9: ByteArray

                    do {
                        val stream9 = ByteArrayOutputStream()
                        bitmap9.compress(Bitmap.CompressFormat.JPEG, quality9, stream9)
                        byteArray9 = stream9.toByteArray()
                        quality9 -= 5
                    } while (byteArray9.size > 500_000 && quality9 > 10)

                    val foodId9 = 10

                    db.foodDao().insert(
                        Food(
                            name = "Zapekané figy s medom, pistáciami a kozím syrom",
                            typeId = 2,
                            cookingTime = 8,
                            preparingTime = 10,
                            portions = 4,
                            calories = 200,
                            description = "Jednoduchý a chutný recept na zapekané figy plnené kozím syrom, posypané pistáciami a pokvapkané medom.",
                            image = byteArray9
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId9, stuff = "figy čerstvé", quantity = 8.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId9, stuff = "syr kozí (Lučina)", quantity = 100.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId9, stuff = "med", quantity = 2.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId9, stuff = "pistácie", quantity = 2.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId9, stuff = "soľ", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId9, stuff = "korenie čierne mleté", quantity = 0.3, unit = "čl"))

                    db.processDao().insert(FoodProcess(food = foodId9, step = 1, description = "Čerstvé figy umyjeme, osušíme a narežeme do kríža alebo prerežeme po dĺžke na polovicu. Uložíme ich na pekáč vystlaný papierom na pečenie."))
                    db.processDao().insert(FoodProcess(food = foodId9, step = 2, description = "Do každej figy vložíme trošku kozieho syra, posypeme nasekanými pistáciami, pokvapkáme medom a zľahka ochutíme soľou a čiernym korením."))
                    db.processDao().insert(FoodProcess(food = foodId9, step = 3, description = "V rúre s funkciou gril zapekáme na najvyššom stupni približne 5–8 minút. Po upečení môžeme ešte jemne posypať soľou a korením a opäť pokvapkať medom."))
                    db.processDao().insert(FoodProcess(food = foodId9, step = 4, description = "Podávame s nasucho opečeným domácim kváskovým chlebom."))


                    //10
                    val bitmap10 = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.tvarohoznik
                    )

                    var quality10 = 90
                    var byteArray10: ByteArray

                    do {
                        val stream10 = ByteArrayOutputStream()
                        bitmap10.compress(Bitmap.CompressFormat.JPEG, quality10, stream10)
                        byteArray10 = stream10.toByteArray()
                        quality10 -= 5
                    } while (byteArray10.size > 500_000 && quality10 > 10)

                    val foodId10 = 11

                    db.foodDao().insert(
                        Food(
                            name = "Výborný tvarožník",
                            typeId = 4,
                            cookingTime = 35,
                            preparingTime = 15,
                            portions = 9,
                            calories = 250,
                            description = "Jemný šťavnatý tvarohový koláč, ktorý sa oplatí upiecť. Zmizol rýchlosťou blesku...",
                            image = byteArray10
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId10, stuff = "tvaroh tučný hrudkovitý", quantity = 600.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId10, stuff = "vajcia", quantity = 3.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId10, stuff = "cukor kryštálový jemný", quantity = 3.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId10, stuff = "cukor vanilkový", quantity = 3.0, unit = "KL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId10, stuff = "krupica detská", quantity = 3.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId10, stuff = "kôra citrónová nastrúhaná", quantity = 1.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId10, stuff = "soľ", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId10, stuff = "smotana kyslá 16%", quantity = 3.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId10, stuff = "hrozienka", quantity = 3.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId10, stuff = "cukor práškový", quantity = 1.0, unit = "PL"))

                    db.processDao().insert(FoodProcess(food = foodId10, step = 1, description = "Hrozienka zalejeme vriacou vodou a necháme 5–10 minút napučať. Potom scedíme a osušíme."))
                    db.processDao().insert(FoodProcess(food = foodId10, step = 2, description = "Oddelíme žĺtky od bielkov. Z bielkov a štipky soli vyšľaháme tuhý sneh."))
                    db.processDao().insert(FoodProcess(food = foodId10, step = 3, description = "V miske vymiešame tvaroh, žĺtky, soľ, cukry, krupicu, smotanu a citrónovú kôru."))
                    db.processDao().insert(FoodProcess(food = foodId10, step = 4, description = "Do tvarohovej masy primiešame hrozienka a následne opatrne vmiešame sneh."))
                    db.processDao().insert(FoodProcess(food = foodId10, step = 5, description = "Cesto rozotrieme do formy 21×21 cm vystlanej papierom na pečenie."))
                    db.processDao().insert(FoodProcess(food = foodId10, step = 6, description = "Pečieme v predhriatej rúre na 175–180 °C približne 35 minút."))
                    db.processDao().insert(FoodProcess(food = foodId10, step = 7, description = "Po vychladnutí posypeme práškovým cukrom a podávame."))


                    //11
                    val bitmap11 = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.makronky
                    )

                    var quality11 = 90
                    var byteArray11: ByteArray

                    do {
                        val stream11 = ByteArrayOutputStream()
                        bitmap11.compress(Bitmap.CompressFormat.JPEG, quality11, stream11)
                        byteArray11 = stream11.toByteArray()
                        quality11 -= 5
                    } while (byteArray11.size > 500_000 && quality11 > 10)

                    val foodId11 = 12

                    db.foodDao().insert(
                        Food(
                            name = "Citrónovo - makové makrónky",
                            typeId = 4,
                            cookingTime = 18,
                            preparingTime = 30,
                            portions = 25,
                            calories = 120,
                            description = "Makrónky s jemnou citrónovou plnkou a makovým korpusom pripravené talianskou metódou s cukrovým rozvarom.",
                            image = byteArray11
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId11, stuff = "mandľová múka", quantity = 85.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId11, stuff = "mak mletý", quantity = 10.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId11, stuff = "cukor práškový", quantity = 95.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId11, stuff = "cukor kryštálový", quantity = 95.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId11, stuff = "voda", quantity = 35.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId11, stuff = "bielky", quantity = 70.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId11, stuff = "soľ", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId11, stuff = "mascarpone", quantity = 120.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId11, stuff = "čokoláda biela", quantity = 90.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId11, stuff = "lemon curd", quantity = 1.5, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId11, stuff = "žĺtky", quantity = 6.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId11, stuff = "cukor krupicový", quantity = 105.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId11, stuff = "šťava citrónová", quantity = 100.0, unit = "ml"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId11, stuff = "maslo", quantity = 75.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId11, stuff = "kôra citrónová", quantity = 2.0, unit = "ks"))

                    db.processDao().insert(FoodProcess(food = foodId11, step = 1, description = "Pripravíme lemon curd: všetky suroviny zmiešame a nad parou šľaháme do zhustnutia. Trvá to približne 45 minút."))
                    db.processDao().insert(FoodProcess(food = foodId11, step = 2, description = "Mandľovú múku preosejeme s práškovým cukrom a mletým makom. Pridáme 35 g nevyšľahaného bielka a premiešame na hustú pastu."))
                    db.processDao().insert(FoodProcess(food = foodId11, step = 3, description = "Kryštálový cukor varíme s vodou, kým zmes nedosiahne 116 °C."))
                    db.processDao().insert(FoodProcess(food = foodId11, step = 4, description = "Druhý bielok (35 g) so štipkou soli vyšľaháme na penu. Za stáleho šľahania pomaly prilievame horúci cukrový sirup a vyšľaháme na tuhý sneh."))
                    db.processDao().insert(FoodProcess(food = foodId11, step = 5, description = "Sneh na trikrát vmiešame do mandľovej pasty, kým zmes nesteká plynulo zo stierky."))
                    db.processDao().insert(FoodProcess(food = foodId11, step = 6, description = "Zmes naplníme do cukrárskeho vrecka a nastriekame na podložku."))
                    db.processDao().insert(FoodProcess(food = foodId11, step = 7, description = "Pečieme v rúre predhriatej na 140 °C (ventilátor) približne 18 minút."))
                    db.processDao().insert(FoodProcess(food = foodId11, step = 8, description = "Na plnku roztopíme bielu čokoládu, necháme vychladnúť a postupne zašľaháme do mascarpone. Pridáme lemon curd a krátko premixujeme."))
                    db.processDao().insert(FoodProcess(food = foodId11, step = 9, description = "Krém necháme zatuhnúť v chladničke, potom naplníme do vrecka a plníme makrónky."))
                    db.processDao().insert(FoodProcess(food = foodId11, step = 10, description = "Makrónky môžeme ozdobiť rozpustenou bielou čokoládou, lyofilizovanými malinami a makom."))

                    //12
                    val bitmap12 = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.mrkvovykolac
                    )

                    var quality12 = 90
                    var byteArray12: ByteArray

                    do {
                        val stream12 = ByteArrayOutputStream()
                        bitmap12.compress(Bitmap.CompressFormat.JPEG, quality12, stream12)
                        byteArray12 = stream12.toByteArray()
                        quality12 -= 5
                    } while (byteArray12.size > 500_000 && quality12 > 10)

                    val foodId12 = 13

                    db.foodDao().insert(
                        Food(
                            name = "Mrkvový koláč bez lepku so smotanou a orechmi",
                            typeId = 4,
                            cookingTime = 30,
                            preparingTime = 20,
                            portions = 15,
                            calories = 220,
                            description = "Bezlepkový mrkvový koláč s gréckym jogurtom, jablkovým pyré a škoricou, ozdobený smotanovým krémom, orechmi a pomarančovou kôrou.",
                            image = byteArray12
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId12, stuff = "múka bezgluténová Promix PK", quantity = 140.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId12, stuff = "vajcia", quantity = 4.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId12, stuff = "orechy vlašské", quantity = 30.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId12, stuff = "jogurt grécky biely", quantity = 160.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId12, stuff = "pyré jablkové", quantity = 60.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId12, stuff = "mrkva", quantity = 120.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId12, stuff = "cukor trstinový", quantity = 85.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId12, stuff = "škorica mletá", quantity = 1.0, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId12, stuff = "prášok kypriaci bez lepku", quantity = 2.0, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId12, stuff = "smotana kyslá", quantity = 300.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId12, stuff = "med", quantity = 3.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId12, stuff = "kôra pomarančová", quantity = 1.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId12, stuff = "mäta", quantity = 1.0, unit = "hrsť"))

                    db.processDao().insert(FoodProcess(food = foodId12, step = 1, description = "Vo väčšej mise vyšľaháme spolu vajcia s cukrom do nadýchanej peny. Pridáme jogurt a jablkové pyré a prešľaháme."))
                    db.processDao().insert(FoodProcess(food = foodId12, step = 2, description = "Pridáme postrúhanú mrkvu a nasekané orechy."))
                    db.processDao().insert(FoodProcess(food = foodId12, step = 3, description = "Do zmesi zapracujeme preosiatu múku zmiešanú so škoricou a kypriacim práškom bez lepku."))
                    db.processDao().insert(FoodProcess(food = foodId12, step = 4, description = "Cesto vylejeme na plech vystlaný papierom na pečenie s rozmermi asi 25x30 cm."))
                    db.processDao().insert(FoodProcess(food = foodId12, step = 5, description = "Pečieme v predhriatej rúre na 180 °C približne 25–30 minút."))
                    db.processDao().insert(FoodProcess(food = foodId12, step = 6, description = "Po upečení necháme vychladnúť a potom naň rovnomerne rozotrieme kyslú smotanu vymiešanú s medom."))
                    db.processDao().insert(FoodProcess(food = foodId12, step = 7, description = "Vložíme do chladničky a necháme trochu stuhnúť."))
                    db.processDao().insert(FoodProcess(food = foodId12, step = 8, description = "Pred podávaním posypeme po stranách posekanými vlašskými orechmi a pomarančovou kôrou. Ozdobíme lístkami čerstvej mäty."))

                    //13
                    val bitmap13 = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.orechovy
                    )

                    var quality13 = 90
                    var byteArray13: ByteArray

                    do {
                        val stream13 = ByteArrayOutputStream()
                        bitmap13.compress(Bitmap.CompressFormat.JPEG, quality13, stream13)
                        byteArray13 = stream13.toByteArray()
                        quality13 -= 5
                    } while (byteArray13.size > 500_000 && quality13 > 10)

                    val foodId13 = 14

                    db.foodDao().insert(
                        Food(
                            name = "Orechovo - vanilkové rezy",
                            typeId = 4,
                            cookingTime = 30,
                            preparingTime = 40,
                            portions = 60,
                            calories = 300,
                            description = "Slávnostný orechový zákusok na Veľkú noc s kombináciou vanilkových a orechových plátov, plnený vanilkovým krémom a čučoriedkovým džemom, zakončený čokoládovou polevou.",
                            image = byteArray13
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId13, stuff = "vajce", quantity = 12.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId13, stuff = "cukor kryštálový", quantity = 9.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId13, stuff = "cukor vanilkový", quantity = 2.0, unit = "bal"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId13, stuff = "olej", quantity = 12.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId13, stuff = "voda", quantity = 10.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId13, stuff = "múka polohrubá", quantity = 15.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId13, stuff = "prášok do pečiva", quantity = 3.0, unit = "ČL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId13, stuff = "orechy mleté", quantity = 5.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId13, stuff = "mlieko", quantity = 1000.0, unit = "ml"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId13, stuff = "Zlatý klas", quantity = 4.0, unit = "bal"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId13, stuff = "maslo", quantity = 500.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId13, stuff = "likér vaječný", quantity = 50.0, unit = "ml"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId13, stuff = "džem čučoriedkový", quantity = 1.0, unit = "podľa potreby"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId13, stuff = "čokoláda", quantity = 200.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId13, stuff = "Cera", quantity = 80.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId13, stuff = "orechy na ozdobu", quantity = 1.0, unit = "podľa potreby"))

                    db.processDao().insert(FoodProcess(food = foodId13, step = 1, description = "Pripravíme si dve vanilkové piškóty: Vajíčka vyšľaháme s kryštálovým a vanilkovým cukrom do hustej peny, pridáme vodu, olej, múku s práškom do pečiva. Cesto rozotrieme na plech s papierom na pečenie a upečieme piškótu. Tak isto upečieme druhú piškótu."))
                    db.processDao().insert(FoodProcess(food = foodId13, step = 2, description = "Pripravíme orechový plát: Vajíčka vyšľaháme s cukrom do hustej peny, pridáme vodu, olej, múku s práškom do pečiva a pomleté orechy. Cesto rozotrieme na plech s papierom na pečenie a upečieme piškótu."))
                    db.processDao().insert(FoodProcess(food = foodId13, step = 3, description = "Krém: Časť mlieka odoberieme a rozmiešame v ňom Zlatý klas. Zvyšné mlieko s cukrom a mletou vanilkou dáme variť. Prilejeme v mlieku rozmiešaný pudingový prášok. Uvaríme hustý puding. Necháme vychladiť za občasného miešania. Nakoniec do pudingu zašľaháme po častiach maslo a vaječný likér. Vyšľaháme hladký krém."))
                    db.processDao().insert(FoodProcess(food = foodId13, step = 4, description = "Skladanie rezu: Z krému odoberieme 3 PL a odložíme bokom. Zbytok krému rozdelíme na dve rovnaké časti. Jeden vanilkový prvý plát potrieme jednou polovicou krému. Orechový plát po upečení necháme na papieri na pečenie, potrieme ho čučoriedkovým džemom a pomocou papiera preklopíme na krém. Papier na pečenie stiahneme z orechového plátu a plát potrieme znova čučoriedkovým džemom. Na džem navrstvíme druhú časť krému. Na krém položíme druhý vanilkový plát. Zaťažíme a odložíme do chladu."))
                    db.processDao().insert(FoodProcess(food = foodId13, step = 5, description = "Dokončenie: Kým zákusok tuhne pripravíme si polevu. Čokoládu s Cerou roztopíme spolu nad parou. Vrch zákusku potrieme jemne krémom, ktorý sme si odložili (3 polievkové lyžice). Robíme to preto, aby sa čokoládová poleva nevpila do korpusu. Takto pripravený rez polejeme čokoládovou polevou."))
                    db.processDao().insert(FoodProcess(food = foodId13, step = 6, description = "Po stuhnutí nakrájame horúcim nožom na rezy. Ozdobíme orechom."))

                    //14
                    val bitmap14 = BitmapFactory.decodeResource(context.resources, R.drawable.zapecenezemiakystekvicou)
                    var quality14 = 90
                    var byteArray14: ByteArray

                    do {
                        val stream14 = ByteArrayOutputStream()
                        bitmap14.compress(Bitmap.CompressFormat.JPEG, quality14, stream14)
                        byteArray14 = stream14.toByteArray()
                        quality14 -= 5
                    } while (byteArray14.size > 500_000 && quality14 > 10)

                    val foodId14 = 15

                    db.foodDao().insert(Food(
                        name = "Zemiaky pečené s tekvicou",
                        typeId = 5,
                        cookingTime = 40,
                        preparingTime = 10,
                        portions = 4,
                        calories = 180,
                        description = "Jednoduchá a chutná príloha z pečených zemiakov a tekvice.",
                        image = byteArray14
                    ))

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId14, stuff = "zemiaky", quantity = 600.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId14, stuff = "tekvica hokkaido", quantity = 400.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId14, stuff = "soľ", quantity = 1.0, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId14, stuff = "paprika červená mletá", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId14, stuff = "korenie čierne mleté", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId14, stuff = "korenie na pečené zemiaky", quantity = 1.0, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId14, stuff = "vňať petržlenová", quantity = 1.0, unit = "hrsť"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId14, stuff = "olej", quantity = 2.0, unit = "PL"))

                    db.processDao().insert(FoodProcess(food = foodId14, step = 1, description = "Predvarené ošúpané zemiaky nakrájame na šestiny a spolu s väčšími kúskami tekvice uložíme na plech."))
                    db.processDao().insert(FoodProcess(food = foodId14, step = 2, description = "Pokvapkáme olejom, osolíme, okoreníme a pridáme petržlen."))
                    db.processDao().insert(FoodProcess(food = foodId14, step = 3, description = "Pečieme vo vyhriatej rúre na 180 °C približne 40 minút."))

                    //15
                    val bitmap15 = BitmapFactory.decodeResource(context.resources, R.drawable.porshrozienkami)
                    var quality15 = 90
                    var byteArray15: ByteArray

                    do {
                        val stream15 = ByteArrayOutputStream()
                        bitmap15.compress(Bitmap.CompressFormat.JPEG, quality15, stream15)
                        byteArray15 = stream15.toByteArray()
                        quality15 -= 5
                    } while (byteArray15.size > 500_000 && quality15 > 10)

                    val foodId15 = 16

                    db.foodDao().insert(Food(
                        name = "Pór s hrozienkami ako príloha",
                        typeId = 5,
                        cookingTime = 15,
                        preparingTime = 10,
                        portions = 2,
                        calories = 130,
                        description = "Sladko-slaná príloha z duseného póru, cesnaku a hrozienok.",
                        image = byteArray15
                    ))

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId15, stuff = "pór", quantity = 2.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId15, stuff = "olej", quantity = 1.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId15, stuff = "cesnak", quantity = 2.0, unit = "strúčiky"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId15, stuff = "hrozienka", quantity = 2.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId15, stuff = "soľ", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId15, stuff = "ocot balzamikový", quantity = 1.0, unit = "čl"))

                    db.processDao().insert(FoodProcess(food = foodId15, step = 1, description = "Pór nakrájame na kolieska a sparíme."))
                    db.processDao().insert(FoodProcess(food = foodId15, step = 2, description = "Na oleji opečieme cesnak, pridáme pór, hrozienka, dusíme."))
                    db.processDao().insert(FoodProcess(food = foodId15, step = 3, description = "Dochutíme soľou a balzamikovým octom."))

                    //16
                    val bitmap16 = BitmapFactory.decodeResource(context.resources, R.drawable.zemiakysuhormai)
                    var quality16 = 90
                    var byteArray16: ByteArray

                    do {
                        val stream16 = ByteArrayOutputStream()
                        bitmap16.compress(Bitmap.CompressFormat.JPEG, quality16, stream16)
                        byteArray16 = stream16.toByteArray()
                        quality16 -= 5
                    } while (byteArray16.size > 500_000 && quality16 > 10)

                    val foodId16 = 17

                    db.foodDao().insert(Food(
                        name = "Zemiaky s kyslými uhorkami",
                        typeId = 5,
                        cookingTime = 20,
                        preparingTime = 10,
                        portions = 3,
                        calories = 160,
                        description = "Teplá príloha z varených zemiakov, kyslých uhoriek a cibule.",
                        image = byteArray16
                    ))

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId16, stuff = "zemiaky", quantity = 500.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId16, stuff = "uhorky kyslé", quantity = 3.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId16, stuff = "cibuľa", quantity = 1.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId16, stuff = "olej", quantity = 1.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId16, stuff = "soľ", quantity = 0.5, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId16, stuff = "korenie čierne mleté", quantity = 0.3, unit = "čl"))

                    db.processDao().insert(FoodProcess(food = foodId16, step = 1, description = "Uvarené zemiaky nakrájame na kolieska."))
                    db.processDao().insert(FoodProcess(food = foodId16, step = 2, description = "Na oleji speníme cibuľu, pridáme zemiaky, premiešame."))
                    db.processDao().insert(FoodProcess(food = foodId16, step = 3, description = "Dochutíme korením a vmiešame nakrájané uhorky."))


                    //17
                    val bitmap17 = BitmapFactory.decodeResource(context.resources, R.drawable.mojorocho)
                    var quality17 = 90
                    var byteArray17: ByteArray

                    do {
                        val stream17 = ByteArrayOutputStream()
                        bitmap17.compress(Bitmap.CompressFormat.JPEG, quality17, stream17)
                        byteArray17 = stream17.toByteArray()
                        quality17 -= 5
                    } while (byteArray17.size > 500_000 && quality17 > 10)

                    val foodId17 = 18

                    db.foodDao().insert(Food(
                        name = "Omáčka Mojo Rocho",
                        typeId = 5,
                        cookingTime = 0,
                        preparingTime = 10,
                        portions = 6,
                        calories = 90,
                        description = "Tradičná pikantná červená omáčka z Kanárskych ostrovov, ideálna k zemiakom alebo grilovanému mäsu.",
                        image = byteArray17
                    ))

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId17, stuff = "červená paprika mletá", quantity = 2.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId17, stuff = "cesnak", quantity = 3.0, unit = "strúčiky"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId17, stuff = "soľ", quantity = 1.0, unit = "čl"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId17, stuff = "ocot vínny", quantity = 2.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId17, stuff = "olej olivový", quantity = 100.0, unit = "ml"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId17, stuff = "rímska rasca", quantity = 0.3, unit = "čl"))

                    db.processDao().insert(FoodProcess(food = foodId17, step = 1, description = "V mažiari roztlačíme cesnak, papriku, soľ a rascu."))
                    db.processDao().insert(FoodProcess(food = foodId17, step = 2, description = "Prilejeme ocot a pomaly vmiešavame olivový olej."))
                    db.processDao().insert(FoodProcess(food = foodId17, step = 3, description = "Podávame k zemiakom, mäsu alebo rybám."))


                    //18
                    val bitmap18 = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.zapekanecestoviny
                    )

                    var quality18 = 90
                    var byteArray18: ByteArray

                    do {
                        val stream18 = ByteArrayOutputStream()
                        bitmap18.compress(Bitmap.CompressFormat.JPEG, quality18, stream18)
                        byteArray18 = stream18.toByteArray()
                        quality18 -= 5
                    } while (byteArray18.size > 500_000 && quality18 > 10)

                    val foodId18 = 19

                    db.foodDao().insert(
                        Food(
                            name = "Zapekané cestoviny so syrom a semienkami",
                            typeId = 3,
                            cookingTime = 25,
                            preparingTime = 20,
                            portions = 4,
                            calories = 450,
                            description = "Chrumkavé zapekané cestoviny s tekvicou, čerstvým syrom, čedarom a posypané semienkami a mandľami.",
                            image = byteArray18
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId18, stuff = "cestoviny", quantity = 400.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId18, stuff = "tekvica", quantity = 200.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId18, stuff = "syr čerstvý", quantity = 150.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId18, stuff = "olej olivový", quantity = 1.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId18, stuff = "soľ", quantity = 1.0, unit = "podľa chuti"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId18, stuff = "korenie čierne mleté", quantity = 1.0, unit = "podľa chuti"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId18, stuff = "semienka (ľanové a slnečnicové)", quantity = 2.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId18, stuff = "mandle nasekané", quantity = 1.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId18, stuff = "čedar", quantity = 80.0, unit = "g"))

                    db.processDao().insert(FoodProcess(food = foodId18, step = 1, description = "Cestoviny uvaríme al dente v osolenej vode."))
                    db.processDao().insert(FoodProcess(food = foodId18, step = 2, description = "Tekvicu nakrájame na malé kúsky a opečieme na olivovom oleji dozlatista."))
                    db.processDao().insert(FoodProcess(food = foodId18, step = 3, description = "K tekvici pridáme scedené cestoviny, čerstvý syr a premiešame. Dochutíme soľou a korením."))
                    db.processDao().insert(FoodProcess(food = foodId18, step = 4, description = "Zmes preložíme do zapekacej misy, posypeme semienkami, nasekanými mandľami a nastrúhaným čedarom."))
                    db.processDao().insert(FoodProcess(food = foodId18, step = 5, description = "Pečieme v rúre vyhriatej na 200 °C približne 25 minút."))


                    //20

                    val bitmap19 = BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.brownies
                    )

                    var quality19 = 90
                    var byteArray19: ByteArray

                    do {
                        val stream19 = ByteArrayOutputStream()
                        bitmap19.compress(Bitmap.CompressFormat.JPEG, quality19, stream19)
                        byteArray19 = stream19.toByteArray()
                        quality19 -= 5
                    } while (byteArray19.size > 500_000 && quality19 > 10)

                    val foodId19 = 20

                    db.foodDao().insert(
                        Food(
                            name = "Čokoládové brownie",
                            typeId = 4,
                            cookingTime = 30,
                            preparingTime = 15,
                            portions = 12,
                            calories = 450,
                            description = "Vynikajúci a jednoduchý koláč zo zámoria, ktorý sa veľmi rýchlo udomácnil aj v našich kuchyniach. Je fantasticky vláčny a hriešne čokoládový.",
                            image = byteArray19
                        )
                    )

                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId19, stuff = "horká čokoláda (aspoň 70% kakaa)", quantity = 200.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId19, stuff = "maslo", quantity = 200.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId19, stuff = "soľ", quantity = 1.0, unit = "štipka"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId19, stuff = "cukor", quantity = 200.0, unit = "g"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId19, stuff = "vajce", quantity = 3.0, unit = "ks"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId19, stuff = "hladká múka", quantity = 3.0, unit = "PL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId19, stuff = "kypriaci prášok", quantity = 1.0, unit = "KL"))
                    db.foodStuffDao().insertFoodStuff(FoodStuff(food = foodId19, stuff = "kakao", quantity = 2.0, unit = "PL"))

                    db.processDao().insert(FoodProcess(food = foodId19, step = 1, description = "Čokoládu spolu s maslom rozpustíme vo vodnom kúpeli."))
                    db.processDao().insert(FoodProcess(food = foodId19, step = 2, description = "Keď je hmota tekutá a lesklá, odložíme ju bokom, aby trochu vychladla."))
                    db.processDao().insert(FoodProcess(food = foodId19, step = 3, description = "Rúru rozohrejeme na 180°C."))
                    db.processDao().insert(FoodProcess(food = foodId19, step = 4, description = "Vo väčšej miske spolu vyšľaháme celé vajcia s cukrom do peny."))
                    db.processDao().insert(FoodProcess(food = foodId19, step = 5, description = "Potom do misky preosejeme múku, kypriaci prášok a kakao a zľahka zapracujeme tak, aby sme stratili čo najmenej vzduchu."))
                    db.processDao().insert(FoodProcess(food = foodId19, step = 6, description = "Proces opatrného zamiešania opakujeme aj s čokoládovo-maslovou zmesou."))
                    db.processDao().insert(FoodProcess(food = foodId19, step = 7, description = "Vymiešané cesto nalejeme do hlbšieho plechu (20x20 cm), ktorý sme vystlali papierom na pečenie."))
                    db.processDao().insert(FoodProcess(food = foodId19, step = 8, description = "Brownies pečieme v rúre vyhriatej na 180°C zhruba 30 minút."))
                    db.processDao().insert(FoodProcess(food = foodId19, step = 9, description = "Okraje by mali byť pevné a stred by sa mal ešte jemne triasť. Necháme vychladnúť a krájame na štvorce."
                    ))


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
