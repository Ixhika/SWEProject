package com.example.sweproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    LottieAnimationView lottie;
    String email, password;
    Button signIn, signUp;
    EditText userEmail, userPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.black));

        lottie = findViewById(R.id.lottie1);
        signIn = findViewById(R.id.signIn);
        signUp = findViewById(R.id.signUp);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        mAuth = FirebaseAuth.getInstance();

        signIn.setOnClickListener(v -> {
            String e , p;
            email = userEmail.getText().toString().trim();
            password = userPassword.getText().toString().trim();
            if(TextUtils.isEmpty(email)){
                Toast.makeText(MainActivity.this,"Enter email",Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(MainActivity.this,"Enter password",Toast.LENGTH_SHORT).show();
                return;
            }


            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in item's information
                            Toast.makeText(MainActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this , HomePage.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the item.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    });
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser==null){
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }
//    }
}
