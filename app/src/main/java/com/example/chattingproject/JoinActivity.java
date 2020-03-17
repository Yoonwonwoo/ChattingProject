package com.example.chattingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class JoinActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    EditText emailText, nameText, pwdText;
    Button signBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mAuth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.sign_email);
        nameText = findViewById(R.id.sign_name);
        pwdText = findViewById(R.id.sign_pwd);
        signBtn = findViewById(R.id.sign_btn);

        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailText.getText().toString();
                String name = nameText.getText().toString();
                String pwd = pwdText.getText().toString();

                joinStart(email, name, pwd);

            }
        });

    }

    public void joinStart(String email, final String name, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(JoinActivity.this,"비밀번호가 간단해요." ,Toast.LENGTH_SHORT).show();

                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(JoinActivity.this,"email 형식에 맞지 않습니다." ,Toast.LENGTH_SHORT).show();

                            } catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(JoinActivity.this,"이미 존재하는 email입니다." ,Toast.LENGTH_SHORT).show();

                            } catch(Exception e) {
                                Toast.makeText(JoinActivity.this,"다시 확인해주세요." ,Toast.LENGTH_SHORT).show();

                            }
                        }else{

                            currentUser = mAuth.getCurrentUser();

                            Toast.makeText(JoinActivity.this, "가입 성공  " + name + currentUser.getEmail() + "/" + currentUser.getUid() ,Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(JoinActivity.this, MainActivity.class));
                            finish();
                        }


                    }
                });

    }
}
