package com.example.aiutovicino.ui.MyApplicazioni;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyApplicazioniViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyApplicazioniViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Qui vedremo le mie applicazioni");
    }

    public LiveData<String> getText() {
        return mText;
    }
}