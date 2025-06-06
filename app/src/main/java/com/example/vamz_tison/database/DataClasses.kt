package com.example.vamz_tison.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// TABUĽKA: druhy jedla
@Entity(tableName = "food_type")
data class FoodType(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @NonNull
    @ColumnInfo(name = "type_name")
    val name: String
)

// TABUĽKA: jedlo
@Entity(
    tableName = "food",
    foreignKeys = [
        ForeignKey(
            entity = FoodType::class,
            parentColumns = ["id"],
            childColumns = ["type_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["type_id"])]
)
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @NonNull
    @ColumnInfo(name = "Meno_jedla")
    val name: String,

    @ColumnInfo(name = "type_id")
    val typeId: Int,

    @NonNull
    @ColumnInfo(name = "Cas_varenia")
    val cookingTime: Int,

    @NonNull
    @ColumnInfo(name = "Cas_pripravy")
    val preparingTime: Int,

    @NonNull
    @ColumnInfo(name = "Porcie")
    val portions: Int,

    @NonNull
    @ColumnInfo(name = "Kalorie")
        val calories: Int,

    @NonNull
    @ColumnInfo(name = "Jednoduchy_popis")
    val description: String,

    @ColumnInfo(name = "TileImage", typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray? = null
)

// TABUĽKA: jedlo–surovina
@Entity(
    tableName = "foodStuff",
    foreignKeys = [
        ForeignKey(
            entity = Food::class,
            parentColumns = ["id"],
            childColumns = ["Id_jedlo"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["Id_jedlo"])]
)
data class FoodStuff(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @NonNull
    @ColumnInfo(name = "Id_jedlo")
    val food: Int,

    @NonNull
    @ColumnInfo(name = "Surovina")
    val stuff: String,

    @NonNull
    @ColumnInfo(name = "Mnozstvo")
    val quantity: Double,

    @NonNull
    @ColumnInfo(name = "Jednotka")
    val unit: String
)

// TABUĽKA: kroky postupu pre recept (FoodProcess)
@Entity(
    tableName = "process",
    foreignKeys = [
        ForeignKey(
            entity = Food::class,
            parentColumns = ["id"],
            childColumns = ["Id_jedlo"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["Id_jedlo"])]
)
data class FoodProcess(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @NonNull
    @ColumnInfo(name = "Id_jedlo")
    val food: Int,

    @NonNull
    @ColumnInfo(name = "Popis")
    val description: String,

    @NonNull
    @ColumnInfo(name = "Krok")
    val step: Int
)

// TABUĽKA: obľúbené jedlo
@Entity(
    tableName = "favoritefood",
    foreignKeys = [
        ForeignKey(
            entity = Food::class,
            parentColumns = ["id"],
            childColumns = ["jedlo"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["jedlo"])]
)
data class FavoriteFood(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "jedlo")
    val food: Int
)



// TABUĽKA: nákupný zoznam
@Entity(tableName = "list")
data class ShoppingList(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @NonNull
    @ColumnInfo(name = "Meno_zoznamu")
    val name: String
)

// TABUĽKA: položky v nákupnom zozname
@Entity(
    tableName = "items",
    foreignKeys = [
        ForeignKey(
            entity = ShoppingList::class,
            parentColumns = ["id"],
            childColumns = ["Id_zoznamu"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["Id_zoznamu"])]
)
data class ListItems(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @NonNull
    @ColumnInfo(name = "Meno_polozky")
    val name: String,

    @NonNull
    @ColumnInfo(name = "Id_zoznamu")
    val list: Int,

    @NonNull
    @ColumnInfo(name = "Stav")
    val activestate: Boolean
)

data class FoodItemStats(
    val food_id: Int,
    val bought_count: Int,
    val total_count: Int
)

data class FoodView(
    val id: Int,
    val image: ByteArray? = null,
    val name: String,
    val category: String
)