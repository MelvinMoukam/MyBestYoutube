package com.example.mybestyoutube;

import static com.example.mybestyoutube.R.id.openYoutube;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybestyoutube.adapter.VideoAdapter;
import com.example.mybestyoutube.database.YoutubeDatabase;
import com.example.mybestyoutube.pojos.YoutubeVideo;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnVideoItemClickListener, VideoAdapter.OnVideoItemClickListener {

    private Context context;

    private RecyclerView rvVideo;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview_main);

        // Récupère le contexte de l'application
        context = getApplicationContext();



        // Récupère le RecyclerView
        rvVideo = findViewById(R.id.rvVideo);

        // Crée le LayoutManager pour gérer le RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        rvVideo.setHasFixedSize(true);
        rvVideo.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Récupère la liste des vidéos depuis la base de données
        List<YoutubeVideo> videos = YoutubeDatabase.getDb(context).youtubeDao().list();

        // Crée l'adaptateur en lui passant la liste
        VideoAdapter videoAdapter = new VideoAdapter(videos, this);
        rvVideo.setAdapter(videoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Crée le menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Teste l'id de l'item sélectionné
        if (item.getItemId() == R.id.AddVideoYoutube) {
            // Crée un Intent pour l'activité d'ajout de vidéo
            Intent intent = new Intent(context, AddYoutubeVideo.class);
            // Démarre l'activité
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onVideoItemClick(YoutubeVideo item) {
        // Gère le clic sur une vidéo
        // Ici, vous pouvez lancer une nouvelle activité pour afficher les détails de la vidéo

        Intent intent = new Intent(MainActivity.this, DetailVideo.class);
        intent.putExtra("youtubeVideo", item);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}


