package it.unive.aiutovicino.ui.annuncio_crea;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AnnuncioCreaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AnnuncioCreaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Creazione annuncio");
    }

    public LiveData<String> getText() {
        return mText;
    }
}