package com.example.vamz_tison.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert
    fun insert(vararg food: Food)

    @Delete
    fun deleteById(food : Food)

    @Query("""
        SELECT * FROM food
        WHERE type_id = :typeId
        ORDER BY Meno_jedla ASC
    """)
    fun getByType(typeId: Int): Flow<List<Food>>

    @Query("""
        SELECT * FROM food
        WHERE id = :id
        ORDER BY Meno_jedla ASC
    """)
    fun getByFoodId(id: Int): Flow<List<Food>>

    @Query("""
        SELECT * FROM food
        WHERE Meno_jedla LIKE '%' || :substring || '%'
        ORDER BY Meno_jedla ASC
    """)
    fun searchByName(substring: String): Flow<List<Food>>

    @Query("""
        SELECT * FROM food
        WHERE (Cas_varenia + Cas_pripravy) BETWEEN :minTotalTime AND :maxTotalTime
        ORDER BY (Cas_varenia + Cas_pripravy) ASC
    """)
    fun searchByTotalTimeRange(minTotalTime: Int, maxTotalTime: Int): Flow<List<Food>>
}

@Dao
interface FoodStuffDao {
    @Insert
    fun insertFoodStuff(vararg foodStuff: FoodStuff)

    @Delete
    fun deleteFoodStuff(foodStuff: FoodStuff)

    @Query("""
        SELECT f.* 
        FROM food AS f 
        JOIN foodStuff AS fs ON f.id = fs.Id_jedlo
        WHERE fs.Surovina IN (:ingredients)
        GROUP BY f.id
        HAVING COUNT(DISTINCT fs.Surovina) = :ingredientCount
    """)
    fun getFoodsByIngredients(
        ingredients: List<String>,
        ingredientCount: Int
    ): Flow<List<Food>>

    @Query("""
        SELECT DISTINCT Surovina
        FROM foodStuff
        ORDER BY Surovina ASC
    """)
    fun getAllDistinctIngredients(): Flow<List<String>>

    @Query("""
    SELECT *
    FROM foodStuff
    WHERE id_jedlo = :foodId
    ORDER BY Surovina ASC
""")
    fun getAllDistinctIngredientsByFoodId(foodId: Int): Flow<List<FoodStuff>>

}

@Dao
interface ProcessDao {
    @Insert
    fun insert(vararg step: FoodProcess)

    @Delete
    fun delete(step: FoodProcess)

    @Query("""
        SELECT * FROM process
        WHERE Id_jedlo = :foodId
        ORDER BY Krok ASC
    """)
    fun getByFoodIdOrdered(foodId: Int): Flow<List<FoodProcess>>
}

@Dao
interface ShoppingListDao {
    @Insert
    fun insert(vararg shoppingList: ShoppingList)

    @Delete
    fun delete(shoppingList: ShoppingList)

    @Query("SELECT * FROM `list` ORDER BY Meno_zoznamu ASC")
    fun getAll(): Flow<List<ShoppingList>>
}

@Dao
interface ListItemsDao {
    @Insert
    fun insert(vararg listItem: ListItems)

    @Delete
    fun delete(listItem: ListItems)

    @Query("""
        SELECT * FROM items
        WHERE Id_zoznamu = :listId
        ORDER BY id ASC
    """)
    fun getByListId(listId: Int): Flow<List<ListItems>>
}

@Dao
interface FoodTypeDao {
    @Insert
    fun insert(vararg foodType: FoodType)

    @Delete
    fun deleteById(foodType : FoodType)

    @Query("SELECT * FROM food_type ORDER BY type_name ASC")
    fun getAll(): Flow<List<FoodType>>
}

//Oblubene jedla
@Dao
interface FavoriteFoodDao {
    @Insert
    fun insert(vararg favorite: FavoriteFood)

    @Delete
    fun delete(favorite: FavoriteFood)

    @Query("SELECT * FROM favoritefood ORDER BY Id_jedlo ASC")
    fun getAll(): Flow<List<FavoriteFood>>
}
