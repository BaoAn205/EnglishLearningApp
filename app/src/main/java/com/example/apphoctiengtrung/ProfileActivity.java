package com.example.apphoctiengtrung;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ProfileActivity extends AppCompatActivity {

    private TextInputEditText editTextFullName, editTextUsername, editTextEmail;
    private Button buttonSaveProfile;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.applyTheme(this);
        setContentView(R.layout.activity_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Hồ sơ");
        }

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        editTextFullName = findViewById(R.id.edit_text_full_name);
        editTextUsername = findViewById(R.id.edit_text_username);
        editTextEmail = findViewById(R.id.edit_text_email);
        buttonSaveProfile = findViewById(R.id.button_save_profile);

        loadUserProfile();

        buttonSaveProfile.setOnClickListener(v -> saveUserProfile());
    }

    private void loadUserProfile() {
        if (currentUser != null) {
            editTextFullName.setText(currentUser.getDisplayName());
            editTextEmail.setText(currentUser.getEmail());
            
            // Username thường không có sẵn trong Firebase Auth, bạn có thể lấy từ Firestore nếu đã lưu
            // Tạm thời để trống hoặc dùng một phần của email
            if (currentUser.getEmail() != null) {
                editTextUsername.setText(currentUser.getEmail().split("@")[0]);
            }
        } else {
            // Người dùng chưa đăng nhập, không nên ở màn hình này
            Toast.makeText(this, "Vui lòng đăng nhập để xem hồ sơ", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveUserProfile() {
        String newFullName = editTextFullName.getText().toString().trim();

        if (newFullName.isEmpty()) {
            editTextFullName.setError("Họ và tên không được để trống");
            editTextFullName.requestFocus();
            return;
        }

        if (currentUser != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newFullName)
                    .build();

            currentUser.updateProfile(profileUpdates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Cập nhật hồ sơ thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Cập nhật thất bại, vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
