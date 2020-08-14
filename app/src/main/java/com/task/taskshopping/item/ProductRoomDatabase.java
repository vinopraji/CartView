package com.task.taskshopping.item;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Productdb.class,version = 2)
public abstract class ProductRoomDatabase extends RoomDatabase {
    public abstract ProductDao noteDao();

    public static volatile ProductRoomDatabase productRoomDatabase;

    public static ProductRoomDatabase getNoteRoomDatabase(final Context context){
        if (productRoomDatabase ==null){
            synchronized (ProductRoomDatabase.class){
                if (productRoomDatabase ==null){
                    productRoomDatabase = Room.databaseBuilder(context.getApplicationContext(), ProductRoomDatabase.class,"note_database").build();
                }
            }
        }
        return productRoomDatabase;
    }
}
