package com.example.adity.invoicemaker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adity.invoicemaker.Fragments.NavigationDrawer;
import com.example.adity.invoicemaker.Login.MainActivity;
import com.example.adity.invoicemaker.model.LoginResponse;
import com.example.adity.invoicemaker.model.VendorCreateResponse;
import com.example.adity.invoicemaker.rest.ApiClient;
import com.example.adity.invoicemaker.rest.UserApiInterface;
import com.example.adity.invoicemaker.rest.VendorApiInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientDetails extends AppCompatActivity {
    AutoCompleteTextView STATE;
    AutoCompleteTextView Country;
    EditText name,phone,email,addline,addline2,zip,GSTIN,PAN_NO;
    String Name,Phone,Email,add1,add2,zp,st,pan_no,gstin,country;
    Map<String,String> mp;
    ProgressDialog pd;
     URL url,State ;
     NetworkResponse.ObjectCountry obj;
    HashMap<String,Integer> hash=new HashMap<>();
    ArrayList<String> str=new ArrayList<String>();

    ArrayList<String> states=new ArrayList<>();
    String userApiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pd=new ProgressDialog(ClientDetails.this);
        url=NetworkResponse.buildUrlCountry();
        obj=new NetworkResponse.ObjectCountry(hash,str);
        new BackGround().execute();
        name=(EditText)findViewById(R.id.clientname);
        phone=(EditText)findViewById(R.id.clientphone);
        email=(EditText)findViewById(R.id.clientemail);
        addline=(EditText)findViewById(R.id.Address1);
        addline2=(EditText)findViewById(R.id.Address2);
        STATE=(AutoCompleteTextView)findViewById(R.id.Address3);
        zip=(EditText)findViewById(R.id.zip);
        GSTIN=(EditText)findViewById(R.id.gst);
        PAN_NO=(EditText)findViewById(R.id.pan);
        Country=(AutoCompleteTextView)findViewById(R.id.Country);
        name.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        SharedPreferences preferences = getSharedPreferences("PROJECT_NAME",android.content.Context.MODE_PRIVATE);
        userApiKey= preferences.getString("apiKey","");

        Log.d("apiKet",userApiKey);
        STATE.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)

                {   String s = Country.getText().toString().trim();
                    if(!(s.equals("")||s.contains("test"))) {
                        if(s.equals("New Delhi"))
                        {  s="NCT";}
                        Integer id = obj.hash.get(s);
                        //   Toast.makeText(ClientDetails.this, id.toString(), Toast.LENGTH_SHORT).show();
                        if(id!=null)
                        { State = NetworkResponse.buildUrlState(id);
                        new BackGroundState().execute();
                    }}
                    else
                    {
                        STATE.setError("Please enter the country first.");
                    }
                }
            }
        });
        Button save=(Button)findViewById(R.id.buttonclient) ;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Name=name.getText().toString();
                Phone=phone.getText().toString();
                Email=email.getText().toString();
                pan_no=PAN_NO.getText().toString();
                gstin=GSTIN.getText().toString();
                add1=addline.getText().toString();
                add2=addline2.getText().toString();
                st=STATE.getText().toString();
                zp=zip.getText().toString();
                country=Country.getText().toString();

                mp=new HashMap<>();

                if(Name.isEmpty() || Name.toLowerCase().contains("test"))
                {
                    name.setError("Please enter the Company Name");
                name.requestFocus();
                }
                else if(Phone.isEmpty())
                {phone.setError("Please enter the Phone number");
                    phone.requestFocus();
                }
                else if(Email.isEmpty() || !Email.contains("@")||!Email.contains(".")|| Email.toLowerCase().contains("test"))
                { email.setError("Please enter the Email");
                    email.requestFocus();
                }
                else if(gstin.isEmpty()|| gstin.toLowerCase().contains("test"))
                {   GSTIN.setError("Please enter the GSTIN");
                    GSTIN.requestFocus();
                }
                else if(pan_no.isEmpty() || pan_no.toLowerCase().contains("test"))
                {PAN_NO.setError("Please enter the Pan No");
                    PAN_NO.requestFocus();
                }
                else if(add1.isEmpty() || add1.toLowerCase().contains("test"))
                {   addline.setError("Please enter the Address");
                    addline.requestFocus();
                }
                else if(add2.isEmpty() || add2.toLowerCase().contains("test"))
                {   addline2.setError("Please enter the Address");
                    addline2.requestFocus();
                }
                else if(country.isEmpty() || country.toLowerCase().contains("test") || (obj.string.indexOf(country)==-1))
                {
                  Country.setError("Please enter the Country");
                    Country.requestFocus();
                }
                else if(st.isEmpty() || st.toLowerCase().contains("test")||(states.indexOf(st)==-1))
                {   STATE.setError("Please enter the State");
                    STATE.requestFocus();
                }
                else if(zp.isEmpty())
                {   zip.setError("Please enter the Zip code");
                    zip.requestFocus();
                }
                else {
                    pd.setMessage("Please Wait...");
                    pd.show();
                   // mp.put("Phone", Phone);


                    HashMap<String,String> jsonObject=new HashMap<>();

                    //    JSONObject jsonObject=new JSONObject();

                        jsonObject.put("vendorName",Name);
                        jsonObject.put("email",Email);
                        jsonObject.put("address1",addline.getText().toString());
                        jsonObject.put("address2",addline2.getText().toString());
                        jsonObject.put("state",STATE.getText().toString());
                        jsonObject.put("zipCode",zip.getText().toString());
                        jsonObject.put("phoneNo",Phone);
                        jsonObject.put("panNo",pan_no);
                        jsonObject.put("gstin",gstin);
                        jsonObject.put("country",country);

                     //   hmap= CodeBase.jsonToMap(jsonObject);

                    Log.d("create vendr",jsonObject.toString());



                    final VendorApiInterface apiService = ApiClient.getClient().create(VendorApiInterface.class);
                    Call<VendorCreateResponse> addVendorResponse = apiService.addVendor(userApiKey, jsonObject);

                    addVendorResponse.enqueue(new Callback<VendorCreateResponse>() {
                        @Override
                        public void onResponse(Call<VendorCreateResponse> call, Response<VendorCreateResponse> response) {
                            Log.d("fguefhe",call.request().body().toString()+"---"+call.request().headers());

                         Log.d("TESTTESTTEST",response.body().toString());


                            String result="";
                            try {
                                 Log.d("resoibse",response.body().getMessage());

                                if(!response.body().isError()){

                                    pd.setMessage(response.body().getMessage());
                                    pd.hide();
                                     return;

                                }
                                else{
                                    pd.setMessage(response.body().getMessage());
                                    pd.hide();
                                    return;

                                }
                            }catch (Exception e){
                                Log.d("aaa","No Response IOE="+e);
                                pd.hide();
                                return;
                            }

                        }

                        @Override
                        public void onFailure(Call<VendorCreateResponse> call, Throwable t) {

                        }
                    });

                    finish();
                }

            }
        });

    }

    public class BackGround extends AsyncTask<Void, Void, String> {



        @Override
        protected String doInBackground(Void... voids) {
            String s=null;
            try {
                s = NetworkResponse.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }


        @Override
        protected void onPostExecute(String result) {
            try {
                obj = NetworkResponse.parseJSON(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ClientDetails.this, android.R.layout.simple_expandable_list_item_1, obj.string);
            Country.setAdapter(adapter);

        }

    }
    public class BackGroundState extends AsyncTask<Void, Void, String> {
ProgressDialog p;
        @Override
        protected void onPreExecute() {
            p=new ProgressDialog(ClientDetails.this);
            p.setMessage("Retrieving States");
            p.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String s=null;
            try {
                s = NetworkResponse.getResponseFromHttpUrl(State);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }


        @Override
        protected void onPostExecute(String result) {
            try {
                states = NetworkResponse.parseJSONStates(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            Toast.makeText(MainActivity.this, ""+Countries.get(0), Toast.LENGTH_SHORT).show();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(ClientDetails.this, android.R.layout.simple_expandable_list_item_1,states);
            STATE.setAdapter(adapter);
            p.hide();
        }
    }


}

