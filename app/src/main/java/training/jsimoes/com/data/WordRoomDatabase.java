package training.jsimoes.com.data;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import training.jsimoes.com.interfaces.WordDao;
import training.jsimoes.com.models.Word;

@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {
    private static WordRoomDatabase INSTANCE;
    private static List<Word> defaultWords = new ArrayList<>();

    public static WordRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {

            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordRoomDatabase.class, "word_database")
                            .addCallback(roomDbCallback)
                            .fallbackToDestructiveMigration()
                            .build();

                    buildDefaultWords();
                }
            }

        }

        return INSTANCE;
    }

    /**
     * Build default words and add them to list
     */
    private static void buildDefaultWords() {
        String[] words = {"dolphin", "crocodile", "cobra"};

        for (int i = 0; i <= words.length - 1; i++) {
            defaultWords.add(new Word(words[i]));
        }
    }

    private static RoomDatabase.Callback roomDbCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.getWordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mDao.deleteAll();
            mDao.insertAll(defaultWords);

            return null;
        }
    }

    public abstract WordDao getWordDao();
}
