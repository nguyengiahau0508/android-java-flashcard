package com.example.flashcard.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.R;
import com.example.flashcard.data.model.Deck;

import java.util.List;

public class DeckListAdapter extends RecyclerView.Adapter<DeckListAdapter.DeckViewHolder> {

    private Context context;
    private List<Deck> deckList;

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
        holder.tvDeckName.setText(deck.getName());
        holder.tvDeckCount.setText(deck.getCardCount() + " cards");

        holder.itemView.setOnClickListener(v ->
                Toast.makeText(context, "Clicked: " + deck.getName(), Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return deckList.size();
    }

    static class DeckViewHolder extends RecyclerView.ViewHolder {
        TextView tvDeckName, tvDeckCount;

        public DeckViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeckName = itemView.findViewById(R.id.tvDeckName);
            tvDeckCount = itemView.findViewById(R.id.tvDeckCount);
        }
    }
}
