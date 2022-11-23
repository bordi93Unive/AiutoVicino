package com.example.aiutovicino.ui.portafoglio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PortafoglioViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PortafoglioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Qui vedremo i coin dell'utente ecc");
    }

    public LiveData<String> getText() {
        return mText;
    }
}