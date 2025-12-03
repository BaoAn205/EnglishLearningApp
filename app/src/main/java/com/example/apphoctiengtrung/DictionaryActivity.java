package com.example.apphoctiengtrung;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DictionaryActivity extends AppCompatActivity implements SuggestionAdapter.OnItemClickListener {

    private Spinner fromSpinner, toSpinner;
    private ImageButton swapButton;
    private TextInputLayout searchInputLayout;
    private TextInputEditText searchEditText;
    private CardView resultCard;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.applyTheme(this);
        setContentView(R.layout.activity_dictionary);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Tra cứu từ điển");
        }

        fromSpinner = findViewById(R.id.spinner_from_lang);
        toSpinner = findViewById(R.id.spinner_to_lang);
        swapButton = findViewById(R.id.button_swap_lang);
        searchInputLayout = findViewById(R.id.text_input_layout_search);
        searchEditText = findViewById(R.id.edit_text_search_word);
        resultCard = findViewById(R.id.card_result);
        resultTextView = findViewById(R.id.text_view_result);

        setupLanguageSpinners();
        setupButtons();
        setupSuggestions();
    }

    private void setupLanguageSpinners() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.language_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        fromSpinner.setSelection(0); // Tiếng Việt
        toSpinner.setSelection(1);   // Tiếng Trung (Giản thể)
    }

    private void setupButtons() {
        swapButton.setOnClickListener(v -> {
            int fromPosition = fromSpinner.getSelectedItemPosition();
            int toPosition = toSpinner.getSelectedItemPosition();
            fromSpinner.setSelection(toPosition);
            toSpinner.setSelection(fromPosition);
        });

        searchInputLayout.setEndIconOnClickListener(v -> {
            Toast.makeText(this, "Chức năng tìm kiếm bằng giọng nói sẽ được phát triển sau", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupSuggestions() {
        RecyclerView suggestionsRecyclerView = findViewById(R.id.recycler_view_suggestions);
        suggestionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> suggestions = new ArrayList<>(Arrays.asList("你好 (nǐ hǎo)", "谢谢 (xièxiè)", "再见 (zàijiàn)", "不客气 (bú kèqì)", "对不起 (duìbuqǐ)"));

        SuggestionAdapter adapter = new SuggestionAdapter(suggestions, this);
        suggestionsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onSuggestionClick(String suggestion) {
        // Khi người dùng nhấn vào một gợi ý, điền nó vào ô tìm kiếm và hiển thị kết quả giả
        searchEditText.setText(suggestion.split(" ")[0]); // Chỉ lấy phần tiếng Trung
        resultTextView.setText("Đây là kết quả dịch cho: " + suggestion);
        resultCard.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
