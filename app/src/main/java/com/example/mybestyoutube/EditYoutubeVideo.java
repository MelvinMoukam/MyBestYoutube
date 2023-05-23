package com.example.mybestyoutube;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mybestyoutube.database.YoutubeDatabase;
import com.example.mybestyoutube.pojos.YoutubeVideo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditYoutubeVideo extends AppCompatActivity  {


    private EditText edtTitre;
    private EditText edtDescription;
    private EditText edtUrl;
    private Spinner categorySpinner;
    private Button btnEdit;
    private Button btnCancel;

    private YoutubeVideo youtubeVideo;

    private Context context;

    private class UpdateVideoTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            // Récupérer les nouvelles valeurs des champs
            String titre = edtTitre.getText().toString();
            String description = edtDescription.getText().toString();
            String url = edtUrl.getText().toString();
            String category = categorySpinner.getSelectedItem().toString();

            // Vérifier si les champs obligatoires sont renseignés
            if (titre.isEmpty() || description.isEmpty() || url.isEmpty()) {
                return false;
            }

            // Mettre à jour les champs de la vidéo dans la base de données
            youtubeVideo.setTitre(titre);
            youtubeVideo.setDescription(description);
            youtubeVideo.setUrl(url);
            youtubeVideo.setCategory(category);

            // Appeler la méthode d'update du Dao pour mettre à jour la vidéo
            YoutubeDatabase.getDb(context).youtubeDao().update(youtubeVideo);

            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                // Afficher un message de succès avec un Toast
                Toast.makeText(EditYoutubeVideo.this, "Vidéo modifiée avec succès", Toast.LENGTH_SHORT).show();

                // Revenir à la liste des vidéos
                Intent listIntent = new Intent(EditYoutubeVideo.this, MainActivity.class);
                startActivity(listIntent);

                // Terminer l'activité
                finish();
            } else {
                // Afficher un message d'erreur avec un Toast
                Toast.makeText(EditYoutubeVideo.this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_youtube_video);


        // affiche le back dans l'actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //recupere le context de l'application
        context = getApplicationContext();

        // Récupère l'objet YoutubeVideo de l'intent
        Intent intent = getIntent();
        youtubeVideo = (YoutubeVideo) intent.getSerializableExtra("youtubeVideo");

        //recupère les elements
        edtTitre = findViewById(R.id.edtTitre);
        edtDescription = findViewById(R.id.edtDescription);
        edtUrl = findViewById(R.id.edtUrl);
        categorySpinner = findViewById(R.id.spinner);
        btnEdit = findViewById(R.id.btnEdit);
        btnCancel = findViewById(R.id.btnCancel);

        // Initialisation d'un tableau de chaînes
        String[] categories = new String[]{
                "Sport",
                "Education",
                "Cuisine",
                "Comedy",
                "music"
        };


        //Convertis un tableau en liste
        List<String> videosList = new ArrayList<>
                (Arrays.asList(categories));


        //Initialisation d'un ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter
                = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                videosList
        );


        //Défini la ressource de vue déroulante
        spinnerArrayAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );


        // Enfin, les données lient l'objet spinner avec l'adaptateur
        categorySpinner.setAdapter(spinnerArrayAdapter);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Exécuter la tâche asynchrone pour la mise à jour de la vidéo
                new UpdateVideoTask().execute();
                }
            });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        // termine un activity
        finish();
        return true;
    }



}