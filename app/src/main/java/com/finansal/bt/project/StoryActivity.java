package com.finansal.bt.project;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class StoryActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseStorage fStorage;
    DatabaseReference oku;
    FirebaseDatabase db;
    TextView icerik;
    String languagePair = "en-tr";
    EditText edt;
    Context context=this;
    String resultString;
    Typeface tf1,tf2;
    ImageView img;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Bundle extras=getIntent().getExtras();
        String toolbarBaslik=extras.getString("baslik");
        img=(ImageView)findViewById(R.id.imageView2);

        icerik=(TextView)findViewById(R.id.icerik);
        tf1=Typeface.createFromAsset(getAssets(), "fonts/gfs.ttf");
        icerik.setTypeface(tf1);

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


       FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageRef=storage.getReferenceFromUrl("gs://stenbo-78fc7.appspot.com/").child(toolbarBaslik+".jpg");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
               Glide.with(StoryActivity.this).load(uri.toString()).into(img);
            }
        });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.from_left,R.anim.right);
    }
    public void searchOnclick(View v){
        edt=(EditText)findViewById(R.id.editText);
        Translate(edt.getText().toString().trim(),languagePair);
    }
    void Translate(String textToBeTranslated,String languagePair){
        new TranslatorBackgroundTask(context).execute(textToBeTranslated,languagePair);
    }
    public class TranslatorBackgroundTask extends AsyncTask<String, Void, String> {
        Context ctx;
        TranslatorBackgroundTask(Context ctx){
            this.ctx = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String textToBeTranslated = params[0];
            String languagePair = params[1];
            String jsonString;

            try {
                String yandexKey = "trnsl.1.1.20171017T070737Z.bbc65d2e5928066a.5800015f153c79e58af84f6e0588fedd5019d57f";
                String yandexUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + yandexKey
                        + "&text=" + textToBeTranslated + "&lang=" + languagePair;
                URL yandexTranslateURL = new URL(yandexUrl);
                HttpURLConnection httpJsonConnection = (HttpURLConnection) yandexTranslateURL.openConnection();
                InputStream inputStream = httpJsonConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder jsonStringBuilder = new StringBuilder();
                while ((jsonString = bufferedReader.readLine()) != null) {
                    jsonStringBuilder.append(jsonString + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpJsonConnection.disconnect();
                resultString = jsonStringBuilder.toString().trim();
                resultString = resultString.substring(resultString.indexOf('[')+1);
                resultString = resultString.substring(0,resultString.indexOf("]"));
                resultString = resultString.substring(resultString.indexOf("\"")+1);
                resultString = resultString.substring(0,resultString.indexOf("\""));
                return jsonStringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom_alertdialog);
            dialog.setTitle("Kelime Anlamı");

            // set the custom dialog components - text, image and button
            TextView text = (TextView) dialog.findViewById(R.id.word);
            text.setText(edt.getText().toString()+" :");
            TextView text1 = (TextView) dialog.findViewById(R.id.main);
            text1.setText(resultString);

            Button dialogButton = (Button) dialog.findViewById(R.id.okey);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}
