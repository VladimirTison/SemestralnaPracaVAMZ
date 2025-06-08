package com.example.vamz_tison.database

import kotlinx.coroutines.flow.Flow

/**
 * Trieda slúži ako jednotný prístupový bod k databáze.
 *
 * @property database Inštancia databázy aplikácie.
 */
class AppRepository(private val database: AppDatabase) {

    /*
    fun insertFoods(vararg foods: Food) {
        database.foodDao().insert(*foods)
    }*/

    /** Získa názov typu jedla podľa ID jedla. */
    fun getNameType(id: Int): Flow<String> {
        return database.foodDao().getNameType(id)
    }

    /** Získa jedlo podľa ID. */
    fun getFoodsById(id: Int): Flow<List<Food>> {
        return database.foodDao().getByFoodId(id)
    }

    /* Vymaže konkrétne jedlo z databázy.
    fun deleteFood(food: Food) {
        database.foodDao().deleteById(food)
    }*/

    /** Získa jedlá, ktoré obsahujú presne všetky zvolené suroviny. */
    fun getFoodsByIngredients(ingredients: List<String>, ingredientCount: Int): Flow<List<FoodView>> {
        return database.foodDao().getFoodsByIngredients(ingredients, ingredientCount)
    }

    /** Získa jedlá podľa typu a zvolených surovín. */
    fun getFoodsByTypeAndIngredients(
        typeId: Int,
        ingredients: List<String>,
        ingredientCount: Int
    ): Flow<List<FoodView>> {
        return database.foodDao().getFoodsByTypeAndIngredients(typeId, ingredients, ingredientCount)
    }

    /** Získa všetky jedlá podľa typu. */
    fun getByType(typeId: Int): Flow<List<FoodView>> {
        return database.foodDao().getByType(typeId)
    }

    /** Získa všetky jedlá vo forme FoodView. */
    fun getAllFoodView(): Flow<List<FoodView>> {
        return database.foodDao().getAllFoodView()
    }

    // === FOOD STUFF DAO ===

    /** Vloží suroviny jedla do databázy. */
    fun insertFoodStuffItems(vararg items: FoodStuff) {
        database.foodStuffDao().insertFoodStuff(*items)
    }

    /** Vymaže konkrétnu surovinu. */
    fun deleteFoodStuff(item: FoodStuff) {
        database.foodStuffDao().deleteFoodStuff(item)
    }

    /** Získa všetky suroviny patriace k danému jedlu. */
    fun getAllDistinctIngredientsByFoodId(id: Int): Flow<List<FoodStuff>> {
        return database.foodStuffDao().getAllDistinctIngredientsByFoodId(id)
    }

    /** Získa všetky unikátne suroviny zo všetkých jedál. */
    fun getAllDistinctIngredients(): Flow<List<String>> {
        return database.foodStuffDao().getAllDistinctIngredients()
    }

    // === PROCESS DAO ===

    /** Vloží kroky postupu varenia. */
    fun insertProcessSteps(vararg steps: FoodProcess) {
        database.processDao().insert(*steps)
    }

    /** Vymaže konkrétny krok postupu. */
    fun deleteProcessStep(step: FoodProcess) {
        database.processDao().delete(step)
    }

    /** Získa všetky kroky postupu podľa ID jedla. */
    fun getProcessByFoodId(foodId: Int): Flow<List<FoodProcess>> {
        return database.processDao().getByFoodIdOrdered(foodId)
    }

    // === SHOPPING LIST DAO ===

    /** Vloží jeden alebo viac nákupných zoznamov. */
    fun insertShoppingLists(vararg list: ShoppingList) {
        database.shoppingListDao().insert(*list)
    }

    /** Získa nákupný zoznam podľa ID. */
    fun getShoppingListById(idList: Int): Flow<ShoppingList> {
        return database.shoppingListDao().getShoppingListById(idList)
    }

    /** Vymaže konkrétny nákupný zoznam. */
    fun deleteShoppingList(list: ShoppingList) {
        database.shoppingListDao().delete(list)
    }

    /** Získa všetky nákupné zoznamy. */
    fun getAllShoppingLists(): Flow<List<ShoppingList>> {
        return database.shoppingListDao().getAll()
    }

    /** Získa štatistiky o počte kúpených a celkových položiek v zoznamoch. */
    fun getFoodItemStats(): Flow<List<FoodItemStats>> {
        return database.shoppingListDao().getFoodItemStats()
    }

    // === LIST ITEMS DAO ===

    /** Vloží položky do nákupného zoznamu. */
    fun insertListItems(vararg items: ListItems) {
        database.listItemsDao().insert(*items)
    }

    /** Odstráni položku zo zoznamu. */
    fun deleteListItem(item: ListItems) {
        database.listItemsDao().delete(item)
    }

    /** Aktualizuje stav položky (kúpené / nekúpené). */
    fun updateListItem(item: ListItems) {
        database.listItemsDao().update(item)
    }

    /** Získa všetky položky patriace k danému nákupnému zoznamu. */
    fun getItemsByListId(listId: Int): Flow<List<ListItems>> {
        return database.listItemsDao().getByListId(listId)
    }

    // === FOOD TYPE DAO ===

    /** Vloží typy jedál. */
    fun insertFoodTypes(vararg types: FoodType) {
        database.foodTypeDao().insert(*types)
    }

    /** Vymaže typ jedla. */
    fun deleteFoodType(type: FoodType) {
        database.foodTypeDao().deleteById(type)
    }

    /** Získa všetky typy jedál. */
    fun getAllFoodTypes(): Flow<List<FoodType>> {
        return database.foodTypeDao().getAll()
    }

    // === FAVORITE FOOD DAO ===

    /** Vloží obľúbené jedlá používateľa. */
    fun insertFavoriteFoods(vararg favorites: FavoriteFood) {
        database.favoriteFoodDao().insert(*favorites)
    }

    /** Odstráni obľúbené jedlo. */
    fun deleteFavoriteFood(favorite: FavoriteFood) {
        database.favoriteFoodDao().delete(favorite)
    }

    /** Získa všetky obľúbené recepty (ID + typ + obrázok). */
    fun getFavoriteFoods(): Flow<List<FoodView>> {
        return database.favoriteFoodDao().getFavoriteFoods()
    }

    /** Zistí, či je konkrétne jedlo obľúbené (reaguje na zmenu). */
    fun observeFavoriteByFoodId(foodId: Int): Flow<FavoriteFood?> {
        return database.favoriteFoodDao().observeFavoriteByFoodId(foodId)
    }

    /** Získa všetky obľúbené jedlá (iba ID). */
    fun getAllFavoriteFoods(): Flow<List<FavoriteFood>> {
        return database.favoriteFoodDao().getAll()
    }
}