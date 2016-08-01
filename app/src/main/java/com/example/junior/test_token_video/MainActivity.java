package com.example.junior.test_token_video;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity{
    VideoView vidView;
    private SurfaceHolder vidHolder;
    private MediaPlayer mediaPlayer;
    private SurfaceView vidSurface;

    List<Post> list=new ArrayList<>();
    RecyclerView recyclerNews;
    LinearLayoutManager llm;
   // String viAdress="https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
String viAdress="http://192.168.1.3:3000/uploads/test/video/6/video-2016-07-31T15_41_36_0000.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       /*  vidView = (VideoView)findViewById(R.id.video);
        Uri vidUri = Uri.parse(viAdress);

        vidView.setVideoURI(vidUri);
        vidView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vidView.start();
            }
        });
        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(vidView);
        vidView.setMediaController(vidControl);
*/
/*        vidSurface = (SurfaceView) findViewById(R.id.surface);
        vidHolder = vidSurface.getHolder();
        vidHolder.addCallback(this);
*/
        recyclerNews=(RecyclerView) findViewById(R.id.recycler_photo);

        llm=new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);


        recyclerNews.setLayoutManager(llm);


       PostAdapter postAdapter=new PostAdapter(this,list);
        recyclerNews.setAdapter(postAdapter);



        getLista();

    }
/*
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDisplay(vidHolder);
        try {
            mediaPlayer.setDataSource(viAdress);

            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

        mediaPlayer.start();
    }*/

    public void getLista(){
        VotaRestClient client=new VotaRestClient();
        client.get("/tests.json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray jsonArray=new JSONArray(new String(responseBody));

                    for (int i=0;i<jsonArray.length();i++){

                     JSONObject object=new JSONObject(jsonArray.getJSONObject(i).toString());
                        String obketp=object.getString("video_url");

                        Log.e("<<<<<<<<<<<>>>>>>>>>>>>>>>>",object.getString("video_url").toString());
                            Post p=new Post("teste","escras","video",object.getString("video_url"));

                                list.add(p);


                    }

                    PostAdapter adapter= (PostAdapter) recyclerNews.getAdapter();
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("<>>>>>>>>>>>>>>>>>>",error.toString());
            }
        });
    }
}
