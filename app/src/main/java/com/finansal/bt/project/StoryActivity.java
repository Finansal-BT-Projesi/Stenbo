package com.finansal.bt.project;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StoryActivity extends AppCompatActivity {

    DatabaseReference oku;
    FirebaseDatabase db;
    TextView icerik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Bundle extras=getIntent().getExtras();
        String toolbarBaslik=extras.getString("baslik");
        icerik=(TextView)findViewById(R.id.icerik);

        db=FirebaseDatabase.getInstance();


        this.setTitle(toolbarBaslik);

        if(BeginnerActivity.beginner==true){
            oku=db.getReference().child("beginner").child(toolbarBaslik);
        }
        else if(ElementaryActivity.elementary==true){
            oku=db.getReference().child("elementary").child(toolbarBaslik);
        }
        else if(IntermediateActivity.intermediate==true){
            oku=db.getReference().child("intermediate").child(toolbarBaslik);
        }

        oku.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                icerik.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.from_left,R.anim.right);
    }
}
