package com.task.taskshopping.cart;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart")
public class Cart {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "discription")
    private String discription;


    @NonNull
    @ColumnInfo(name = "price")
    private String price;

    @NonNull
    @ColumnInfo(name = "count")
    private int count;
    public Cart() {
    }

    public Cart(@NonNull String name, @NonNull String discription, @NonNull String price, int count) {
        this.name = name;
        this.discription = discription;
        this.price = price;
        this.count = count;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getDiscription() {
        return discription;
    }

    public void setDiscription(@NonNull String discription) {
        this.discription = discription;
    }

    @NonNull
    public String getPrice() {
        return price;
    }

    public void setPrice(@NonNull String price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
