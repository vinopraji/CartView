package com.task.taskshopping.item;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProductDao {
    @Insert
    void insert(Productdb note);
    @Query("SELECT * FROM Productdb")
   LiveData<List<Productdb>> getAllNotes();

    @Query("SELECT * FROM Productdb WHERE id=:noteId")
    List<Productdb> getNote(String noteId);

    @Update
    void update(Productdb pro);

    @Query("DELETE FROM Productdb")
    int delete();
}
