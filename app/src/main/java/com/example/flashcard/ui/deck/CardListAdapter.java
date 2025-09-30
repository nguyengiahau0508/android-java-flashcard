package com.example.flashcard.ui.deck;

import android.content.Context;
import android.content.Intent;
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
import com.example.flashcard.ui.card.AddEditCardActivity;

import java.util.List;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardViewHolder> {

    private final Context context;
    private final List<Card> cardList;

    public CardListAdapter(Context context, List<Card> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card card = cardList.get(position);

        // Bind card data
        holder.tvFront.setText(card.getFrontText());
        holder.tvBack.setText(card.getBackText());

        // Show image if available
        if (card.getImagePath() != null && !card.getImagePath().isEmpty()) {
            holder.ivCardImage.setVisibility(View.VISIBLE);
            holder.ivCardImage.setImageURI(Uri.parse(card.getImagePath()));
        } else {
            holder.ivCardImage.setVisibility(View.GONE);
        }

        // Handle card click → open in edit mode
        holder.itemView.setOnClickListener(v -> openEditCard(card));
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    private void openEditCard(Card card) {
        Intent intent = new Intent(context, AddEditCardActivity.class);
        intent.putExtra("mode", "edit");
        intent.putExtra("card", card);
        context.startActivity(intent);
    }

    // --- ViewHolder ---

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
