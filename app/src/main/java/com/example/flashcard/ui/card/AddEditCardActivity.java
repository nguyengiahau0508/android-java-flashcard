package com.example.flashcard.ui.card;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flashcard.R;
import com.example.flashcard.data.model.Card;

public class AddEditCardActivity extends AppCompatActivity {

    private EditText etFrontText, etBackText;
    private ImageView ivCardImage;
    private Button btnChooseImage, btnSave, btnCancel;

    private String imagePath = null;
    private String mode;
    private Card editingCard;

    private ActivityResultLauncher<String> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_card);

        initViews();
        initImagePicker();
        getIntentData();
        loadCardIfEditMode();
        setupListeners();
    }

    /** Initialize views from layout */
    private void initViews() {
        etFrontText = findViewById(R.id.etFrontText);
        etBackText = findViewById(R.id.etBackText);
        ivCardImage = findViewById(R.id.ivCardImage);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    /** Initialize image picker for gallery selection */
    private void initImagePicker() {
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        ivCardImage.setImageURI(uri);
                        imagePath = uri.toString();
                    }
                }
        );
    }

    /** Get intent extras (mode and card data if editing) */
    private void getIntentData() {
        Intent intent = getIntent();
        mode = intent.getStringExtra("mode");
        if ("edit".equals(mode)) {
            editingCard = (Card) intent.getSerializableExtra("card");
        }
    }

    /** Populate UI fields if editing an existing card */
    private void loadCardIfEditMode() {
        if (editingCard == null) return;

        etFrontText.setText(editingCard.getFrontText());
        etBackText.setText(editingCard.getBackText());

        if (editingCard.getImagePath() != null) {
            Uri selectedImageUri = Uri.parse(editingCard.getImagePath());
            ivCardImage.setImageURI(selectedImageUri);
            imagePath = editingCard.getImagePath();
        }
    }

    /** Set button click listeners */
    private void setupListeners() {
        btnChooseImage.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));
        btnSave.setOnClickListener(v -> saveCard());
        btnCancel.setOnClickListener(v -> cancelEdit());
    }

    /** Validate input and return the result card */
    private void saveCard() {
        String front = etFrontText.getText().toString().trim();
        String back = etBackText.getText().toString().trim();

        if (front.isEmpty() || back.isEmpty()) {
            etFrontText.setError("Required");
            etBackText.setError("Required");
            return;
        }

        Card newCard = new Card(0, 0, front, back, imagePath);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("card", newCard);

        int position = getIntent().getIntExtra("position", -1);
        resultIntent.putExtra("position", position);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    /** Validate user input */
    private boolean validateInput(String front, String back) {
        if (front.isEmpty()) {
            etFrontText.setError("Required");
            etFrontText.requestFocus();
            return false;
        }
        if (back.isEmpty()) {
            etBackText.setError("Required");
            etBackText.requestFocus();
            return false;
        }
        return true;
    }

    /** Cancel and close activity */
    private void cancelEdit() {
        setResult(RESULT_CANCELED);
        finish();
    }
}
