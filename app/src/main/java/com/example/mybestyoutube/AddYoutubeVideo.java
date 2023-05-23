package com.example.mybestyoutube;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

public class AddYoutubeVideo extends AppCompatActivity {

    private EditText edtTitre;
    private EditText edtDescription;
    private EditText edtUrl;
    private Spinner categorySpinner;
    private Button btnAdd;
    private Button btnCancel;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_youtube_video);

        // affiche le back dans l'actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //recupere le context de l'application
        context = getApplicationContext();

        //recupère les elements
        edtTitre = findViewById(R.id.edtTitre);
        edtDescription= findViewById(R.id.edtDescription);
        edtUrl= findViewById(R.id.edtUrl);
        categorySpinner= findViewById(R.id.spinner);
        btnAdd = findViewById(R.id.btnAdd);
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
                (Arrays.asList(categories ));


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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Récupére les valeurs des champs
                String titre =  edtTitre.getText().toString();
                String description =edtDescription.getText().toString();
                String url = edtUrl.getText().toString();
                String category = categorySpinner.getSelectedItem().toString();

                // Vérifie si les champs obligatoires sont renseignés
                if (titre.isEmpty() || description.isEmpty() || url.isEmpty()) {
                    Toast toast = Toast.makeText(AddYoutubeVideo.this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    // Tous les champs sont renseignés, ajout de la vidéo dans la base de données

                    YoutubeVideo video = new YoutubeVideo();
                    video.setTitre(titre);
                    video.setDescription(description);
                    video.setUrl(url);
                    video.setCategory(category);

                    //la logique pour traiter l'ajout de la video
                    YoutubeDatabase.getDb(context).youtubeDao().add(video);

                    // Affiche un message de succès avec un Toast
                    Toast toast=Toast.makeText(AddYoutubeVideo.this, "Vidéo ajoutée avec succès", Toast.LENGTH_SHORT);
                    toast.show();

                    // Revenons à la page principale
                    finish();

                    // Créer l'intent avec l'activité DetailVideo
                    Intent detailIntent = new Intent(AddYoutubeVideo.this, MainActivity.class);

                    // Ajout de l'objet youtubeVideo à l'intent en tant qu'extra
                    detailIntent.putExtra("youtubeVideo", video);

                    // Démarre l'activité DetailVideo avec l'intent
                    startActivity(detailIntent);
                }
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