package com.example.adity.invoicemaker.Login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adity.invoicemaker.Fragments.NavigationDrawer;
import com.example.adity.invoicemaker.R;
import com.example.adity.invoicemaker.model.LoginResponse;
import com.example.adity.invoicemaker.model.UserLogin;
import com.example.adity.invoicemaker.rest.ApiClient;
import com.example.adity.invoicemaker.rest.UserApiInterface;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText email,pass;
    Button signin;
    TextView forgot;
    ProgressDialog pd;
   // UserApiInterface apiService;
    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.pass);
        signin=(Button)findViewById(R.id.signin);
        forgot=(TextView) findViewById(R.id.forgot);

        final UserApiInterface apiService = ApiClient.getClient().create(UserApiInterface.class);


        /**
         *
         * this is for permissions
         */
        int permissionCheck1 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck3 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        int permissionCheck4 = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int permissionCheck5 = ContextCompat.checkSelfPermission(this, Manifest.permission.BROADCAST_SMS);

        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED&&permissionCheck2 != PackageManager.PERMISSION_GRANTED &&permissionCheck3 != PackageManager.PERMISSION_GRANTED&&permissionCheck4 != PackageManager.PERMISSION_GRANTED&& permissionCheck5 != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,  android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.READ_SMS, android.Manifest.permission.RECEIVE_SMS,android.Manifest.permission.BROADCAST_SMS
                    },123);
        }

        SharedPreferences preferences = getSharedPreferences("userData",0);
        String userApiKey = preferences.getString("apiKey","");
        Log.d("apikey",userApiKey);
       // Toast.makeText(getApplicationContext(), userApiKey, Toast.LENGTH_LONG).show();
       /* if(userApiKey!=null){
            Intent i=new Intent(MainActivity.this,NavigationDrawer.class);
            //  i.putExtra("apiKey",list.getApiKey());
            //i.putExtra("email",em);
            startActivity(i);
            finish();
        }
*/

        ActionBar a=getSupportActionBar();
        if(a!=null)
            a.hide();

        pd=new ProgressDialog(this);
      /* mAuth = FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();*/
       /* if(user!=null)
        {
            startActivity(new Intent(MainActivity.this,NavigationDrawer.class));
            finish();

        }*/

        signin.setOnClickListener(new View.OnClickListener() {
            @Override





            public void onClick(View v) {

                final String em=email.getText().toString().trim();
                final String pas=pass.getText().toString().trim();

                if((em.equals("")) || !(em.contains("@")) || !(em.contains(".")))
                {
                    email.setError("Enter valid Email");
                    email.requestFocus();

                }

                else if(pas.equals(""))
                {
                    pass.setError("please Enter a valid Password");
                    pass.requestFocus();
                }

                else
                {
                    pd.setMessage("Logging In");
                    pd.show();

               //     Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
                    Log.e("test","clicked!!!!");
                  Call<LoginResponse> loginResponseCall =  apiService.checkLogin(email.getText().toString(),pass.getText().toString());
                  //  Call<com.example.adity.invoicemaker.model.LoginResponse> loginResponseCall = apiService.checkLogin(email.getText().toString(),pass.getText().toString());
                    loginResponseCall.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            boolean error = response.body().isError();
                            Log.e("response" , response.body().toString());
                            String message  = response.body().getMessage();
                            UserLogin list = response.body().getUsers();
                            Log.e("test",message);
                            Log.e("test112121",list.getEmail()+"---"+list.getApiKey());

                            sharedPreferences = getSharedPreferences("userData",MODE_WORLD_READABLE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("apiKey",list.getApiKey());
                            editor.putString("email",list.getEmail());
                            editor.putString("companyName",list.getCompanyName());
                            editor.commit();

                            if(!error){
                                pd.hide();

                                Intent i=new Intent(MainActivity.this,NavigationDrawer.class);
                              //  i.putExtra("apiKey",list.getApiKey());
                                //i.putExtra("email",em);
                                startActivity(i);
                                finish();
                            }
                            else{
                                pd.setMessage(message);
                                pd.hide();


                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.e("error",t.toString());
                        }
                    });

                   /* mAuth.signInWithEmailAndPassword(em,pas).addOnCompleteListener(MainActivity.this,new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                pd.hide();
                                Intent i=new Intent(MainActivity.this,NavigationDrawer.class);
                                //i.putExtra("email",em);
                                startActivity(i);
                                finish();
                            }

                            else {
                                pd.hide();
                                if(task.getException().getMessage().toLowerCase().contains("password"))
                                { pass.setError(task.getException().getMessage());
                                        pass.requestFocus();
                                }

                                else if(task.getException().getMessage().toLowerCase().contains("no user"))
                                { email.setError("There is no user with this account");
                                    email.requestFocus();
                                }
                                else if(task.getException().getMessage().toLowerCase().contains("badly formatted"))
                                { email.setError(""+task.getException().getMessage());
                                    email.requestFocus();
                                }


                            }


                        }
                    });*/



                }
            }
        });




        Button sup=(Button)findViewById(R.id.signup);
        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,signup.class));

            }
        });



    }


}
