package com.example.easylearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText email,password,confirm;
    Button button;
    TextView sign;
    FirebaseAuth mFirebaseAuth;

    //public static final String EMAIL_KEY = "emailName";
    //public static final String PASSWORD_KEY = "password";
    //public static final String TAG = "SuccessAddInfo";
    DatabaseReference databaseUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        databaseUserInfo = FirebaseDatabase.getInstance().getReference("User_Email_Password");

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        button = findViewById(R.id.button);
        sign = findViewById(R.id.back);
        confirm = findViewById(R.id.confirm);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailid = email.getText().toString();
                String pwd = password.getText().toString();
                String re = confirm.getText().toString();

                if(emailid.isEmpty()){
                    email.setError("Please enter email address");
                    email.requestFocus();
                }
                else if(pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else if(re.isEmpty()){
                    confirm.setError("Please confirm your password");
                }
                else if(emailid.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this,"Fields are empty!",Toast.LENGTH_SHORT);
                }
                else if(!(re.equalsIgnoreCase(pwd))){
                    Toast.makeText(MainActivity.this,"Confirm wrong!",Toast.LENGTH_SHORT);
                }
                else if(re.equalsIgnoreCase(pwd)){
                    //addInfo();

                    mFirebaseAuth.createUserWithEmailAndPassword(emailid,pwd)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,
                                 "SignUp Unsuccessful, Please try again", Toast.LENGTH_SHORT);
                            }
                            else{
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainActivity.this,"Error Ocurred!",Toast.LENGTH_SHORT);
                }

            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }

    private void addInfo(){
        String emailInfo = email.getText().toString();
        int medNumber = 1;
        String id = databaseUserInfo.push().getKey();

        UserINFO userinfo = new UserINFO(id, emailInfo, medNumber);
        databaseUserInfo.child(id).setValue(userinfo);
    }

}

