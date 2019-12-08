package training.jsimoes.com.data;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import training.jsimoes.com.interfaces.WordDao;
import training.jsimoes.com.models.Word;

public class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> wordList;

    WordRepository(Application application) {
        WordRoomDatabase wordRoomDatabase = WordRoomDatabase.getInstance(application);
        wordDao = wordRoomDatabase.getWordDao();
        wordList = wordDao.getAll();
    }

    LiveData<List<Word>> getAllWords() {
        return wordList;
    }

    public void insert(Word word) {
        new insertAsyncTask(wordDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
