package com.example.vamz_tison.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * DAO rozhranie pre operácie s receptami.
 */
@Dao
interface FoodDao {
    /**
     * Získa recepty, ktoré obsahujú presne všetky zvolené suroviny.
     */
    @Query(
        """
    SELECT f.id, f.tileImage AS image, f.meno_jedla AS name, ft.type_name AS category
    FROM food f
    JOIN food_type ft ON f.type_id = ft.id
    JOIN foodStuff fs ON f.id = fs.id_jedlo
    WHERE fs.surovina IN (:ingredients)
    GROUP BY f.id
    HAVING COUNT(DISTINCT fs.Surovina) = :ingredientCount
"""
    )
    fun getFoodsByIngredients(
        ingredients: List<String>,
        ingredientCount: Int
    ): Flow<List<FoodView>>

    /**
     * Získa recepty podľa typu jedla.
     */
    @Query(
        """
    SELECT f.id, f.tileImage AS image, f.meno_jedla AS name, ft.type_name AS category
    FROM food f
    JOIN food_type ft ON f.type_id = ft.id
    WHERE f.type_id = :typeId
    ORDER BY f.meno_jedla ASC
"""
    )
    fun getByType(typeId: Int): Flow<List<FoodView>>

    /**
     * Získa recepty podľa typu a zvolených surovín – musia obsahovať presne dané ingrediencie.
     */
    @Query(
        """
    SELECT f.id, f.tileImage AS image, f.meno_jedla AS name, ft.type_name AS category
    FROM food f
    JOIN food_type ft ON f.type_id = ft.id
    JOIN foodStuff fs ON f.id = fs.id_jedlo
    WHERE f.type_id = :typeId
      AND fs.surovina IN (:ingredients)
    GROUP BY f.id
    HAVING COUNT(DISTINCT fs.surovina) = :ingredientCount
"""
    )
    fun getFoodsByTypeAndIngredients(
        typeId: Int,
        ingredients: List<String>,
        ingredientCount: Int
    ): Flow<List<FoodView>>


    @Insert
    fun insert(vararg food: Food)

    @Delete
    fun deleteById(food: Food)

    @Query(
        """
        SELECT * FROM food
    """
    )
    fun getAllFood(): Flow<List<Food>>

    /**
     * Získa všetky jedlá ako FoodView (s kategóriou a obrázkom).
     */
    @Query(
        """
    SELECT f.id, f.tileImage AS image, f.meno_jedla AS name, ft.type_name AS category
    FROM food f
    JOIN food_type ft ON f.type_id = ft.id
"""
    )
    fun getAllFoodView(): Flow<List<FoodView>>

    /**
     * Získa názov typu jedla podľa ID jedla.
     */
    @Query(
        """
    SELECT food_type.type_name FROM food
    JOIN food_type ON food_type.id = food.type_id
    WHERE food.id = :id
"""
    )
    fun getNameType(id: Int): Flow<String>

    /**
     * Získa jedlo podľa ID.
     */
    @Query(
        """
        SELECT * FROM food
        WHERE id = :id
        ORDER BY Meno_jedla ASC
    """
    )
    fun getByFoodId(id: Int): Flow<List<Food>>

    /**
     * Vyhľada jedlá podľa názvu (čiastočná zhoda).
     */
    @Query(
        """
        SELECT * FROM food
        WHERE meno_jedla LIKE '%' || :substring || '%'
        ORDER BY meno_jedla ASC
    """
    )
    fun searchByName(substring: String): Flow<List<Food>>
}

/**
 * DAO rozhranie pre operácie s  FoodStuff.
 */
@Dao
interface FoodStuffDao {
    @Insert
    fun insertFoodStuff(vararg foodStuff: FoodStuff)

    @Delete
    fun deleteFoodStuff(foodStuff: FoodStuff)

