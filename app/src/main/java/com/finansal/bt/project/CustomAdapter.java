package com.finansal.bt.project;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Kübra Özbaykus on 17.10.2017.
 */

public class CustomAdapter extends BaseAdapter{

    TextView baslik;
    ImageView resim;

    private LayoutInflater mInflater;
    private List<String> isimListesi;
    private List<String>resimListesi;

    public CustomAdapter(Activity activity, List<String> isimler,List<String>resimler) {
        mInflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        isimListesi=isimler;
        resimListesi=resimler;
    }

    @Override
    public int getCount() {
        return isimListesi.size();
    }

    @Override
    public Object getItem(int position) {
        return isimListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View satirView=mInflater.inflate(R.layout.satir,null);

        baslik=(TextView)satirView.findViewById(R.id.beginnerIsim);
        resim=(ImageView)satirView.findViewById(R.id.beginnerResim);

        String isim=isimListesi.get(position);
        baslik.setText(isim);

          Glide.with(mInflater.getContext()).load(resimListesi.get(position).toString()).into(resim);

        return satirView;
    }
}
