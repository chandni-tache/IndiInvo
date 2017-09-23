package com.example.adity.invoicemaker.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.adity.invoicemaker.ClientDetails;
import com.example.adity.invoicemaker.Listener.onItemTouchListener;
import com.example.adity.invoicemaker.R;
import com.example.adity.invoicemaker.Edits.VendorEDIT;
import com.example.adity.invoicemaker.Vendor_Details;
import com.example.adity.invoicemaker.adapter.Vendor_Adapter;
import com.example.adity.invoicemaker.model.VendorDetails;
import com.example.adity.invoicemaker.model.VendorListResponse;
import com.example.adity.invoicemaker.rest.ApiClient;
import com.example.adity.invoicemaker.rest.UserApiInterface;
import com.example.adity.invoicemaker.rest.VendorApiInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class vendorfragment extends Fragment implements com.example.adity.invoicemaker.Listener.onItemTouchListener {
    FrameLayout zerovendor;
    Button addvendor;
    Vendor_Adapter adapter;
    RecyclerView rv;
    FloatingActionButton fab;
    ArrayList<VendorDetails> arrayList;
    String name,email,gstin,pan,add1,add2,zip,state,number,country;
    ProgressDialog pd;
    onItemTouchListener onItemTouchListener;
    private  Paint p=new Paint();
    public vendorfragment() {


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return (inflater.inflate(R.layout.activity_vendorfragment,container,false));
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Vendors");

        zerovendor=(FrameLayout)getActivity().findViewById(R.id.novendordetails);
        addvendor=(Button)getActivity().findViewById(R.id.createvendor);

        arrayList=new ArrayList<>();
        rv= (RecyclerView)getActivity().findViewById(R.id.venlist);

        adapter =new Vendor_Adapter(getActivity(),arrayList,onItemTouchListener);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(adapter);

        pd=new ProgressDialog(getActivity());
        pd.setMessage("Please Wait ...");
        pd.show();
//        Read();
        adapter.setClickListener(this);

        addvendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.clear();
                zerovendor.setVisibility(View.GONE);
                startActivity(new Intent(getActivity(),ClientDetails.class));
            }
        });

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT |ItemTouchHelper.LEFT){
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int pos = viewHolder.getAdapterPosition();
                final VendorDetails obj = arrayList.get(pos);
                if(direction==ItemTouchHelper.RIGHT) {

                    AlertDialog DeletionDialogBox = new AlertDialog.Builder(getActivity())
                            //set message, title, and icon
                            .setTitle("Delete")
                            .setMessage("Do you really want to delete the following bank details?\n\n" + "\t\tVendor Name-" + obj.getName() + "\n\t\tE-mail -"
                                    + obj.getEmail())
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //your deleting code
                                    arrayList.remove(pos);
                                    adapter.notifyDataSetChanged();
                                    chklayout();

                                //    DatabaseReference  db1 = FirebaseDatabase.getInstance().getReference("Company/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+obj.v_name);
                                  //  db1.removeValue();

                                    Toast.makeText(getActivity(), "DELETED", Toast.LENGTH_SHORT).show();

                                    dialog.dismiss();
                                }

                            })

                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    adapter.notifyDataSetChanged();
                                    dialog.dismiss();

                                }
                            })
                            .create();
                    DeletionDialogBox.setCanceledOnTouchOutside(false);
                    DeletionDialogBox.show();
                }
                else
                {    Intent i=new Intent(getActivity(),VendorEDIT.class);
                    i.putExtra("name",obj.getName());
                    i.putExtra("phone",obj.getPhoneNo());
                    i.putExtra("email",obj.getEmail());
                    i.putExtra("address1",obj.getAddress1());
                    i.putExtra("address2",obj.getAddress2());
                    i.putExtra("State",obj.getState());
                    i.putExtra("Zip",obj.getZipCode());
                    i.putExtra("gstin",obj.getGstin());
                    i.putExtra("pan",obj.getPanNo());
                    i.putExtra("Country",obj.getCountry());
                    startActivity(i);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX<0){
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        Drawable d=getResources().getDrawable(R.drawable.ic_edit_white);
                        icon = InvoiceListFragment.drawableToBitmap(d);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                    else
                    {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background,p);
                        Drawable d=getResources().getDrawable(R.drawable.ic_delete_white);
                        icon = InvoiceListFragment.drawableToBitmap(d);
                        //icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_send);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);

                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv);




        fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.clear();
                startActivity(new Intent(getActivity(),ClientDetails.class));
            }
        });








    }



    public static class ObjectVendor{
        public String v_name,v_email,v_gstin,v_pan,v_add1,v_add2,v_state,v_zip,v_phone,v_country;
        ObjectVendor(String name,String mail,String gstin,String pan,String add1,String add2,String state,String zip,String phone,String country){

            v_name=name;
            v_email=mail;
            v_gstin=gstin;
            v_pan=pan;
            v_add1=add1;
            v_add2=add2;
            v_zip=zip;
            v_state=state;
            v_phone=phone;
            v_country=country;
        }

    }

    @Override
    public void onClick(View view, int position) {
        Bundle extras = getActivity().getIntent().getExtras();
        if(extras!=null){
            String act = extras.getString("from");

            if(act.equals("Invoice")) {



                VendorDetails ob=arrayList.get(position);
                Intent i=new Intent();
                i.putExtra("name",ob.getName());
                i.putExtra("phone",ob.getPhoneNo());
                i.putExtra("email",ob.getEmail());
                i.putExtra("address1",ob.getAddress1());
                i.putExtra("address2",ob.getAddress2());
                i.putExtra("State",ob.getState());
                i.putExtra("Zip",ob.getCountry());
                i.putExtra("gstin",ob.getGstin());
                i.putExtra("pan",ob.getPanNo());
                i.putExtra("Country",ob.getCountry());



            }}
        else{

        }
    }

    public void Read()
    {


        SharedPreferences preferences = this.getActivity().getSharedPreferences("PROJECT_NAME",android.content.Context.MODE_PRIVATE);
      String  userApiKey= preferences.getString("apiKey","");


        final VendorApiInterface apiService = ApiClient.getClient().create(VendorApiInterface.class);
        Call<VendorListResponse> vendorListResponseCall =  apiService.getAllVendors(userApiKey);

        vendorListResponseCall.enqueue(new Callback<VendorListResponse>() {
            @Override
            public void onResponse(Call<VendorListResponse> call, Response<VendorListResponse> response) {
                Log.d("response",response.body().toString());
                List<VendorDetails> vendorDetailsList = response.body().getVendorDetailsList();
                Log.d("NO of venfors" ,vendorDetailsList.get(0).getEmail());

                for(VendorDetails v: vendorDetailsList){
                    arrayList.add(v);
                }
              //  arrayList.add(obj);
                adapter.notifyDataSetChanged();
                chklayout();
                pd.hide();

            }

            @Override
            public void onFailure(Call<VendorListResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "error"+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        // DatabaseReference db= FirebaseDatabase.getInstance().getReference("Company/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());


        /*db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot Company:dataSnapshot.getChildren())
                {

                    name=Company.getKey();
                    for(DataSnapshot ds:Company.getChildren())
                    {

                        if(ds.getKey().equals("Address1"))
                        {
                            add1=ds.getValue(String.class);
                        }
                        if(ds.getKey().equals("Address2"))
                        {
                            add2=ds.getValue(String.class);
                        }
                        if(ds.getKey().equals("Zip"))
                        {
                            zip=ds.getValue(String.class);
                        }
                        if(ds.getKey().equals("State"))
                        {
                            state=ds.getValue(String.class);
                        }
                        if(ds.getKey().equals("Email"))
                        {
                            email=ds.getValue(String.class);
                        }
                        if(ds.getKey().equals("Gstin"))
                        {
                            gstin=ds.getValue(String.class);
                        }
                        if(ds.getKey().equals("Pan no"))
                        {
                            pan=ds.getValue(String.class);
                        }
                        if(ds.getKey().equals("Phone"))
                        {
                            number=ds.getValue(String.class);
                        }

                        if(ds.getKey().equals("Country"))
                        {
                            country=ds.getValue(String.class);
                        }
                    }
                    Vendor_Details.ObjectVendor obj=new Vendor_Details.ObjectVendor(name,email,gstin,pan,add1,add2,state,zip,number,country);
                    arrayList.add(obj);
                    adapter.notifyDataSetChanged();

                }





               chklayout();
                pd.hide();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "error"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/



    }

    void chklayout()
    {
        if(arrayList.isEmpty())
        {
            rv.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
            zerovendor.setVisibility(View.VISIBLE);
        }
        else{
            rv.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
            zerovendor.setVisibility(View.GONE);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        arrayList.clear();
        Read();
    }

}
