package com.example.adity.invoicemaker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adity.invoicemaker.R;
import com.example.adity.invoicemaker.Vendor_Details;
import com.example.adity.invoicemaker.Listener.onItemTouchListener;
import com.example.adity.invoicemaker.model.VendorDetails;

import java.util.ArrayList;

/**
 * Created by shivani on 13/7/17.
 */

public class Vendor_Adapter extends RecyclerView.Adapter<Vendor_Adapter.ViewHolder> {
    public  Context mContext;
    private com.example.adity.invoicemaker.Listener.onItemTouchListener onItemTouchListener;
    ArrayList<VendorDetails> objects;

    /**
     * for vendor list
     * @param mContext
     * @param objects
     * @param listener
     */
    public Vendor_Adapter(Context mContext, ArrayList<VendorDetails> objects, onItemTouchListener listener)
    {
        this.mContext=mContext;
        this.objects=objects;
        this.onItemTouchListener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(mContext).inflate(R.layout.vendor_item_details, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final VendorDetails obj=objects.get(position);

        holder.name.setText(obj.getName());

        holder.email.setText(obj.getEmail());


    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void setClickListener(onItemTouchListener itemClickListener) {
        this.onItemTouchListener = itemClickListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name,gstin,pan,email;
        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.v_name);
           email=(TextView)itemView.findViewById(R.id.v_email);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onItemTouchListener.onClick(view,getAdapterPosition());
        }
    }
}
