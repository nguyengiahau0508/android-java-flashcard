package com.example.flashcard.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    private final OnDeckEditListener editListener;

    public interface OnDeckEditListener {
        void onEditDeck(Deck deck, int position);
    }

    public DeckListAdapter(Context context, List<Deck> deckList, OnDeckEditListener editListener) {
        this.context = context;
        this.deckList = deckList;
        this.editListener = editListener;
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

        // Bind data
        holder.tvDeckName.setText(deck.getName());
        holder.tvDeckCount.setText(deck.getCardCount() + " cards");

        // Open detail when click on item
        holder.itemView.setOnClickListener(v -> openDeckDetail(deck));

        // Edit button
        holder.btnEditDeck.setOnClickListener(v -> {
            if (editListener != null) {
                editListener.onEditDeck(deck, position);
            }
        });

        // Delete button (not handled yet)
        holder.btnDeleteDeck.setOnClickListener(v -> {
            // TODO: Implement delete deck later
        });
    }

    @Override
    public int getItemCount() {
        return deckList.size();
    }

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
        final ImageButton btnEditDeck, btnDeleteDeck;

        public DeckViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeckName = itemView.findViewById(R.id.tvDeckName);
            tvDeckCount = itemView.findViewById(R.id.tvDeckCount);
            btnEditDeck = itemView.findViewById(R.id.btnEditDeck);
            btnDeleteDeck = itemView.findViewById(R.id.btnDeleteDeck);
        }
    }
}
