package com.eflexsoft.easyclosest.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.eflexsoft.easyclosest.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateAccountRepository {

    Context context;
    public MutableLiveData<Boolean> isSignInSuccessful = new MutableLiveData<>();


    public CreateAccountRepository(Context context) {
        this.context = context;
    }
    public void doGoogleSignIn(AuthCredential authCredential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    boolean isFirstSignIn = task.getResult().getAdditionalUserInfo().isNewUser();

                    if (isFirstSignIn) {

                        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(context);

                        String id = firebaseAuth.getUid();
                        String name = googleSignInAccount.getDisplayName();
                        String email = googleSignInAccount.getEmail();
                        String imageUrl = googleSignInAccount.getPhotoUrl().toString();


                        User user = new User(name, imageUrl, "not specified", id, email);

                        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                        DocumentReference reference = firebaseFirestore.collection("Users").document(id);

                        reference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                isSignInSuccessful.setValue(true);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                isSignInSuccessful.setValue(false);
                            }
                        });

                    } else {
                        isSignInSuccessful.setValue(true);
                    }

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
    public void createAccount(String email,String name,String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                User user = new User(name, "none", "not specified", FirebaseAuth.getInstance().getUid(), email);

                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference reference = firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid());

                reference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        isSignInSuccessful.setValue(true);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        isSignInSuccessful.setValue(false);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                isSignInSuccessful.setValue(false);
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
