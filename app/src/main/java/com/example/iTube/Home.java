package com.example.iTube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iTube.data.DatabaseHelperP;
import com.example.iTube.model.Playlist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Home extends AppCompatActivity {
    Button addToPlaylistBtn,playBtn,myPlaylistBtn;
    EditText url;
    DatabaseHelperP dbP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addToPlaylistBtn = findViewById(R.id.addToPlayListBtn);
        url = findViewById(R.id.url);
        playBtn = findViewById(R.id.playBtn);
        myPlaylistBtn = findViewById(R.id.myPlaylistBtn);
        dbP = new DatabaseHelperP(this);

        addToPlaylistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isYoutubeUrl(url.getText().toString().trim())){
                   long result =  dbP.insertVideo(new Playlist(url.getText().toString().trim(),getIntent().getIntExtra("user_id",0)));
                   if(result>0){
                       Toast.makeText(Home.this,"added successfully",Toast.LENGTH_SHORT).show();
                   }else
                   {
                       Toast.makeText(Home.this,"couldn't add to playlist",Toast.LENGTH_SHORT).show();
                   }
                }
                else{
                    Toast.makeText(Home.this,"please enter a valid url",Toast.LENGTH_SHORT).show();
                }
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isYoutubeUrl(url.getText().toString().trim())){

                    String id = getVideoId(url.getText().toString().trim());
                    if(!id.equals("error")){
                        Intent intent = new Intent(Home.this,PlayVideo.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(Home.this,"couldn't fetch id please enter a valid url",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(Home.this,"please enter a valid url",Toast.LENGTH_SHORT).show();
                }
            }
        });

        myPlaylistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent oldIntent = getIntent();
                    Intent intent = new Intent(Home.this,MyPlaylist.class);
                    intent.putExtra("user_id",oldIntent.getIntExtra("user_id",0));
                    startActivity(intent);
                }
                catch (Exception e){
                    Log.d("reached",e.getMessage());
                }

            }
        });

        url.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    public static boolean isYoutubeUrl(String youTubeURl)
    {
        boolean success;
        String pattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
        if (!youTubeURl.isEmpty() && youTubeURl.matches(pattern))
        {
            success = true;
        }
        else
        {
            success = false;
        }
        return success;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getVideoId(@NonNull String videoUrl) {
        String regex = "http(?:s)?:\\/\\/(?:m.)?(?:www\\.)?youtu(?:\\.be\\/|be\\.com\\/(?:watch\\?(?:feature=youtu.be\\&)?v=|v\\/|embed\\/|user\\/(?:[\\w#]+\\/)+))([^&#?\\n]+)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(videoUrl);

        if(matcher.find()){
            return matcher.group(1);
        } else {
            return "error";
        }
    }
}