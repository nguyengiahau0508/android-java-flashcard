package com.example.flashcard.ui.card;

import android.content.Intent;
import android.graphics.BitmapFactory;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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
                        // Copy the selected image to app storage
                        String localPath = copyUriToInternalStorage(uri);
                        if (localPath != null) {
                            imagePath = localPath;
                            ivCardImage.setImageBitmap(BitmapFactory.decodeFile(localPath));
                        }
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
            imagePath = editingCard.getImagePath();
            ivCardImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
        }
    }

    /** Set button click listeners */
    private void setupListeners() {
        btnChooseImage.setOnClickListener(v -> imagePickerLauncher.launch("image/*"));
        btnSave.setOnClickListener(v -> saveCard());
        btnCancel.setOnClickListener(v -> cancelEdit());
    }

    /** Validate input and return the result card */
    /** Validate input and return the result card */
    private void saveCard() {
        String front = etFrontText.getText().toString().trim();
        String back = etBackText.getText().toString().trim();

        if (!validateInput(front, back)) return;

        // Nếu đang edit thì giữ nguyên id và deckId
        int id = editingCard != null ? editingCard.getId() : 0;
        int deckId = editingCard != null ? editingCard.getDeckId() : getIntent().getIntExtra("deck_id", 0);

        Card resultCard = new Card(id, deckId, front, back, imagePath);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("card", resultCard);

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

    private String copyUriToInternalStorage(Uri uri) {
        try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
            File file = new File(getFilesDir(), "image_" + System.currentTimeMillis() + ".jpg");
            try (OutputStream outputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
            }
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
