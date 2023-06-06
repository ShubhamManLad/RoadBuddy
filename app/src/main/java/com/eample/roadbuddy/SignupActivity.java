package com.eample.roadbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private EditText email_edittext;
    private EditText password_edittext;
    private TextView login_textview;
    private Button signup_button;

    String email,password;
    int mode;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email_edittext = findViewById(R.id.new_emailID_edittext);
        password_edittext = findViewById(R.id.new_password_edittext);
        login_textview = findViewById(R.id.login_textview);
        signup_button = findViewById(R.id.signup_button);

        mAuth = FirebaseAuth.getInstance();
        mode = getIntent().getIntExtra("mode",0);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = email_edittext.getText().toString();
                password = password_edittext.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(SignupActivity.this, "Enter a valid email and password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (mode == 55){
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else if (mode == 105){
                                Intent intent = new Intent(getApplicationContext(),RTO_MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }

            }
        });

        login_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}