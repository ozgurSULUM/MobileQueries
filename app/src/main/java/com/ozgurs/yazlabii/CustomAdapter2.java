package com.ozgurs.yazlabii;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;

public class CustomAdapter2 extends ArrayAdapter {
    private ArrayList<Tip3DataClass> data;
    private Activity context;

    public CustomAdapter2(ArrayList<Tip3DataClass> data, Activity context){
        super(context, R.layout.list_item2,data);
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //activitenin layout inflateri alınır
        LayoutInflater inflater = context.getLayoutInflater();
        //aktivitenin layoutu custom_view yapılır
        View customView = inflater.inflate(R.layout.list_item2,null,true);
        //textView texti seçilen nesnenin ismi yapılır
        TextView tarih = customView.findViewById(R.id.textView9);
        TextView pickup_location = customView.findViewById(R.id.textView10);
        TextView drop_location = customView.findViewById(R.id.textView11);
        TextView distance = customView.findViewById(R.id.textView12);

        tarih.setText(data.get(position).getTarih());
        pickup_location.setText(Integer.toString(data.get(position).getPickup_location()));
        drop_location.setText(Integer.toString(data.get(position).getDrop_location()));
        distance.setText(Double.toString(data.get(position).getTrip_distance()));

        return customView;
    }
}
