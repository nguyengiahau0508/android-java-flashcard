package com.example.flashcard.ui.home;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.R;
import com.example.flashcard.data.model.Deck;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDecks;
    private FloatingActionButton fabAddDeck;
    private MaterialToolbar toolbar;

    private DeckListAdapter adapter;
    private List<Deck> deckList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerViewDecks = findViewById(R.id.recyclerViewDecks);
        recyclerViewDecks.setLayoutManager(new LinearLayoutManager(this));

        fabAddDeck = findViewById(R.id.fabAddDeck);

        deckList = new ArrayList<>();
        deckList.add(new Deck(1, "Động vật", "Các loài động vật quen thuộc", 10));
        deckList.add(new Deck(2, "Màu sắc", "Tên các màu cơ bản", 8));
        deckList.add(new Deck(3, "IT Terms", "Thuật ngữ công nghệ thông tin", 12));

        adapter = new DeckListAdapter(this, deckList);
        recyclerViewDecks.setAdapter(adapter);
    }
}
