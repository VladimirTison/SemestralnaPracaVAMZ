package com.example.vamz_tison.database

import android.content.SharedPreferences
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    //novinka - pre ten filter
    @Query("""
    SELECT f.id, f.TileImage AS image, f.Meno_jedla AS name, ft.type_name AS category
    FROM food f
    JOIN food_type ft ON f.type_id = ft.id
    JOIN foodStuff fs ON f.id = fs.Id_jedlo
    WHERE fs.Surovina IN (:ingredients)
    GROUP BY f.id
    HAVING COUNT(DISTINCT fs.Surovina) = :ingredientCount
""")
    fun getFoodsByIngredients(
        ingredients: List<String>,
        ingredientCount: Int
    ): Flow<List<FoodView>>

    @Query("""
    SELECT f.id, f.TileImage AS image, f.Meno_jedla AS name, ft.type_name AS category
    FROM food f
    JOIN food_type ft ON f.type_id = ft.id
    WHERE f.type_id = :typeId
    ORDER BY f.Meno_jedla ASC
""")
    fun getByType(typeId: Int): Flow<List<FoodView>>


    //prienik oboch
    @Query("""
    SELECT f.id, f.TileImage AS image, f.Meno_jedla AS name, ft.type_name AS category
    FROM food f
    JOIN food_type ft ON f.type_id = ft.id
    JOIN foodStuff fs ON f.id = fs.Id_jedlo
    WHERE f.type_id = :typeId
      AND fs.Surovina IN (:ingredients)
    GROUP BY f.id
    HAVING COUNT(DISTINCT fs.Surovina) = :ingredientCount
""")
    fun getFoodsByTypeAndIngredients(
        typeId: Int,
        ingredients: List<String>,
        ingredientCount: Int
    ): Flow<List<FoodView>>


    @Insert
    fun insert(vararg food: Food)

    @Delete
    fun deleteById(food : Food)
/*
    @Query("""
        SELECT * FROM food
        WHERE type_id = :typeId
        ORDER BY Meno_jedla ASC
    """)
    fun getByType(typeId: Int): Flow<List<Food>>
*/

    @Query("""
        SELECT * FROM food
    """)
    fun getAllFood(): Flow<List<Food>>

    @Query("""
    SELECT f.id, f.TileImage AS image, f.Meno_jedla AS name, ft.type_name AS category
    FROM food f
    JOIN food_type ft ON f.type_id = ft.id
""")
    fun getAllFoodView(): Flow<List<FoodView>>

    @Query("""
    SELECT food_type.type_name FROM food
    JOIN food_type ON food_type.id = food.type_id
    WHERE food.id = :id
""")
    fun getNameType(id: Int): Flow<String>


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

    @Query("SELECT * FROM list Where id = :idlist")
    fun getShopingListById(idlist: Int): Flow<ShoppingList>

    @Query(
        """
        SELECT 
            items.Id_zoznamu AS food_id,
            SUM(CASE WHEN stav = 1 THEN 1 ELSE 0 END) AS bought_count,
            COUNT(*) AS total_count
        FROM items
        GROUP BY Id_zoznamu
    """
    )
    fun getFoodItemStats(): Flow<List<FoodItemStats>>
}

@Dao
interface ListItemsDao {
    @Insert
    fun insert(vararg listItem: ListItems)

    @Update
    fun update(item: ListItems)

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

    @Query("SELECT * FROM favoritefood ORDER BY jedlo ASC")
    fun getAll(): Flow<List<FavoriteFood>>

    @Query("SELECT * FROM favoritefood WHERE jedlo = :foodId LIMIT 1")
    fun observeFavoriteByFoodId(foodId: Int): Flow<FavoriteFood?>

    @Query("""
    SELECT f.id, f.TileImage AS image, f.Meno_jedla AS name, ft.type_name AS category
    FROM food f
    JOIN favoritefood ff ON ff.jedlo = f.id
    JOIN food_type ft ON f.type_id = ft.id
""")
    fun getFavoriteFoods(): Flow<List<FoodView>>

}






