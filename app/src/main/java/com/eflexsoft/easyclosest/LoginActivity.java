package com.eflexsoft.easyclosest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.eflexsoft.easyclosest.databinding.ActivityLoginBinding;
import com.eflexsoft.easyclosest.viewmodel.LoginViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding;
    LoginViewModel viewModel;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    int RequestCode = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
//                overridePendingTransition(R.anim.slide_in_rigth,R.anim.slide_out_left);
            }
        });

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        activityLoginBinding.showP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    activityLoginBinding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    activityLoginBinding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        viewModel.isSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    activityLoginBinding.emailAddress.setError("may be incorrect");
                    activityLoginBinding.password.setError("also may be incorrect");
                }
                activityLoginBinding.loginContinue.revertAnimation();
//                activityLoginBinding.emailAddress.setError("missing");
//                activityLoginBinding.password.setError("missing");
            }
        });

        activityLoginBinding.loginContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = activityLoginBinding.emailAddress.getText().toString();
                String password = activityLoginBinding.password.getText().toString();

                if (email.trim().isEmpty()) {
                    activityLoginBinding.emailAddress.setError("missing");
                } else if (password.trim().isEmpty()) {
                    activityLoginBinding.password.setError("missing");
                } else {
                    activityLoginBinding.loginContinue.startAnimation();
                    viewModel.doSignInWithEmailAndPassword(email, password);
                }
            }
        });

        googleSignInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        activityLoginBinding.googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent,RequestCode);
            }
        });
//FirebaseAuth.getInstance().signInWithCredential().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//    @Override
//    public void onSuccess(AuthResult authResult) {
//        if (new)
//    }
//}).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//    @Override
//    public void onComplete(@NonNull Task<AuthResult> task) {
//
//    }
//})
    }
}