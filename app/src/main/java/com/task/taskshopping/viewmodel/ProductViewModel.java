package com.task.taskshopping.viewmodel;

import android.app.Application;

import com.task.taskshopping.repository.QuestionsRepository;
import com.task.taskshopping.item.Productdb;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * Created by VINO on 14-08-2020
 */
public class ProductViewModel extends AndroidViewModel {
    private QuestionsRepository mRepository;

    private LiveData<List<Productdb>> mAllProduct;

    public ProductViewModel(Application application) {
        super(application);
        mRepository = new QuestionsRepository(application);
        mAllProduct = mRepository.getmAllQuestions();
    }

    public LiveData<List<Productdb>> getAllProduct() {
        return mAllProduct;
    }

    public void insert(Productdb word) {
        mRepository.insert(word);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

/*
    @Override
    protected void onCleared() {
        super.onCleared();
        getAllProduct().getValue().clear();
    }*/
}

