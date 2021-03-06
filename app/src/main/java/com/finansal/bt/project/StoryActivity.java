package com.finansal.bt.project;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
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
import java.util.concurrent.TimeUnit;


public class StoryActivity extends AppCompatActivity {

    private Menu menu;
    private FirebaseAuth mAuth;
    private FirebaseStorage fStorage;
    DatabaseReference oku;
    FirebaseDatabase db;
    TextView icerik;
    String languagePair = "en-tr";
    EditText edt;
    Context context=this;
    String resultString,sozcuk;
    Typeface tf1,tf2;
    ImageView img;
    private MediaPlayer mediaPlayer;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private Button play,twitter,whatsapp;
    String myUri;
    int start=0,lenght=0;
    public static int oneTimeOnly = 0;

    ToggleButton toggleBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        mediaPlayer=new MediaPlayer();


        toggleBtn = (ToggleButton) findViewById(R.id.button3);

        toggleBtn.setChecked(true);


        toggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean on = ((ToggleButton) v).isChecked();
                if (on) {
                    // ON durumunda yapılacaklar
                    toggleBtn.setBackgroundResource(R.drawable.play);
                        mediaPlayer.pause();
                        lenght=mediaPlayer.getCurrentPosition();
                }
                else {
                    // OFF durumunda yapılacaklar
                    toggleBtn.setBackgroundResource(R.drawable.stop);
                    if(start==0){
                        try {
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.setDataSource(myUri);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                            start=1;
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    finalTime = mediaPlayer.getDuration();
                    if(start==1 && lenght!=finalTime){
                        mediaPlayer.seekTo(lenght);
                        mediaPlayer.start();
                    }
                    if(lenght==finalTime){
                        mediaPlayer.start();
                    }
                    startTime = mediaPlayer.getCurrentPosition();
                    if(oneTimeOnly == 0){
                        seekbar.setMax((int) finalTime);
                        oneTimeOnly = 1;
                    }
                    seekbar.setProgress((int)startTime);
                    myHandler.postDelayed(UpdateSongTime,100);
                }
            }
        });

        Bundle extras=getIntent().getExtras();
        String toolbarBaslik=extras.getString("baslik");
        img=(ImageView)findViewById(R.id.imageView2);
        seekbar = (SeekBar)findViewById(R.id.seekBar1);

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
        StorageReference storageRef1=storage.getReferenceFromUrl("gs://stenbo-78fc7.appspot.com/"+toolbarBaslik+".mp3");
        storageRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                myUri=uri.toString();
            }
        });
        seekbar.setClickable(false);

    }
    @Override
    public void onPause(){
        super.onPause();
        mediaPlayer.stop();
    }

    @Override
    public void onStop(){
        super.onStop();
        mediaPlayer.stop();
    }

    //paylaşma iconu toolbara eklenme kısmı başlangıç/////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);
        this.menu=menu;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.whatsapp:
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT, icerik.getText().toString());
                intent1.setType("text/plain");
                intent1.setPackage("com.whatsapp");
                startActivity(intent1);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /////paylaşma iconu toolbara eklenme kısmı son//////////////

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            //Muziğin hangi sürede oldugunu gostermek icin, seekbar kullarak gosteriyoruz...
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };
    public void onClickforward(View view){
        int temp = (int)startTime;
        if((temp+forwardTime)<=finalTime){
            startTime = startTime + forwardTime;
            mediaPlayer.seekTo((int) startTime);
        }
        else{

            //Muzigin çalıs suresi son 5 saniye geldiginde ,ileri tusuna basarsanız kosulu icin uyarı yazdıyoruz

            Toast.makeText(getApplicationContext(), "You can not run it forward in the last 5 seconds ",
                    Toast.LENGTH_SHORT).show();
        }

    }
    //Geri butonuna bastgınızda,muzigin çalış süresini 5 saniye azaltarak muzigi  geriye alır
    public void onClickrewind(View view){
        int temp = (int)startTime;
        if((temp-backwardTime)>0){
            startTime = startTime - backwardTime;
            mediaPlayer.seekTo((int) startTime);
        }
        else{
            //Muzigin çalıs suresi ilk 5 saniye gelmeden ,geri tusuna basarsanız kosulu icin uyarı yazdıyoruz
            Toast.makeText(getApplicationContext(),"You can not rewind the sound in the first 5 seconds",
                    Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.from_left,R.anim.right);
    }
    public void searchOnclick(View v){
        edt=(EditText)findViewById(R.id.editText);
        sozcuk=edt.getText().toString().trim();
        if(sozcuk.equals("")){
            Toast.makeText(this,"You have not written any words",Toast.LENGTH_LONG).show();
        }
        else{
            Translate(edt.getText().toString().trim(),languagePair);
        }
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
            dialog.setTitle("Meaning of the word");

            // set the custom dialog components - text, image and button
            TextView text = (TextView) dialog.findViewById(R.id.word);
            TextView text1 = (TextView) dialog.findViewById(R.id.main);
            if(resultString.equals(sozcuk)){
                text1.setText("You have written a wrong word. Try again.");
            }
            else{
                text.setText(edt.getText().toString()+" :");
                text1.setText(resultString);
            }
            Button dialogButton = (Button) dialog.findViewById(R.id.okey);
            Button dialogButton1=(Button)dialog.findViewById(R.id.fav);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialogButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Database db = new Database(StoryActivity.this);
                    db.VeriEkle(sozcuk, resultString);
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
