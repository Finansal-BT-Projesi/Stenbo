package com.finansal.bt.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelActivity extends AppCompatActivity {



    Button btnBeginner;
    Button btnElementary;
    Button btnIntermediate;
    Button btnKelimelerim;

    Animation uptodown,downtoup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        btnBeginner = (Button) findViewById(R.id.btnBeginner);
        btnElementary= (Button) findViewById(R.id.btnElementary);
        btnIntermediate= (Button) findViewById(R.id.btnAdvanced);
        btnKelimelerim= (Button) findViewById(R.id.btnKelimelerim);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        btnBeginner.setAnimation(uptodown);
        btnElementary.setAnimation(uptodown);
        btnIntermediate.setAnimation(uptodown);
        btnKelimelerim.setAnimation(downtoup);

    }
    public void Beginner(View v){
        Intent ıntent=new Intent(this,BeginnerActivity.class);
        this.startActivity(ıntent);
    }
    public void Elementary(View v){
        Intent ıntent=new Intent(this,ElementaryActivity.class);
        this.startActivity(ıntent);
    }
    public void Intermediate1(View v){
        Intent ıntent=new Intent(this,IntermediateActivity.class);
        this.startActivity(ıntent);
    }
    public void Kelimelerim(View v){
        Intent ıntent=new Intent(this,FavoriteWords.class);
        this.startActivity(ıntent);
    }
}
