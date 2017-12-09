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
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20Boring%20Day.jpg?alt=media&token=2826665e-3e3b-4d90-a5c4-b7e412378483",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20Broken%20Chair.jpg?alt=media&token=201d44f0-b3f0-456c-9440-fe4841b7c726",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20Lazy%20Kid.jpg?alt=media&token=b4c57de8-5420-450a-92a3-9165c0d632c6",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20piece%20of%20Paper.jpg?alt=media&token=45c33ad1-4ca3-4244-b5ed-e3898105bdc6",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/A%20Thin%20Man.jpg?alt=media&token=5d7b3cda-b7a5-415e-bebd-178537ead3e7",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Arts%20and%20Crafts.jpg?alt=media&token=a323a7d9-d3f8-45e5-a77b-02887044ffce",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Birthday%20Celebration.jpg?alt=media&token=c0332d6f-e973-40c9-b720-456ba0366896",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Buying%20Shoes%20Online.jpg?alt=media&token=24a96495-9a48-4159-af17-42ecefb51fd0",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Claire%20Reads%20a%20Book.jpg?alt=media&token=7449abca-2598-4ea0-972b-00e7a3e43ac3",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Dirty%20Shoes.jpg?alt=media&token=255504b2-bbd2-4a65-a12a-565429a2877a",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Drawing%20Animals.jpg?alt=media&token=cb44627b-f2ea-4019-9112-09022e4c6098",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/First%20Day%20of%20School.jpg?alt=media&token=db48d262-394f-4538-a3ce-b8ab39948424",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Flowers%20for%20Mom.jpg?alt=media&token=5a90a232-2f33-40b1-bc1d-60665e0be98f",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Graduating.jpg?alt=media&token=97f1fe8c-ffbe-49cb-a8de-179f687f2a1f",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Help%20From%20Coffee.jpg?alt=media&token=87cc7d91-f5c1-40b2-8cc6-2b7b1c18b7e1",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Luke's%20New%20School.jpg?alt=media&token=84ad888b-4288-40bb-a290-ed9f659ad15f",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Making%20a%20Volcano.jpg?alt=media&token=1f1f8bb9-9dbd-4520-bd2d-fd7578935981",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Meeting%20His%20Cousins.jpg?alt=media&token=4b871ca8-e49b-41b1-b184-e88be6963542",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Moving%20to%20New%20York.jpg?alt=media&token=bdb63eec-af4a-467a-996d-510e1cce0ac6",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Pat's%20Bunny.jpg?alt=media&token=d7f1aee0-233c-4526-bdf4-5624b8b38965",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Red%2C%20White%2C%20and%20Blue.jpg?alt=media&token=64924456-7275-4278-94bc-c2ce40f3573d",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Sharing%20is%20Caring.jpg?alt=media&token=57255844-a31a-44d2-b23c-ebd2f2202dc3",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/She%20Brings%20Lunch%20from%20Home.jpg?alt=media&token=a317b311-23a3-485f-b3ff-a86e1b26a43a",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/The%20Final%20Exam.jpg?alt=media&token=ee7f798e-5ae4-4e9f-b4c4-b07874fecb32",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/The%20Petting%20Zoo.jpg?alt=media&token=2de5936e-2df2-43ad-af1c-09e929983c91",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/The%20Pink%20One%20is%20Popular.jpg?alt=media&token=2f269d19-33e7-48c4-8aef-b9156fc08b16",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/The%20Quiet%20Boy.jpg?alt=media&token=5d316290-d021-4740-a63b-57f70740942d",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/The%20Quiet%20Game.jpg?alt=media&token=0961e7b3-394d-41e5-96ec-329ebb92196e",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/The%20School%20Bully.jpg?alt=media&token=ac1bf959-f140-452a-8edf-bb42002685e7",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Try%20to%20Sleep.jpg?alt=media&token=2e9cf99f-77a2-43a1-9bdb-66ae30fe07a0",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Using%20Her%20Credit%20Card.jpg?alt=media&token=b2d167e9-0417-4b3a-9bb4-c63ce97544da",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Washing%20Dishes.jpg?alt=media&token=096e7790-1737-4a5c-8acf-eca47044243d",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Washing%20Her%20Hands.jpg?alt=media&token=837168ef-1bbb-43d4-8c70-1a88223eec10",
            "https://firebasestorage.googleapis.com/v0/b/stenbo-78fc7.appspot.com/o/Water%20Slide.jpg?alt=media&token=ab04a3dd-60dc-4632-baae-02d0736126c2"


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