    /**
     * Získa recepty, ktoré obsahujú presne všetky zadané suroviny.
     */
    @Query("""
        SELECT f.* 
        FROM food AS f 
        JOIN foodStuff AS fs ON f.id = fs.id_jedlo
        WHERE fs.surovina IN (:ingredients)
        GROUP BY f.id
        HAVING COUNT(DISTINCT fs.surovina) = :ingredientCount
    """)
    fun getFoodsByIngredients(
        ingredients: List<String>,
        ingredientCount: Int
    ): Flow<List<Food>>

    /**
     * Získa všetky unikátne suroviny zo všetkých receptov.
     */
    @Query("""
        SELECT DISTINCT surovina
        FROM foodStuff
        ORDER BY surovina ASC
    """)
    fun getAllDistinctIngredients(): Flow<List<String>>

    /**
     *  Získa všetky suroviny patriace k danému jedlu.
     */
    @Query("""
    SELECT *
    FROM foodStuff
    WHERE id_jedlo = :foodId
    ORDER BY Surovina ASC
""")
    fun getAllDistinctIngredientsByFoodId(foodId: Int): Flow<List<FoodStuff>>
}

/**
 * DAO rozhranie pre operácie s tabuľkou FoodStuff.
 */
@Dao
interface ProcessDao {
    @Insert
    fun insert(vararg step: FoodProcess)

    @Delete
    fun delete(step: FoodProcess)

    @Query("""
        SELECT * FROM process
        WHERE id_jedlo = :foodId
        ORDER BY krok ASC
    """)
    fun getByFoodIdOrdered(foodId: Int): Flow<List<FoodProcess>>
}

/**
 * DAO rozhranie pre operácie s tabuľkou FoodProcess.
 */
@Dao
interface ShoppingListDao {
    @Insert
    fun insert(vararg shoppingList: ShoppingList)

    @Delete
    fun delete(shoppingList: ShoppingList)

    @Query("SELECT * FROM `list` ORDER BY meno_zoznamu ASC")
    fun getAll(): Flow<List<ShoppingList>>

    /**
     * Získa všetky kroky postupu patriace ku konkrétnemu jedlu, usporiadané podľa poradia.
     */
    @Query("SELECT * FROM list Where id = :idlist")
    fun getShoppingListById(idlist: Int): Flow<ShoppingList>

    /**
     *  Získa štatistiky pre položky v nákupných zoznamoch.
     */
    @Query(
        """
    SELECT 
        items.id_zoznamu AS foodId,
        SUM(CASE WHEN stav = 1 THEN 1 ELSE 0 END) AS boughtCount,
        COUNT(*) AS totalCount
    FROM items
    GROUP BY id_zoznamu
    """
    )
    fun getFoodItemStats(): Flow<List<FoodItemStats>>
}

    /**
 * DAO rozhranie pre operácie s tabuľkou ListItems.
 */
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
        WHERE id_zoznamu = :listId
        ORDER BY id ASC
    """)
    fun getByListId(listId: Int): Flow<List<ListItems>>
}

/**
 * DAO rozhranie pre operácie s tabuľkou FoodType.
 */
@Dao
interface FoodTypeDao {
    @Insert
    fun insert(vararg foodType: FoodType)

    @Delete
    fun deleteById(foodType : FoodType)

    @Query("SELECT * FROM food_type ORDER BY type_name ASC")
    fun getAll(): Flow<List<FoodType>>
}

/**
 * DAO rozhranie pre operácie s tabuľkou FavoriteFood.
 */
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

    /**
     * Získa zobrazenie všetkých obľúbených jedál.
     */
    @Query("""
    SELECT f.id, f.tileImage AS image, f.meno_jedla AS name, ft.type_name AS category
    FROM food f
    JOIN favoritefood ff ON ff.jedlo = f.id
    JOIN food_type ft ON f.type_id = ft.id
""")
    fun getFavoriteFoods(): Flow<List<FoodView>>
}






