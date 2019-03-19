package com.hutech.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hutech.model.BanGhi;
import com.hutech.quanlychitieu.R;

public class BanGhiAdapter extends ArrayAdapter<BanGhi> {
    Activity context;
    int resource;

    public BanGhiAdapter(Activity context, int resource) {
        super(context, resource);

        this.context= context;
        this.resource= resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflatter= this.context.getLayoutInflater();
        View custom= inflatter.inflate(this.resource, null);

        TextView txtDongTien= custom.findViewById(R.id.txtDongTien);
        TextView txtThoiGian= custom.findViewById(R.id.txtThoiGian);
        TextView txtHoatDong= custom.findViewById(R.id.txtHoatDong);
        TextView txtTien= custom.findViewById(R.id.txtTien);
        TextView txtMieuTa= custom.findViewById(R.id.txtMieuTa);


        BanGhi selected= getItem(position);

        txtThoiGian.setText(selected.getThoiGian());
        txtTien.setText(""+selected.getSoTien());
        txtMieuTa.setText(selected.getNoiDung());
        txtDongTien.setText(selected.getTenDongTien());
        txtHoatDong.setText(selected.getTenHoatDong());

        return custom;
    }
}
