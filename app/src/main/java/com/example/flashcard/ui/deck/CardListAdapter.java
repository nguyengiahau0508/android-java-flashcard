package com.example.flashcard.ui.deck;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.R;
import com.example.flashcard.data.model.Card;

import java.util.List;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {

    public interface OnCardClickListener {
        void onCardClick(Card card, int position);
    }

    private final List<Card> cardList;
    private final OnCardClickListener listener;

    public CardListAdapter(List<Card> cardList, OnCardClickListener listener) {
        this.cardList = cardList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card card = cardList.get(position);

        holder.tvFront.setText(card.getFrontText());
        holder.tvBack.setText(card.getBackText());

        if (card.getImagePath() != null && !card.getImagePath().isEmpty()) {
            holder.ivCardImage.setVisibility(View.VISIBLE);
            holder.ivCardImage.setImageURI(Uri.parse(card.getImagePath()));
        } else {
            holder.ivCardImage.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCardClick(card, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        final TextView tvFront, tvBack;
        final ImageView ivCardImage;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFront = itemView.findViewById(R.id.tvFront);
            tvBack = itemView.findViewById(R.id.tvBack);
            ivCardImage = itemView.findViewById(R.id.ivCardImage);
        }
    }
}
