package training.jsimoes.com.interfaces;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import training.jsimoes.com.models.Word;

@Dao
public interface WordDao {

    @Insert
    void insert(Word word);

    @Insert
    void insertAll(List<Word> words);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Query("SELECT * FROM word_table ORDER BY text ASC")
    LiveData<List<Word>> getAll();
}
