package com.task.taskshopping.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.task.taskshopping.item.ProductDao;
import com.task.taskshopping.item.ProductRoomDatabase;
import com.task.taskshopping.item.Productdb;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * Created by delaroy on 4/30/18.
 */


public class QuestionsRepository {

    private ProductDao mQuestionsDao;
    private LiveData<List<Productdb>> mAllQuestions;

    // Productdb that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public QuestionsRepository(Application application) {
        ProductRoomDatabase db = ProductRoomDatabase.getNoteRoomDatabase(application);
        mQuestionsDao = db.noteDao();
        mAllQuestions = mQuestionsDao.getAllNotes();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public  LiveData<List<Productdb>> getmAllQuestions() {
        return mAllQuestions;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert (Productdb word) {
        new insertAsyncTask(mQuestionsDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Productdb, Void, Void> {

        private ProductDao mAsyncTaskDao;

        insertAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Productdb... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void deleteAll()  {
        new deleteAllWordsAsyncTask(mQuestionsDao).execute();
    }
    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProductDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(ProductDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.delete();
            return null;
        }
    }
}
