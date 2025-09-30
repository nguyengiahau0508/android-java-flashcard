package com.example.flashcard.ui.deck;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.R;
import com.example.flashcard.data.model.Card;
import com.example.flashcard.ui.card.AddEditCardActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DeckDetailActivity extends AppCompatActivity {

    // --- UI Components ---
    private RecyclerView recyclerViewCards;
    private FloatingActionButton fabAddCard;
    private MaterialToolbar toolbar;

    // --- Adapter & Data ---
    private CardListAdapter adapter;
    private final List<Card> cardList = new ArrayList<>();

    // --- Activity Result Launchers ---
    private ActivityResultLauncher<Intent> addCardLauncher;
    private ActivityResultLauncher<Intent> editCardLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_detail);

        initViews();
        setupToolbar();
        setupRecyclerView();
        loadDummyCards();
        registerActivityLaunchers();
        setupListeners();
    }

    // --- Initialization ---

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerViewCards = findViewById(R.id.recyclerViewCards);
        fabAddCard = findViewById(R.id.fabAddCard);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);

        // Set title from Intent data
        Intent intent = getIntent();
        String deckName = intent.getStringExtra("deck_name");
        setTitle(deckName != null ? deckName : "Deck Detail");
    }

    private void setupRecyclerView() {
        recyclerViewCards.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CardListAdapter(this, cardList);
        recyclerViewCards.setAdapter(adapter);
    }

    private void loadDummyCards() {
        // TODO: Replace this with actual data loading from DB/Repository
        cardList.add(new Card(1, 1, "Dog", "Con chó", null));
        cardList.add(new Card(2, 1, "Cat", "Con mèo", null));
        cardList.add(new Card(3, 1, "Elephant", "Con voi", null));
    }

    // --- Listeners & Launchers ---

    private void setupListeners() {
        fabAddCard.setOnClickListener(v -> navigateToAddCard());
    }

    private void navigateToAddCard() {
        Toast.makeText(this, "Add new Card", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AddEditCardActivity.class);
        intent.putExtra("mode", "add");
        addCardLauncher.launch(intent);
    }

    private void registerActivityLaunchers() {
        addCardLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Card newCard = (Card) result.getData().getSerializableExtra("card");
                        if (newCard != null) {
                            addNewCard(newCard);
                        }
                    }
                }
        );

        // TODO: implement editCardLauncher if editing is required
    }

    private void addNewCard(Card newCard) {
        cardList.add(newCard);
        adapter.notifyItemInserted(cardList.size() - 1);
    }
}
