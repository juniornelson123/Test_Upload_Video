package com.example.junior.test_token_video;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaSyncEvent;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;
import java.util.Random;

/**
 * Created by junior on 13/07/16.
 */
public class PostAdapter extends RecyclerView.Adapter<PostViewHolder>{
    List<Post> list;
    LayoutInflater inflater;
    Context c;
    public PostAdapter(Context c, List<Post> l){
        list=l;
        inflater=LayoutInflater.from(c);
        this.c=c;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=inflater.inflate(R.layout.item_photo_fragment,parent,false);
        PostViewHolder pvh=new PostViewHolder(v,c);

        return pvh;
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position) {
        /*byte[] fotoArray;

        fotoArray=list.get(position).getMedia();
        if(fotoArray != null){
            if (fotoArray.length != 0) {
                holder.imagePost.setImageBitmap(BitmapFactory.decodeByteArray(fotoArray, 0, fotoArray.length));

            }
        }else{
            Random random = new Random();
            // get the next random int
            int randomInt = random.nextInt(10);
            if (randomInt %2 == 0) {
                holder.imagePost.setImageDrawable(c.getResources().getDrawable(R.drawable.fundo1));
            }else {
                holder.imagePost.setImageDrawable(c.getResources().getDrawable(R.drawable.fundo2));

            }
        }
*/
        if (list.get(position).getMedia() != null) {
            Uri uri = Uri.parse(VotaRestClient.BASE_URL + list.get(position).getMedia());

            holder.imagePost.setVideoURI(uri);
            final android.widget.MediaController mediaController = new android.widget.MediaController(c);
            mediaController.setAnchorView(holder.imagePost);
            holder.imagePost.setMediaController(mediaController);
        }

        holder.id.setText(list.get(position).getId());
        holder.titlePost.setText(list.get(position).getTitle());
        holder.descriptionPost.setText(list.get(position).getDescription());

    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
