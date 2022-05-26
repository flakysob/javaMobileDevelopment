package com.flakysob.kiralaapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.flakysob.kiralaapp.Entity.KategoriEntity;
import com.flakysob.kiralaapp.Entity.UrunEntity;

import java.util.List;

public class Ilan_RecyclerViewAdapter extends RecyclerView.Adapter<Ilan_RecyclerViewAdapter.Kayitlar>{

    private final IlanRecyclerView ilanRecyclerView;

    private Context context;
    private List<UrunEntity> urunler;

    public Ilan_RecyclerViewAdapter(Context context, List<UrunEntity> urunler, IlanRecyclerView ilanRecyclerView) {
        this.context = context;
        this.urunler = urunler;
        this.ilanRecyclerView = ilanRecyclerView;
    }

    //satır görünümünü adaptöre bağlama
    @NonNull
    @Override
    public Kayitlar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ilanlar,parent,false);
        return new Kayitlar(view, ilanRecyclerView);
    }

    //verileri alma, kontrollere aktarma satırlara ya da alt ögelere tıklama gibi işlemler
    @Override
    public void onBindViewHolder(@NonNull Kayitlar holder, int position) {
        UrunEntity urunEntity = urunler.get(position);
        Long id = urunEntity.getId();
        String urunAdi = urunEntity.getUrunBaslik();
        String urunFiyat = ""+urunEntity.getFiyat();
        String urunResim = urunEntity.getUrunResim();

        holder.urunResim.setImageURI(Uri.parse(urunResim));
        holder.txtIlanBaslik.setText(urunAdi);
        holder.IlanFiyati.setText(urunFiyat);

    }
    //satır sayısı
    @Override
    public int getItemCount() {
        return urunler.size();
    }


    //kontrol tanımlamaları
    public class Kayitlar extends RecyclerView.ViewHolder{

        TextView txtIlanBaslik,IlanFiyati;
        ImageView urunResim;

        public Kayitlar(@NonNull View itemView, IlanRecyclerView ilanRecyclerView) {
            super(itemView);

            txtIlanBaslik = itemView.findViewById(R.id.txtIlanBaslik);
            IlanFiyati = itemView.findViewById(R.id.IlanFiyati);
            urunResim = itemView.findViewById(R.id.urunResim);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ilanRecyclerView != null){
                        int pos = getBindingAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            UrunEntity urunEntity = urunler.get(pos);
                            ilanRecyclerView.onItemClick(urunEntity);
                        }
                    }
                }
            });
        }
    }
}
