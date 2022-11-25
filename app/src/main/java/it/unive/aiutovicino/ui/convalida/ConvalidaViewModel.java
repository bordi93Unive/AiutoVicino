package it.unive.aiutovicino.ui.convalida;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConvalidaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ConvalidaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Roba da convalidare per l'admin");
    }

    public LiveData<String> getText() {
        return mText;
    }
}