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

public class CustomAdapter1 extends ArrayAdapter {
    private ArrayList<Tip2DataClass> data;
    private Activity context;

    public CustomAdapter1(ArrayList<Tip2DataClass> data, Activity context){
        super(context, R.layout.list_item,data);
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //activitenin layout inflateri alınır
        LayoutInflater inflater = context.getLayoutInflater();
        //aktivitenin layoutu custom_view yapılır
        View customView = inflater.inflate(R.layout.list_item,null,true);
        //textView texti seçilen nesnenin ismi yapılır
        TextView tarih = customView.findViewById(R.id.textView2);
        TextView distance = customView.findViewById(R.id.textView3);
        tarih.setText(data.get(position).getTarih());
        distance.setText(Double.toString(data.get(position).getGidilen_yol()));

        return customView;
    }
}
