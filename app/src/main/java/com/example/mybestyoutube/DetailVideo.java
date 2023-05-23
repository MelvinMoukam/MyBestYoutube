package com.example.mybestyoutube;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybestyoutube.dao.YoutubeDao;
import com.example.mybestyoutube.database.YoutubeDatabase;
import com.example.mybestyoutube.pojos.YoutubeVideo;

public class DetailVideo extends AppCompatActivity {

    private TextView tv_titre;

    private TextView dv_description;

    private TextView uv_url;


    private boolean isFavorite = false;
    private Context context;

    private TextView cv_categorie;

    private Button openYoutube;

    private Button bntAddFavorites;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_video);

        // affiche le back dans l'actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Récupére l'objet YoutubeVideo de l'intent
        Intent intent = getIntent();
        YoutubeVideo youtubeVideo = (YoutubeVideo) intent.getSerializableExtra("youtubeVideo");

        //recupere le context de l'application
        context = getApplicationContext();

        // Vérifiez si on obtient le bon objet YoutubeVideo
        Log.d("DetailVideo", "Received video: " + youtubeVideo.toString());

        // Recupère les elements
        tv_titre=findViewById(R.id.tv_titre);
        dv_description=findViewById(R.id.dv_description);
        uv_url=findViewById(R.id.uv_url);
        cv_categorie=findViewById(R.id.cv_categorie);
        openYoutube = findViewById(R.id.openYoutube);
        bntAddFavorites=findViewById(R.id.btnAddToFavorites);


        tv_titre.setText(youtubeVideo.getTitre());
        dv_description.setText(youtubeVideo.getDescription());
        uv_url.setText(youtubeVideo.getUrl());
        cv_categorie.setText(youtubeVideo.getCategory());

        //evènement sur le bouton permettant de voir la video
        openYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupère l'URL de la vidéo
                String videoUrl = uv_url.getText().toString();
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(videoUrl));
                try {
                    DetailVideo.this.startActivity(webIntent);
                } catch (ActivityNotFoundException ex) {
                }
            }
        });


        bntAddFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFavorite) {
                    // Retire la vidéo des favoris
                    isFavorite = false;
                    bntAddFavorites.setText("Ajouter aux favoris");
                    Toast.makeText(DetailVideo.this, "Vidéo retirée des favoris", Toast.LENGTH_SHORT).show();
                } else {
                    // Ajoute la vidéo aux favoris
                    isFavorite = true;
                    bntAddFavorites.setText("Retirer des favoris");
                    Toast.makeText(DetailVideo.this, "Vidéo ajoutée aux favoris", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
// Creation du menu dans detail
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete_update, menu);

        MenuItem deleteItem = menu.findItem(R.id.action_delete);
        deleteItem.setIcon(R.drawable.ic_delete_foreground);

        MenuItem editItem = menu.findItem(R.id.action_edit);
        editItem.setIcon(R.drawable.ic_edit_foreground);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        YoutubeVideo youtubeVideo = null;
        if (id == R.id.action_delete) {


            // Récupére l'objet YoutubeVideo de l'intent
            Intent intent = getIntent();
            youtubeVideo = (YoutubeVideo) intent.getSerializableExtra("youtubeVideo");

            // Supprime la vidéo en appelant la méthode delete du DAO
            YoutubeDatabase.getDb(context).youtubeDao().delete(youtubeVideo);

            // Affiche un message de confirmation
            Toast.makeText(this, "Vidéo supprimée", Toast.LENGTH_SHORT).show();

            // Termine l'activité et retourner à l'écran d'accueil
            finish();
            return true;

        } else if (id == R.id.action_edit) {

            // Récupère l'objet YoutubeVideo de l'intent
            Intent intent = getIntent();
            youtubeVideo = (YoutubeVideo) intent.getSerializableExtra("youtubeVideo");

            // Lancer l'activité d'édition pour modifier la vidéo
            Intent editIntent = new Intent(DetailVideo.this, EditYoutubeVideo.class);
            editIntent.putExtra("youtubeVideo", youtubeVideo);
            startActivity(editIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        // termine un activity
        finish();
        return true;
    }
}