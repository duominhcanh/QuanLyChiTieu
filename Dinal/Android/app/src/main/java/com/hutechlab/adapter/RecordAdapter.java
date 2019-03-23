package com.hutechlab.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hutechlab.model.Record;
import com.hutechlab.monaget.R;

public class RecordAdapter extends ArrayAdapter<Record> {
    Activity context;
    int resource;

    public RecordAdapter(Activity context, int resource) {
        super(context, resource);
        this.context= context;
        this.resource= resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflatter= this.context.getLayoutInflater();
        View custom= inflatter.inflate(this.resource, null);

        TextView txtDescription= custom.findViewById(R.id.txtDescription);
        TextView txtActivity= custom.findViewById(R.id.txtActivity);
        TextView txtAmmount= custom.findViewById(R.id.txtAmmount);
        TextView txtTime= custom.findViewById(R.id.txtTime);
        ImageView imgFlow= custom.findViewById(R.id.imgFlow);
        View viewHr= custom.findViewById(R.id.viewHr);

        Record selected= getItem(position);

        txtDescription.setText(selected.getDescription());
        txtActivity.setText(selected.getActivityName());
        txtAmmount.setText(""+selected.getAmmount());
        txtTime.setText(selected.getTime());
        if(selected.getFlowId() == 1){
            imgFlow.setImageResource(R.drawable.get64);
            viewHr.setBackgroundResource(R.color.green);
        }
        else if(selected.getFlowId() == 2){
            imgFlow.setImageResource(R.drawable.spend64);
            viewHr.setBackgroundResource(R.color.pink);
        }

        return custom;
    }
}
