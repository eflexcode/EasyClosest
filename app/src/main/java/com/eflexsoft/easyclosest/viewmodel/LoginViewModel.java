package com.eflexsoft.easyclosest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.easyclosest.repository.LoginRepository;
import com.google.firebase.auth.AuthCredential;

public class LoginViewModel extends AndroidViewModel {

    LoginRepository repository;
    MutableLiveData<Boolean> isSignInSuccessful = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new LoginRepository(application);
    }

    public void doSignInWithEmailAndPassword(String email, String password){
        repository.doSignInWithEmailAndPassword(email, password);
    }
    public LiveData<Boolean> isSuccess() {
        isSignInSuccessful = repository.isSignInSuccessful;

        return isSignInSuccessful;
    }
    public void doGoogleSignIn(AuthCredential authCredential) {
        repository.doGoogleSignIn(authCredential);
    }
}
