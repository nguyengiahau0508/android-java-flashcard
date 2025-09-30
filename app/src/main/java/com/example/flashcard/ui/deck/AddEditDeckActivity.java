package com.example.flashcard.ui.deck;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flashcard.R;
import com.example.flashcard.data.model.Deck;

public class AddEditDeckActivity extends AppCompatActivity {

    private EditText etDeckName, etDeckDescription;
    private Button btnSaveDeck, btnCancelDeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_edit_deck);

        initViews();
        loadDeckIfEditMode();
        setupListeners();
    }

    private void initViews() {
        etDeckName = findViewById(R.id.etDeckName);
        etDeckDescription = findViewById(R.id.etDeckDescription);
        btnSaveDeck = findViewById(R.id.btnSaveDeck);
        btnCancelDeck = findViewById(R.id.btnCancelDeck);
    }

    /**
     * Load deck data into the form if in "edit" mode.
     */
    private void loadDeckIfEditMode() {
        String mode = getIntent().getStringExtra("mode");
        if ("edit".equals(mode)) {
            Deck deck = (Deck) getIntent().getSerializableExtra("deck");
            if (deck != null) {
                etDeckName.setText(deck.getName());
                etDeckDescription.setText(deck.getDescription());
            }
        }
    }

    private void setupListeners() {
        btnSaveDeck.setOnClickListener(v -> saveDeck());
        btnCancelDeck.setOnClickListener(v -> finish());
    }

    private void saveDeck() {
        String name = etDeckName.getText().toString().trim();
        String description = etDeckDescription.getText().toString().trim();

        if (name.isEmpty()) {
            etDeckName.setError("Name is required");
            return;
        }

        Deck newDeck = new Deck(0, name, description, 0);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("deck", newDeck);

        int position = getIntent().getIntExtra("position", -1);
        resultIntent.putExtra("position", position);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

}
