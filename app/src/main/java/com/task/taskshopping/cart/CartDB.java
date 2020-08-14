package com.task.taskshopping.cart;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Cart.class}, version = 2)
public abstract class CartDB extends RoomDatabase {
    public abstract CartDao getCartDao();

}
