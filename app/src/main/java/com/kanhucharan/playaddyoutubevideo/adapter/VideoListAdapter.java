package com.kanhucharan.playaddyoutubevideo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.kanhucharan.playaddyoutubevideo.R;
import com.kanhucharan.playaddyoutubevideo.database.DatabaseHelper;
import com.kanhucharan.playaddyoutubevideo.model.VideoListModel;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;

import java.util.ArrayList;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.viewHolder> {

    Context context;
    Activity activity;
    ArrayList<VideoListModel> arrayList;
    DatabaseHelper database_helper;

    public VideoListAdapter(Context context, Activity activity, ArrayList<VideoListModel> arrayList) {
        this.context = context;
        this.activity  = activity ;
        this.arrayList = arrayList;
    }

    @Override
    public VideoListAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.videos_list, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoListAdapter.viewHolder holder, final int position) {
        database_helper = new DatabaseHelper(context);
        holder.playButton.setOnClickListener(view -> {
            holder.playButton.setVisibility(View.GONE);
            holder.youTubePlayerView.initialize(initializedYouTubePlayer -> initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady() {
                    initializedYouTubePlayer.loadVideo(arrayList.get(position).getVideourl(), 0);
                }
            }), true);
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                //deleting note
                database_helper.delete(arrayList.get(position).getID());
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView delete,playButton;
        YouTubePlayerView youTubePlayerView;
        public viewHolder(View itemView) {
            super(itemView);
            youTubePlayerView=itemView.findViewById(R.id.youtube_view);
            delete =  itemView.findViewById(R.id.delete);
            playButton =  itemView.findViewById(R.id.playButton);
        }
    }
}