package com.task.taskshopping.cart;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CartDao {
    @Insert
    void insert(Cart... cart);

    @Query("SELECT * FROM cart WHERE name")
    List<Cart> getAllItems();

    
}
