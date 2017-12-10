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
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20Bus%20Accident.jpg?alt=media&token=8b2a56d3-3596-4a2c-8c74-5511c6a49910",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20Clean%20Car.jpg?alt=media&token=03c6827d-83a4-4fef-9125-0bdde800640d",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20Good%20Meal.jpg?alt=media&token=2330b82a-118c-4db6-a425-7a7ae86e154f",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20Headache.jpg?alt=media&token=a139a3da-99ce-4864-b5cf-067fae39f22d",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20Surpise%20from%20Australia.jpg?alt=media&token=43d6e986-4ece-41a2-8be3-8ee91a546b18",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20Wedding.jpeg?alt=media&token=c0e7ed40-dded-40ea-8c48-37f710f67efc",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/April's%20Month.jpg?alt=media&token=ba39e5ae-d5fe-4945-aefb-60ff10cad95a",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Boys%20Will%20Be%20Boys.jpg?alt=media&token=bcc1acc3-eb60-4792-8e48-885db72b706b",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Fix%20the%20Car.jpg?alt=media&token=a13c44eb-b58d-4f7e-b4fb-43410b6db38c",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Homeless%20People.jpg?alt=media&token=c4548e51-300d-4d53-af19-8adbceb2c783",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Kevin's%20Car.jpg?alt=media&token=c3f28319-df4b-43ad-9120-bfc4ce8c3c3f",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Language%20Confusion.JPG?alt=media&token=0adabad6-4441-423d-9334-a008c57c9215",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Life%20Will%20Be%20Better.jpg?alt=media&token=f65d164e-3564-4d31-9d2c-d7cdc61fd044",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Make%20a%20Sandwich.jpg?alt=media&token=1541a8ce-a267-4e65-b9da-0207fe57ca55",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/My%20Best%20Friend.jpg?alt=media&token=9b15f912-8314-49ec-9b56-eb80e226ec63",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/New%20Kittens.jpg?alt=media&token=a032fb18-f4e4-4501-a32e-51bf47259319",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/No%20Friends%20for%20Me.jpg?alt=media&token=9e25895e-390a-4f9e-b427-721277835a12",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Popcorn.jpg?alt=media&token=07415b81-33d7-417b-997a-4a1076f56b44",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Sean%20and%20the%20Birthday%20Cake.jpg?alt=media&token=b8871307-0fde-44d3-ad59-59d756c8fe4a",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/She%20Plays%20Golf.jpg?alt=media&token=149edef4-a9c0-42ca-be1d-bf5c06cbc22e",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/She%20Wants%20to%20Ride.jpg?alt=media&token=a8edb20e-7ed1-4e6e-b90f-54bdc646c1a5",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Squares%20and%20Circles.jpg?alt=media&token=839b7507-92e5-43de-ad4b-01788a1f86ab"
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

