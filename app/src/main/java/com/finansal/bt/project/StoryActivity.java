package com.finansal.bt.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);


        Bundle extras=getIntent().getExtras();
        String toolbarBaslik=extras.getString("baslik");

        this.setTitle(toolbarBaslik);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.from_left,R.anim.right);
    }
}
