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

public class ElementaryActivity extends AppCompatActivity {

    public static boolean elementary;
    final List<String> basliklar=new ArrayList<String>();
    final List<String>resimler=new ArrayList<String>();
    ListView listem;

    ProgressBar pb;
    FirebaseDatabase db;
    DatabaseReference oku;
    private final String image_url[]={
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20Surpise%20from%20Australia.jpg?alt=media&token=43d6e986-4ece-41a2-8be3-8ee91a546b18",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/April's%20Month.jpg?alt=media&token=ba39e5ae-d5fe-4945-aefb-60ff10cad95a",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Kevin's%20Car.jpg?alt=media&token=c3f28319-df4b-43ad-9120-bfc4ce8c3c3f",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Language%20Confusion.JPG?alt=media&token=0adabad6-4441-423d-9334-a008c57c9215",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Sean%20and%20the%20Birthday%20Cake.jpg?alt=media&token=b8871307-0fde-44d3-ad59-59d756c8fe4a"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elementary);
        this.setTitle("Elementary");

        for(int i=0;i<image_url.length;i++){
            resimler.add(image_url[i].toString());
        }
        elementary=true;
        IntermediateActivity.intermediate=false;
        BeginnerActivity.beginner=false;

        listem=(ListView)findViewById(R.id.listeElementary);
        pb=(ProgressBar)findViewById(R.id.progressBar4);
        final CustomAdapter adapter=new CustomAdapter(this,basliklar,resimler);
        listem.setAdapter(adapter);

        db= FirebaseDatabase.getInstance();
        oku=db.getReference().child("elementary");

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
                Intent intent=new Intent(ElementaryActivity.this,StoryActivity.class);
                intent.putExtra("baslik", listem.getAdapter().getItem(position).toString());
                startActivity(intent);

                overridePendingTransition(R.anim.from_right,R.anim.left);
            }
        });

    }
    }

