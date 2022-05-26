package com.flakysob.kiralaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flakysob.kiralaapp.Entity.KategoriEntity;

import java.util.List;

public class KategoriSpinnerAdapter extends ArrayAdapter<KategoriEntity> {

    LayoutInflater layoutInflater;
    List<KategoriEntity> kategoriler;

    public KategoriSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<KategoriEntity> kategoris) {
        super(context, resource, kategoris);
        kategoriler = kategoris;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = layoutInflater.inflate(R.layout.spinner_kategori,null,true);
        KategoriEntity kategori = getItem(position);
        TextView tvKategoriAd = (TextView)rowView.findViewById(R.id.tvspinner);
        tvKategoriAd.setText(kategori.getKategoriAdi());
        return rowView;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.spinner_kategori,parent,false);
        }
        KategoriEntity kategori = getItem(position);
        TextView textView = (TextView)convertView.findViewById(R.id.tvspinner);
        textView.setText(kategori.getKategoriAdi());
        return convertView;
    }
}
