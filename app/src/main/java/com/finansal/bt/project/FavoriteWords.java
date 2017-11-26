package com.finansal.bt.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class FavoriteWords extends AppCompatActivity {

    private ListView VeriListele;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_words);
        VeriListele=(ListView)findViewById(R.id.liste);
        Listele();
    }
    public void Listele(){
        Database db = new Database(FavoriteWords.this);
        List<String> list = db.VeriListele();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(FavoriteWords.this, android.R.layout.simple_list_item_1,android.R.id.text1,list);
        VeriListele.setAdapter(adapter);
    }
}
