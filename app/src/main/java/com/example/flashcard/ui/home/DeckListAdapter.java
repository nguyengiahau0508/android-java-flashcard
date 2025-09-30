package com.example.flashcard.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.R;
import com.example.flashcard.data.model.Deck;
import com.example.flashcard.ui.deck.DeckDetailActivity;

import java.util.List;

public class DeckListAdapter extends RecyclerView.Adapter<DeckListAdapter.DeckViewHolder> {

    private final Context context;
    private final List<Deck> deckList;

    public DeckListAdapter(Context context, List<Deck> deckList) {
        this.context = context;
        this.deckList = deckList;
    }

    @NonNull
    @Override
    public DeckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_deck, parent, false);
        return new DeckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeckViewHolder holder, int position) {
        Deck deck = deckList.get(position);

        // Bind deck data to views
        holder.tvDeckName.setText(deck.getName());
        holder.tvDeckCount.setText(deck.getCardCount() + " cards");

        // Set item click listener
        holder.itemView.setOnClickListener(v -> openDeckDetail(deck));
    }

    @Override
    public int getItemCount() {
        return deckList.size();
    }

    /**
     * Opens DeckDetailActivity for the selected deck.
     */
    private void openDeckDetail(Deck deck) {
        Intent intent = new Intent(context, DeckDetailActivity.class);
        intent.putExtra("deck_id", deck.getId());
        intent.putExtra("deck_name", deck.getName());
        context.startActivity(intent);
    }

    // --- ViewHolder ---

    static class DeckViewHolder extends RecyclerView.ViewHolder {
        final TextView tvDeckName;
        final TextView tvDeckCount;

        public DeckViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeckName = itemView.findViewById(R.id.tvDeckName);
            tvDeckCount = itemView.findViewById(R.id.tvDeckCount);
        }
    }
}
