package com.finansal.bt.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class BeginnerActivity extends AppCompatActivity {

    final List<String>resimler=new ArrayList<String>();
    final List<String>basliklar=new ArrayList<String>();
    ListView listem;
    ProgressBar pb;
    public static boolean beginner;
    CustomAdapter adapter;
    FirebaseDatabase db;
    DatabaseReference oku;
    private String img_url[]={
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20piece%20of%20Paper.jpg?alt=media&token=45c33ad1-4ca3-4244-b5ed-e3898105bdc6",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20Thin%20Man.jpg?alt=media&token=5d7b3cda-b7a5-415e-bebd-178537ead3e7",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Red%2C%20White%2C%20and%20Blue.png?alt=media&token=81f2a313-031c-46ca-a0e2-3e2813572b8c",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Try%20to%20Sleep.jpg?alt=media&token=2e9cf99f-77a2-43a1-9bdb-66ae30fe07a0",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Washing%20Her%20Hands.jpg?alt=media&token=837168ef-1bbb-43d4-8c70-1a88223eec10"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beginner);
        for(int i=0;i<img_url.length;i++){
            resimler.add(img_url[i].toString());
        }
        this.setTitle("Beginner");
        beginner=true;
        ElementaryActivity.elementary=false;
        IntermediateActivity.intermediate=false;

        listem=(ListView)findViewById(R.id.listeBeginner);
        pb=(ProgressBar)findViewById(R.id.progressBar3);

        adapter=new CustomAdapter(this,basliklar,resimler);
        listem.setAdapter(adapter);

        db= FirebaseDatabase.getInstance();
        oku=db.getReference().child("beginner");

        oku.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot key:dataSnapshot.getChildren()){
                    basliklar.add(key.getKey().toString());
                }
                pb.setVisibility(View.INVISIBLE);

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


           }
       });

    }
}
