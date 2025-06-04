package database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

//tabulka jedlo
@Entity(tableName = "food")
data class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @NonNull
    @ColumnInfo(name = "Meno_jedla")
    val name: String,

    @NonNull
    @ColumnInfo(name = "Kategoria")
    val category: String,

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
    val calories: Float,

    @NonNull
    @ColumnInfo(name = "Jednoduchy_popis")
    val description: String,

    @ColumnInfo(name = "TileImage", typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray? = null
)

//tabulka jedlo-surovina
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
    indices = [ Index(value = ["Id_jedlo"]) ]
)
data class FoodStuff(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @NonNull
    @ColumnInfo(name = "Id_jedlo")
    val Id_jedlo: Int,

    @NonNull
    @ColumnInfo(name = "Surovina")
    val stuff: String,

    @NonNull
    @ColumnInfo(name = "Mnozstvo")
    val quantity: Float,

    @NonNull
    @ColumnInfo(name = "Jednotka")
    val unit: String
)

//tabulka jedlo-postup
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
    indices = [ Index(value = ["Id_jedlo"]) ]
)
data class Process(
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
    val step: Int,
)

//oblubene jedlo
@Entity(
    tableName = "favoritefood",
    foreignKeys = [
        ForeignKey(
            entity = Food::class,
            parentColumns = ["id"],
            childColumns = ["Id_jedlo"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [ Index(value = ["Id_jedlo"]) ]
)
data class FavoriteFood(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @NonNull
    @ColumnInfo(name = "Id_jedlo")
    val food: Int
)

//tabulka jedlo
@Entity(tableName = "list")
data class List(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @NonNull
    @ColumnInfo(name = "Meno_zoznamu")
    val name: String,
)

//položky zoznamu
@Entity(
    tableName = "items",

    foreignKeys = [
        ForeignKey(
            entity = List::class,
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
    val state: Int
)