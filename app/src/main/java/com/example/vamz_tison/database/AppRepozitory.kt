package com.example.vamz_tison.database

import kotlinx.coroutines.flow.Flow

class AppRepository(private val database: AppDatabase) {

    // --- FoodDao methods ---
    fun insertFoods(vararg foods: Food) {
        database.foodDao().insert(*foods)
    }

    fun getAllFood(): Flow<List<Food>> {
        return database.foodDao().getAllFood()
    }


    fun getNameType( id: Int):Flow<String> {
        return database.foodDao().getNameType(id)
    }

    fun getFoodsById(id: Int): Flow<List<Food>> {
        return database.foodDao().getByFoodId(id)
    }

    fun deleteFood(food: Food) {
        database.foodDao().deleteById(food)
    }

   /* fun getFoodsByType(typeId: Int): Flow<List<Food>> {
        return database.foodDao().getByType(typeId)
    }*/

    fun searchFoodsByName(substring: String): Flow<List<Food>> {
        return database.foodDao().searchByName(substring)
    }

    fun searchFoodsByTotalTimeRange(minTotalTime: Int, maxTotalTime: Int): Flow<List<Food>> {
        return database.foodDao().searchByTotalTimeRange(minTotalTime, maxTotalTime)
    }


    // --- FoodStuffDao methods ---
    fun insertFoodStuffItems(vararg items: FoodStuff) {
        database.foodStuffDao().insertFoodStuff(*items)
    }

    fun deleteFoodStuff(item: FoodStuff) {
        database.foodStuffDao().deleteFoodStuff(item)
    }

    fun getFoodsByIngredients(ingredients: List<String>, ingredientCount: Int): Flow<List<Food>> {
        return database.foodStuffDao().getFoodsByIngredients(ingredients, ingredientCount)
    }

    fun getAllDistinctIngredientsByFoodId(id: Int): Flow<List<FoodStuff>> {
        return database.foodStuffDao().getAllDistinctIngredientsByFoodId(id)
    }

    fun getAllDistinctIngredients(): Flow<List<String>> {
        return database.foodStuffDao().getAllDistinctIngredients()
    }

    // --- ProcessDao methods ---
    fun insertProcessSteps(vararg steps: FoodProcess) {
        database.processDao().insert(*steps)
    }

    fun deleteProcessStep(step: FoodProcess) {
        database.processDao().delete(step)
    }

    fun getProcessByFoodId(foodId: Int): Flow<List<FoodProcess>> {
        return database.processDao().getByFoodIdOrdered(foodId)
    }


    // --- ShoppingListDao methods ---
    fun insertShoppingLists(vararg list: ShoppingList) {
        database.shoppingListDao().insert(*list)
    }

    fun getShoppingListById(idList: Int): Flow<ShoppingList> {
        return database.shoppingListDao().getShopingListById(idList)
    }

    fun deleteShoppingList(list: ShoppingList) {
        database.shoppingListDao().delete(list)
    }
;
    fun getAllShoppingLists(): Flow<List<ShoppingList>> {
        return database.shoppingListDao().getAll()
    }


    // --- ListItemsDao methods ---
    fun insertListItems(vararg items: ListItems) {
        database.listItemsDao().insert(*items)
    }

    fun deleteListItem(item: ListItems) {
        database.listItemsDao().delete(item)
    }

    fun getItemsByListId(listId: Int): Flow<List<ListItems>> {
        return database.listItemsDao().getByListId(listId)
    }

    fun updateListItem(item: ListItems) {
        database.listItemsDao().update(item)
    }



    // --- FoodTypeDao methods ---
    fun insertFoodTypes(vararg types: FoodType) {
        database.foodTypeDao().insert(*types)
    }

    fun deleteFoodType(type: FoodType) {
        database.foodTypeDao().deleteById(type)
    }

    fun getAllFoodTypes(): Flow<List<FoodType>> {
        return database.foodTypeDao().getAll()
    }


    // --- FavoriteFoodDao methods ---
    fun insertFavoriteFoods(vararg favorites: FavoriteFood) {
        database.favoriteFoodDao().insert(*favorites)
    }

    fun deleteFavoriteFood(favorite: FavoriteFood) {
        database.favoriteFoodDao().delete(favorite)
    }

    fun getAllFavoriteFoods(): Flow<List<FavoriteFood>> {
        return database.favoriteFoodDao().getAll()
    }

    fun getFoodItemStats(): Flow<List<FoodItemStats>> {
        return database.shoppingListDao().getFoodItemStats()
    }

    fun observeFavoriteByFoodId(foodId: Int): Flow<FavoriteFood?> {
        return database.favoriteFoodDao().observeFavoriteByFoodId(foodId)
    }

    fun getFavoriteFoods(): Flow<List<FoodView>> {
        return database.favoriteFoodDao().getFavoriteFoods()
    }

    fun getAllFoodView(): Flow<List<FoodView>> {
        return database.foodDao().getAllFoodView()
    }
}
