package com.eflexsoft.easyclosest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.eflexsoft.easyclosest.databinding.ActivityCreateAccountBinding;
import com.eflexsoft.easyclosest.databinding.ActivityLoginBinding;
import com.eflexsoft.easyclosest.viewmodel.CreateAccountViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class CreateAccountActivity extends AppCompatActivity {


    CreateAccountViewModel viewModel;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    int RequestCode = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_account);

        ActivityCreateAccountBinding activityCreateAccountBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_account);
        activityCreateAccountBinding.toolb.setNavigationIcon(R.drawable.ic_left_arrow);
        activityCreateAccountBinding.toolb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                overridePendingTransition(R.anim.slide_in_rigth,R.anim.slide_out_left);
            }
        });

        viewModel = new ViewModelProvider(this).get(CreateAccountViewModel.class);

        activityCreateAccountBinding.showP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    activityCreateAccountBinding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    activityCreateAccountBinding.passwordConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    activityCreateAccountBinding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    activityCreateAccountBinding.passwordConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        viewModel.isSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
                    finish();
                } else {

                }
                activityCreateAccountBinding.loginContinue.revertAnimation();
//                activityLoginBinding.emailAddress.setError("missing");
//                activityLoginBinding.password.setError("missing");
            }
        });
        activityCreateAccountBinding.loginContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = activityCreateAccountBinding.Names.getText().toString();
                String email = activityCreateAccountBinding.email.getText().toString();
                String password = activityCreateAccountBinding.password.getText().toString();
                String confirmPassword = activityCreateAccountBinding.passwordConfirm.getText().toString();

                if (name.trim().isEmpty()) {
                    activityCreateAccountBinding.Names.setError("missing");
                } else if (email.trim().isEmpty()) {
                    activityCreateAccountBinding.email.setError("missing");
                } else if (password.trim().isEmpty()) {
                    activityCreateAccountBinding.password.setError("missing");
                } else if (confirmPassword.trim().isEmpty()) {
                    activityCreateAccountBinding.passwordConfirm.setError("missing");
                } else if (password.length() < 6) {
                    activityCreateAccountBinding.password.setError("too short at lest 6 characters");
                } else if (!password.equals(confirmPassword)) {
                    activityCreateAccountBinding.password.setError("mismatched");
                    activityCreateAccountBinding.passwordConfirm.setError("mismatched");
                } else {
                    viewModel.createAccount(email,name,password);
                    activityCreateAccountBinding.loginContinue.startAnimation();
                }

            }
        });

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);


        activityCreateAccountBinding.googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, RequestCode);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCode) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount googleSignInAccount;

            try {
                googleSignInAccount = task.getResult(ApiException.class);
                AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

                Toast.makeText(this, "Connecting to account", Toast.LENGTH_SHORT).show();

                viewModel.doGoogleSignIn(authCredential);
            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }

    }
}