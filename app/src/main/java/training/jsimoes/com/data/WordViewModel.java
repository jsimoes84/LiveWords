package training.jsimoes.com.data;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import training.jsimoes.com.models.Word;

public class WordViewModel extends AndroidViewModel {
    private WordRepository wordRepository;
    private LiveData<List<Word>> wordLiveData;

    public WordViewModel(Application application) {
        super(application);
        wordRepository = new WordRepository(application);
        wordLiveData = wordRepository.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return wordLiveData;
    }

    public void insert(Word word) {
        wordRepository.insert(word);
    }
}
