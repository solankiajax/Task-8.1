package com.example.iTube.model;

public class Playlist {
    private  String video_uri;
    private Integer user_id;
    private int video_id;

    public Playlist() {
    }

    public Playlist(String video_uri, int user_id) {
        this.video_uri = video_uri;
        this.user_id = user_id;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getVideo_uri() {
        return video_uri;
    }

    public void setVideo_uri(String video_uri) {
        this.video_uri = video_uri;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
