package it.unive.aiutovicino.ui.classifica;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClassificaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ClassificaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Qui vedremo i coin dell'utente ecc");
    }

    public LiveData<String> getText() {
        return mText;
    }
}