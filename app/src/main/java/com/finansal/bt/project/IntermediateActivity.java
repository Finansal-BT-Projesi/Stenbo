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

public class IntermediateActivity extends AppCompatActivity {

    final List<String>resimler=new ArrayList<String>();
    public static boolean intermediate;
    final List<String> basliklar=new ArrayList<String>();
    ListView listem;
    ProgressBar pb;
     CustomAdapter adapter;
    FirebaseDatabase db;
    DatabaseReference oku;

    private String img_url[]={
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20Secret%20Talent.jpg?alt=media&token=3397fb05-e3a0-4881-8689-db02d1f827d2",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Act%20like%20the%20Others.jpg?alt=media&token=00df8e77-522c-4d31-bdd0-0ff04afb91d6",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Lost%20and%20Found.jpg?alt=media&token=fa945d48-2090-499b-b945-2b28957da634",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Love%20is%20in%20the%20Air.jpg?alt=media&token=0c617dae-fa53-4934-bd3e-7fdc9af9d928",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/The%20Best%20Practice%20Ever.jpg?alt=media&token=3ca5d908-c14a-4895-857c-2444e089ab85"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediate);
        this.setTitle("Intermediate");

        for(int i=0;i<img_url.length;i++){
            resimler.add(img_url[i].toString());
        }
        intermediate=true;
        ElementaryActivity.elementary=false;
        BeginnerActivity.beginner=false;

        listem=(ListView)findViewById(R.id.listeIntermediate);
        pb=(ProgressBar)findViewById(R.id.progressBar5);

        adapter=new CustomAdapter(this,basliklar,resimler);
        listem.setAdapter(adapter);

        db= FirebaseDatabase.getInstance();
        oku=db.getReference().child("intermediate");

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
                Intent intent=new Intent(IntermediateActivity.this,StoryActivity.class);
                intent.putExtra("baslik", listem.getAdapter().getItem(position).toString());
                startActivity(intent);

                overridePendingTransition(R.anim.from_right,R.anim.left);
            }
        });

    }
}

