package com.example.adity.invoicemaker.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.adity.invoicemaker.R;
import com.example.adity.invoicemaker.typesofinvoice;

/**
 * Created by adity on 7/10/2017.
 */

public class invoice_fragment extends Fragment {

    public invoice_fragment() {


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
                  return (inflater.inflate(R.layout.invoice,container,false));
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Invoice");

            Button invoice = (Button) getActivity().findViewById(R.id.createinvoice);
            invoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), typesofinvoice.class));
                }
            });

    }


}
