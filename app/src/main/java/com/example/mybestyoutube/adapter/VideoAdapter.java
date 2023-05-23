package com.example.mybestyoutube.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybestyoutube.MainActivity;
import com.example.mybestyoutube.R;
import com.example.mybestyoutube.pojos.YoutubeVideo;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /*définis les types de vue des éléments de la liste.
    VIEW_TYPE_VIDEO représente la vue d'une vidéo non détaillée, tandis que
     VIEW_TYPE_DETAILS représente la vue des détails d'une vidéo.*/
    private static final int VIEW_TYPE_VIDEO = 0;
    private static final int VIEW_TYPE_DETAILS = 1;

    private List<YoutubeVideo> videoItems;
    private OnVideoItemClickListener listener;

    //Constructeur
    public VideoAdapter(List<YoutubeVideo> videoItems, OnVideoItemClickListener listener) {
        this.videoItems = videoItems;
        this.listener = listener;
    }


    //Determine le type de vue d'un element à une position donnée

    @Override
    public int getItemViewType(int position) {
        YoutubeVideo item = videoItems.get(position);
        return item.isExpanded() ? VIEW_TYPE_DETAILS : VIEW_TYPE_VIDEO;
    }

    //cette methode est appelée lors de la création d'une nouvelle vue

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_VIDEO) {
            View videoView = inflater.inflate(R.layout.video_item, parent, false);
            return new VideoViewHolder(videoView);
        } else {
            View detailsView = inflater.inflate(R.layout.activity_detail_video, parent, false);
            return new DetailsViewHolder(detailsView);
        }
    }

    //Lie les données d'un élément à sa vue

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        YoutubeVideo item = videoItems.get(position);

        if (holder instanceof VideoViewHolder) {
            VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
            videoViewHolder.bind(item);
        } else if (holder instanceof DetailsViewHolder) {
            DetailsViewHolder detailsViewHolder = (DetailsViewHolder) holder;
            detailsViewHolder.bind(item);
        }
    }
    //Retourne le nombre total de videos la liste
    @Override
    public int getItemCount() {
        return videoItems.size();
    }

    //gère les clics sur les éléments de la liste
    public interface OnVideoItemClickListener {
        void onVideoItemClick(YoutubeVideo item);
    }

    // représente la vue d'un élément de type vidéo.
    private class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView videoTitle;
        private TextView videoDescription;

        public VideoViewHolder(View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.edtTitre);
            videoDescription = itemView.findViewById(R.id.edtDescription);
            itemView.setOnClickListener(this);
        }
        // associes les données de la vidéo à la vue correspondante.
        public void bind(YoutubeVideo item) {
            videoTitle.setText(item.getTitre());
            videoDescription.setText(item.getDescription());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                YoutubeVideo item = videoItems.get(position);
                item.setExpanded(true);
                notifyItemChanged(position);
                listener.onVideoItemClick(item);
            }
        }
    }
        //représentes la vue d'un élément de type détails.
    private class DetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView videoDescription;
        private TextView videoUrl;
        private TextView videoCategory;
        private TextView videoTitre;

        public DetailsViewHolder(View itemView) {
            super(itemView);
            videoTitre = itemView.findViewById(R.id.tv_titre);
            videoDescription = itemView.findViewById(R.id.dv_description);
            videoUrl = itemView.findViewById(R.id.uv_url);
            videoCategory = itemView.findViewById(R.id.cv_categorie);
        }

        //associes les données de la vidéo à la vue correspondante.
        public void bind(YoutubeVideo item) {
            videoTitre.setText(item.getTitre());
            videoDescription.setText(item.getDescription());
            videoUrl.setText(item.getUrl());
            videoCategory.setText(item.getCategory());
        }
    }
}


