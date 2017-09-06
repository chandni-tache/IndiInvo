package com.example.adity.invoicemaker.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.adity.invoicemaker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signup extends AppCompatActivity {

    EditText fn,email,pass,repass,phone;
    CheckBox cb;
    Button signup;
    DatabaseReference db;
    FirebaseAuth mAuth;
    Boolean mAllowNavigation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fn = (EditText) findViewById(R.id.fn);
        email = (EditText) findViewById(R.id.enteremail);
        pass = (EditText) findViewById(R.id.enterpass);
        repass = (EditText) findViewById(R.id.repass);
        phone = (EditText) findViewById(R.id.num);
        cb = (CheckBox) findViewById(R.id.cb);
        signup = (Button) findViewById(R.id.signup);

    //    mAuth = FirebaseAuth.getInstance();

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name, em, pwd, no;
                name = fn.getText().toString().trim();
                em = email.getText().toString().trim();
                pwd = pass.getText().toString().trim();
                no = phone.getText().toString().trim();

                if (name.equals("")) {
                    fn.setError("Enter a valid name");
                    fn.requestFocus();
                }
                else if (em.isEmpty()||!em.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                    email.setError("Enter  valid Email");
                    email.requestFocus();

                }
                else if (pwd.isEmpty()||pwd.length()<6) {
                    pass.setError("please Enter a valid Password");
                    pass.requestFocus();

                }
                else if (no.length() < 10) {
                    phone.setError("Please enter a valid number");
                    phone.requestFocus();

                }
                else if (!cb.isChecked()) {
                    cb.setError("please check this");
                    cb.requestFocus();

                }
                else {
                    startActivity(new Intent(signup.this,OTPCheck.class).putExtra("number",no).putExtra("company_name",fn.getText().toString()).putExtra("Email",em).putExtra("Contact_person",repass.getText().toString()).putExtra("password",pwd));


                }
            }
        });



    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(signup.this,MainActivity.class));
    }
}
