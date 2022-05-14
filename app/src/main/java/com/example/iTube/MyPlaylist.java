package com.example.iTube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.iTube.data.DatabaseHelper;
import com.example.iTube.data.DatabaseHelperP;
import com.example.iTube.model.Playlist;

import java.util.ArrayList;
import java.util.List;

public class MyPlaylist extends AppCompatActivity {
    ListView playList;
    ArrayList<String> videoList;
    ArrayAdapter<String> adapter;
    DatabaseHelperP dbP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_playlist);

        try {
            Intent oldIntent = getIntent();

            int user_id = oldIntent.getIntExtra("user_id",0);
            dbP = new DatabaseHelperP(this);
            playList = findViewById(R.id.playList);
            videoList = new ArrayList<>();

            List<Playlist> list = dbP.fetchAllVideo(user_id);
            for (Playlist video :list)
            {
                videoList.add(video.getVideo_uri());
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, videoList);
            playList.setAdapter(adapter);
        }catch (Exception e){
            Log.d("reached",e.getMessage());
        }

    }
}