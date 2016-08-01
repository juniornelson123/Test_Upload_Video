package com.example.junior.test_token_video;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by junior on 13/07/16.
 */
public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public VideoView imagePost;
    public TextView titlePost,descriptionPost;
    public TextView id;
    Context c;
    VotaRestClient client=new VotaRestClient();
    public PostViewHolder(View itemView, Context c) {

        super(itemView);
        this.c=c;
        id=(TextView)itemView.findViewById(R.id.id_posts);
        imagePost= (VideoView) itemView.findViewById(R.id.video);
        titlePost=(TextView) itemView.findViewById(R.id.title_post);
        descriptionPost=(TextView) itemView.findViewById(R.id.description_post);
        itemView.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        //Intent intent=new Intent(c, DetailsPostActivity.class);
        //intent.putExtra("id_post",id.getText().toString());
       // c.startActivity(new Intent(c,DetalhesVideosActivity.class));

    }
    public void delete(){
        client.delete("/posts/" + id, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(c, "Deletado", Toast.LENGTH_SHORT).show();
          //      c.startActivity(new Intent(c,PostActivity.class));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("erro",error.toString());
            }
        });
    }
}
