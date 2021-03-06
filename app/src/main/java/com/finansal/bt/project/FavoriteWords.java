package com.finansal.bt.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

public class FavoriteWords extends AppCompatActivity {

    private ListView VeriListele;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_words);


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        VeriListele=(ListView)findViewById(R.id.liste);
        Listele();
        VeriListele.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int sayac=position+1;
                Database vt = new Database(FavoriteWords.this);
                AlertDialog.Builder builder=new AlertDialog.Builder(FavoriteWords.this);
                builder.setTitle("Alert");
                builder.setMessage("Are you sure  you want to delete the word ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Database vt = new Database(FavoriteWords.this);
                        vt.VeriSil(sayac);
                        Listele();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog=builder.create();
                alertDialog.show();
            }
        });
    }
    public void Listele(){
        Database db = new Database(FavoriteWords.this);
        List<String> list = db.VeriListele();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(FavoriteWords.this, android.R.layout.simple_list_item_1,android.R.id.text1,list);
        VeriListele.setAdapter(adapter);
    }
}
