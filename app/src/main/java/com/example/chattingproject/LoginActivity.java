package com.example.chattingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    EditText emailLogin, nameLogin, pwdLogin;
    Button joinBtn, loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailLogin = findViewById(R.id.login_email);
        nameLogin = findViewById(R.id.login_name);
        pwdLogin = findViewById(R.id.login_pwd);

        joinBtn = findViewById(R.id.login_join);
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, JoinActivity.class));
                finish();
            }
        });

        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailLogin.getText().toString();
                String pwd = pwdLogin.getText().toString();

                loginStart(email, pwd);
            }
        });
    }


    public void loginStart(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                Toast.makeText(LoginActivity.this,"존재하지 않는 id 입니다." ,Toast.LENGTH_SHORT).show();

                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(LoginActivity.this,"이메일 형식이 맞지 않습니다." ,Toast.LENGTH_SHORT).show();

                            } catch (FirebaseNetworkException e) {
                                Toast.makeText(LoginActivity.this,"Firebase NetworkException" ,Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                Toast.makeText(LoginActivity.this,"Exception" ,Toast.LENGTH_SHORT).show();

                            }

                        }else{


                            currentUser = mAuth.getCurrentUser();

                            Toast.makeText(LoginActivity.this, "로그인 성공" + "/" + currentUser.getEmail() + "/" + currentUser.getUid() ,Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }

                    }
                });
    }

    //로그인 되어있으면 바로 메인으로 넘어가기
    @Override
    protected void onStart() {
        super.onStart();

        currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}
