package com.finansal.bt.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
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
}
