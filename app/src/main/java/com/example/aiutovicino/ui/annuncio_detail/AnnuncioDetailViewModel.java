package com.example.aiutovicino.ui.annuncio_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AnnuncioDetailViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AnnuncioDetailViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Qui vedremo il dettaglio dell'annuncio");
    }

    public LiveData<String> getText() {
        return mText;
    }
}