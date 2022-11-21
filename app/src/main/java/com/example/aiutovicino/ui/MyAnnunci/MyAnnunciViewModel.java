package com.example.aiutovicino.ui.MyAnnunci;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyAnnunciViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyAnnunciViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Qui vedremo i miei annunci");
    }

    public LiveData<String> getText() {
        return mText;
    }
}