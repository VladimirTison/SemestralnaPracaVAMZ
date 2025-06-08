package com.example.vamz_tison.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


/**
 * Tabuľka reprezentujúca typ jedla.
 *
 * @property id Primárny kľúč.
 * @property name Názov typu jedla.
 */
@Entity(tableName = "food_type")
data class FoodType(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "type_name")
    val name: String
)

/**
 * Tabuľka reprezentujúca recept.
 *
 * @property id Primárny kľúč.
 * @property name Meno jedla.
 * @property typeId ID typu jedla (cudzí kľúč).
 * @property cookingTime Čas varenia v minútach.
 * @property preparingTime Čas prípravy v minútach.
 * @property portions Počet porcií.
 * @property calories Kalorická hodnota jedla.
 * @property description Stručný popis jedla.
 * @property image Obrázok jedla vo forme bajtového poľa.
 */
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

    @ColumnInfo(name = "meno_jedla")
    val name: String,

    @ColumnInfo(name = "type_id")
    val typeId: Int,

    @ColumnInfo(name = "cas_varenia")
    val cookingTime: Int,

    @ColumnInfo(name = "cas_pripravy")
    val preparingTime: Int,

    @ColumnInfo(name = "porcie")
    val portions: Int,

    @ColumnInfo(name = "kalorie")
        val calories: Int,

    @ColumnInfo(name = "jednoduchy_popis")
    val description: String,

    @ColumnInfo(name = "tileImage", typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray? = null
) {
    //andoid studio si to samo vygenerovalo
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Food

        if (id != other.id) return false
        if (typeId != other.typeId) return false
        if (cookingTime != other.cookingTime) return false
        if (preparingTime != other.preparingTime) return false
        if (portions != other.portions) return false
        if (calories != other.calories) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }
    //andoid studio si to samo vygenerovalo
    override fun hashCode(): Int {
        var result = id
        result = 31 * result + typeId
        result = 31 * result + cookingTime
        result = 31 * result + preparingTime
        result = 31 * result + portions
        result = 31 * result + calories
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}

/**
 * Tabuľka reprezentujúca surovinu patriacu k jedlu.
 *
 * @property id Primárny kľúč.
 * @property food ID jedla, ku ktorému surovina patrí.
 * @property stuff Názov suroviny.
 * @property quantity Množstvo.
 * @property unit Jednotka.
 */
@Entity(
    tableName = "foodStuff",
    foreignKeys = [
        ForeignKey(
            entity = Food::class,
            parentColumns = ["id"],
            childColumns = ["id_jedlo"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["id_jedlo"])]
)
data class FoodStuff(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "id_jedlo")
    val food: Int,

    @ColumnInfo(name = "surovina")
    val stuff: String,

    @ColumnInfo(name = "mnozstvo")
    val quantity: Double,

    @ColumnInfo(name = "jednotka")
    val unit: String
)

/**
 * Tabuľka reprezentujúca krok postupu pri varení konkrétneho jedla.
 *
 * @property id Primárny kľúč.
 * @property food ID jedla, ku ktorému krok patrí.
 * @property description Popis kroku.
 * @property step Číslo kroku v poradí.
 */
@Entity(
    tableName = "process",
    foreignKeys = [
        ForeignKey(
            entity = Food::class,
            parentColumns = ["id"],
            childColumns = ["id_jedlo"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["id_jedlo"])]
)
data class FoodProcess(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "id_jedlo")
    val food: Int,

    @ColumnInfo(name = "popis")
    val description: String,

    @ColumnInfo(name = "krok")
    val step: Int
)

/**
 * Entita reprezentujúca obľúbené jedlá používateľa.
 *
 * @property id Primárny kľúč.
 * @property food ID jedla, ktoré je obľúbené.
 */
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
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "jedlo")
    val food: Int
)


/**
 * Tabuľka reprezentujúca nákupný zoznam.
 *
 * @property id Primárny kľúč.
 * @property name Názov nákupného zoznamu.
 */
@Entity(tableName = "list")
data class ShoppingList(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "meno_zoznamu")
    val name: String
)

/**
 * Tabuľka reprezentujúca položku v nákupnom zozname.
 *
 * @property id Primárny kľúč.
 * @property name Názov položky.
 * @property list ID nákupného zoznamu (cudzí kľúč).
 * @property activestate Stav kúpenia (true = kúpené, false = ešte nie).
 */
@Entity(
    tableName = "items",
    foreignKeys = [
        ForeignKey(
            entity = ShoppingList::class,
            parentColumns = ["id"],
            childColumns = ["id_zoznamu"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["id_zoznamu"])]
)
data class ListItems(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "meno_polozky")
    val name: String,

    @ColumnInfo(name = "id_zoznamu")
    val list: Int,

    @ColumnInfo(name = "stav")
    val activestate: Boolean
)

/**
 * Dátová trieda slúžiaca na štatistiku v nákupnom zozname.
 *
 * @property foodId ID jedla.
 * @property boughtCount Počet kúpených položiek.
 * @property totalCount Celkový počet položiek pre dané jedlo.
 */
data class FoodItemStats(
    val foodId: Int,
    val boughtCount: Int,
    val totalCount: Int
)

/**
 * Zjednodušené zobrazenie jedla na UI účely (napr. pre karty receptov).
 *
 * @property id ID jedla.
 * @property image Voliteľný obrázok vo forme bajtového poľa.
 * @property name Názov jedla.
 * @property category Kategória jedla.
 */
data class FoodView(
    val id: Int,
    val image: ByteArray? = null,
    val name: String,
    val category: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FoodView

        if (id != other.id) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false
        if (name != other.name) return false
        if (category != other.category) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (image?.contentHashCode() ?: 0)
        result = 31 * result + name.hashCode()
        result = 31 * result + category.hashCode()
        return result
    }
}