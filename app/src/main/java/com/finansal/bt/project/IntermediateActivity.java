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
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20Haunted%20House%20by%20Virginia%20Woolf.jpg?alt=media&token=be47576e-82ea-46a7-8473-42fc34306bc0",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20Secret%20Talent.jpg?alt=media&token=3397fb05-e3a0-4881-8689-db02d1f827d2",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Act%20like%20the%20Others.jpg?alt=media&token=00df8e77-522c-4d31-bdd0-0ff04afb91d6",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Beauty.jpg?alt=media&token=f71d5f52-34ab-4ff0-ace1-d069c66884ee",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Clockwork.jpg?alt=media&token=2ac69c8c-6e00-4dc5-a130-2e9d4443ff3a",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Computers%20and%20Girls.jpg?alt=media&token=63951888-2b7b-481d-b76c-59a0504f7c40",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Decision.jpg?alt=media&token=785486a4-f376-488d-948c-c40bddddd0c7",
             "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Happiness.jpg?alt=media&token=78badfae-ebc1-4dd8-b7d8-e303719189ed",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Lost%20and%20Found.jpg?alt=media&token=fa945d48-2090-499b-b945-2b28957da634",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Love%20is%20in%20the%20Air.jpg?alt=media&token=0c617dae-fa53-4934-bd3e-7fdc9af9d928",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Red%20Balloons.jpg?alt=media&token=64046ac3-52c4-4c96-8d25-e66435c6e8f3",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Robot%20Eagle.jpg?alt=media&token=d4d4b842-ce78-4483-b02e-fd6fd7a797eb",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/The%20Best%20Practice%20Ever.jpg?alt=media&token=3ca5d908-c14a-4895-857c-2444e089ab85",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/The%20Big%20Chance.jpg?alt=media&token=614e4907-9847-440d-adf6-5ff56988bfa4",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/The%20Champion%20Duck.jpg?alt=media&token=9483dc2a-7c63-4386-bbc6-b4539202e6c9",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/The%20Little%20Whale%20And%20Sharks.jpg?alt=media&token=3b7c4a6c-212f-47af-afa3-2931bfd3b387",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/The%20Search%20For%20Lorna.jpg?alt=media&token=da1d5c3a-6090-42df-b615-7cbf0f84f9b3",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/The%20Wrong%20House.jpg?alt=media&token=764a71ca-799e-4c4d-b32b-215e76b4a035",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Will%20Of%20The%20Wind.jpg?alt=media&token=f9d6962a-448d-407f-b1b8-091ee835c980",
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

