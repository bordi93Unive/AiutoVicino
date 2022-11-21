package com.example.aiutovicino.ui.applicazioni;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ApplicazioniViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ApplicazioniViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Qui vedremo le mie applicazioni");
    }

    public LiveData<String> getText() {
        return mText;
    }
}