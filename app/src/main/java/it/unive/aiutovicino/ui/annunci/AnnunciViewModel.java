package it.unive.aiutovicino.ui.annunci;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AnnunciViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AnnunciViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Qui vedremo i miei annunci");
    }

    public LiveData<String> getText() {
        return mText;
    }
}