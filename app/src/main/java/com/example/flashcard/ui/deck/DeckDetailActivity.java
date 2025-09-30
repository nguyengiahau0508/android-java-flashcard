package com.example.flashcard.ui.deck;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.R;
import com.example.flashcard.data.model.Card;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DeckDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCards;
    private FloatingActionButton fabAddCard;
    private MaterialToolbar toolbar;
    private CardListAdapter adapter;
    private List<Card> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_detail);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewCards = findViewById(R.id.recyclerViewCards);
        recyclerViewCards.setLayoutManager(new LinearLayoutManager(this));

        fabAddCard = findViewById(R.id.fabAddCard);

        // Dummy data
        cardList = new ArrayList<>();
        cardList.add(new Card(1, 1, "Dog", "Con chó", null));
        cardList.add(new Card(2, 1, "Cat", "Con mèo", null));
        cardList.add(new Card(3, 1, "Elephant", "Con voi", null));

        adapter = new CardListAdapter(this, cardList);
        recyclerViewCards.setAdapter(adapter);

        fabAddCard.setOnClickListener(v ->
                Toast.makeText(this, "Thêm Card mới", Toast.LENGTH_SHORT).show()
        );
    }
}
