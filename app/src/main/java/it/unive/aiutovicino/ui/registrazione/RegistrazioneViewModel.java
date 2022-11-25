package it.unive.aiutovicino.ui.registrazione;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegistrazioneViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RegistrazioneViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Registrazione");
    }

    public LiveData<String> getText() {
        return mText;
    }
}