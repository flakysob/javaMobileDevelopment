package com.flakysob.kiralaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.flakysob.kiralaapp.Entity.KategoriEntity;
import java.util.List;

public class Kategori_RecyclerViewAdapter extends RecyclerView.Adapter<Kategori_RecyclerViewAdapter.Kayitlar>{

    private final RecyclerViewInterface recyclerViewInterface;

    private Context context;
    private List<KategoriEntity> kategoriler;

    public Kategori_RecyclerViewAdapter(Context context, List<KategoriEntity> kategoriler, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.kategoriler = kategoriler;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    //satır görünümünü adaptöre bağlama
    @NonNull
    @Override
    public Kayitlar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kategori,parent,false);
        return new Kayitlar(view, recyclerViewInterface);
    }

    //verileri alma, kontrollere aktarma satırlara ya da alt ögelere tıklama gibi işlemler
    @Override
    public void onBindViewHolder(@NonNull Kayitlar holder, int position) {
        KategoriEntity kategoriEntity = kategoriler.get(position);
        Long id = kategoriEntity.getId();
        String kategoriAdi = kategoriEntity.getKategoriAdi();

        holder.kategoriBaslik.setText(kategoriAdi);

    }
    //satır sayısı
    @Override
    public int getItemCount() {
        return kategoriler.size();
    }


    //kontrol tanımlamaları
    public class Kayitlar extends RecyclerView.ViewHolder{

        TextView kategoriBaslik;

        public Kayitlar(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            kategoriBaslik = itemView.findViewById(R.id.txtKategoriBaslik);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getBindingAdapterPosition();
                        KategoriEntity kategori = kategoriler.get(pos);
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(kategori);
                        }
                    }
                }
            });
        }
    }
}
