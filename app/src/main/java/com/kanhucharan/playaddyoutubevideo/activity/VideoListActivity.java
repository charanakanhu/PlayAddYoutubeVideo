package com.kanhucharan.playaddyoutubevideo.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kanhucharan.playaddyoutubevideo.model.VideoListModel;
import com.kanhucharan.playaddyoutubevideo.adapter.VideoListAdapter;
import com.kanhucharan.playaddyoutubevideo.R;
import com.kanhucharan.playaddyoutubevideo.database.DatabaseHelper;

import java.util.ArrayList;

public class VideoListActivity extends AppCompatActivity {

    ArrayList<VideoListModel> arrayList;
    RecyclerView recyclerView;
    Button actionButton;
    DatabaseHelper database_helper;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        actionButton =  findViewById(R.id.add);
        database_helper = new DatabaseHelper(this);
        displayNotes();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        if (database_helper.getVideo().isEmpty()){
            database_helper.addVideos("EfxU3M47jTQ");
            database_helper.addVideos("_qyw6LC5pnE");
        }


    }

    //display video list
    public void displayNotes() {
        arrayList = new ArrayList<>(database_helper.getVideo());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        VideoListAdapter adapter = new VideoListAdapter(getApplicationContext(), this, arrayList);
        recyclerView.setAdapter(adapter);
    }

    //display dialog
    public void showDialog() {
        final EditText video_url_ed;
        final ImageView close_img;
       final Button submit;
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        dialog.setContentView(R.layout.dialog);
        params.copyFrom(dialog.getWindow().getAttributes());
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        video_url_ed =  dialog.findViewById(R.id.video_url_ed);
        submit = dialog.findViewById(R.id.submit);
        close_img = dialog.findViewById(R.id.close_img);

        submit.setOnClickListener(new View.OnClickListener() {;
            @Override
            public void onClick(View v) {
                    if (video_url_ed.getText().toString().isEmpty()) {
                        video_url_ed.setError("Please Enter videourl");
                    } else {
                        database_helper.addVideos(video_url_ed.getText().toString());
                        dialog.cancel();
                        displayNotes();
                    }
            }
        });
        close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

}