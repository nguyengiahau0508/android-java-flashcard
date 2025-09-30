package com.example.flashcard.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcard.R;
import com.example.flashcard.data.model.Deck;
import com.example.flashcard.ui.deck.AddEditDeckActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    // --- UI Components ---
    private RecyclerView recyclerViewDecks;
    private FloatingActionButton fabAddDeck;
    private MaterialToolbar toolbar;

    // --- Adapter & Data ---
    private DeckListAdapter deckAdapter;
    private final List<Deck> deckList = new ArrayList<>();

    // --- Activity Result ---
    private ActivityResultLauncher<Intent> addDeckResultLauncher;
    private ActivityResultLauncher<Intent> editDeckResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        initViews();
        setupToolbar();
        setupRecyclerView();
        loadInitialDeckData();
        setupListeners();
        registerActivityLaunchers();
    }

    // --- Initialization ---

    /**
     * Finds views by their IDs.
     */
    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerViewDecks = findViewById(R.id.recyclerViewDecks);
        fabAddDeck = findViewById(R.id.fabAddDeck);
    }

    /**
     * Sets up the Toolbar as the ActionBar.
     */
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        // Extra toolbar setup can be added here
    }

    /**
     * Configures RecyclerView with its LayoutManager and Adapter.
     */
    private void setupRecyclerView() {
        recyclerViewDecks.setLayoutManager(new LinearLayoutManager(this));
        deckAdapter = new DeckListAdapter(this, deckList, (deck, pos) -> openEditDeck(deck, pos));
        recyclerViewDecks.setAdapter(deckAdapter);
    }

    /**
     * Loads sample deck data.
     * In a real app, this should come from a repository or ViewModel.
     */
    private void loadInitialDeckData() {
        deckList.add(new Deck(1, "Animals", "Common animal species", 10));
        deckList.add(new Deck(2, "Colors", "Names of basic colors", 8));
        deckList.add(new Deck(3, "IT Terms", "Information Technology terms", 12));
    }

    // --- Listeners ---

    /**
     * Attaches click listeners for UI components.
     */
    private void setupListeners() {
        fabAddDeck.setOnClickListener(v -> navigateToAddDeckActivity());
    }

    /**
     * Launches AddEditDeckActivity in "add" mode.
     */
    private void navigateToAddDeckActivity() {
        Intent intent = new Intent(this, AddEditDeckActivity.class);
        intent.putExtra("mode", "add");
        addDeckResultLauncher.launch(intent);
    }

    // --- Activity Result Handling ---

    /**
     * Registers ActivityResultLaunchers for starting activities for results.
     */
    private void registerActivityLaunchers() {
        addDeckResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Serializable serializableDeck = result.getData().getSerializableExtra("deck");
                        if (serializableDeck instanceof Deck) {
                            addNewDeck((Deck) serializableDeck);
                        }
                    }
                }
        );

        // Edit Deck launcher
        editDeckResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Deck updatedDeck = (Deck) result.getData().getSerializableExtra("deck");
                        int pos = result.getData().getIntExtra("position", -1);
                        if (updatedDeck != null && pos >= 0) {
                            deckList.set(pos, updatedDeck);
                            deckAdapter.notifyItemChanged(pos);
                        }
                    }
                }
        );
    }

    /**
     * Adds a new Deck to the list and updates the adapter.
     */
    private void addNewDeck(Deck newDeck) {
        deckList.add(newDeck);
        deckAdapter.notifyItemInserted(deckList.size() - 1);
        Toast.makeText(this, "Deck added: " + newDeck.getName(), Toast.LENGTH_SHORT).show();
    }

    private void openEditDeck(Deck deck, int position) {
        Intent intent = new Intent(this, AddEditDeckActivity.class);
        intent.putExtra("mode", "edit");
        intent.putExtra("deck", deck);
        intent.putExtra("position", position);
        editDeckResultLauncher .launch(intent);
    }

    // --- Menu Handling ---

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            handleSettingsClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handles Settings menu click.
     */
    private void handleSettingsClick() {
        Toast.makeText(this, "Open Settings", Toast.LENGTH_SHORT).show();
        // TODO: Launch SettingsActivity later
    }
}
