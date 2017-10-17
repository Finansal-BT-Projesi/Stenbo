package com.finansal.bt.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class BeginnerActivity extends AppCompatActivity {

    final List<String>basliklar=new ArrayList<String>();
    ListView listem;

    FirebaseDatabase db;
    DatabaseReference oku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);

        listem=(ListView)findViewById(R.id.listeBeginner);
        final CustomAdapter adapter=new CustomAdapter(this,basliklar);
        listem.setAdapter(adapter);

        db= FirebaseDatabase.getInstance();
        oku=db.getReference().child("beginner");

        oku.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot key:dataSnapshot.getChildren()){
                    basliklar.add(key.getKey().toString());

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       listem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent=new Intent(BeginnerActivity.this,StoryActivity.class);
            intent.putExtra("baslik", listem.getAdapter().getItem(position).toString());
               startActivity(intent);

               overridePendingTransition(R.anim.from_right,R.anim.left);
           }
       });

    }
}
