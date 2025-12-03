package com.example.apphoctiengtrung;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextFullName;
    private Button buttonRegister;
    private TextView textViewGoToLogin;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.applyTheme(this); // Áp dụng theme
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        editTextFullName = findViewById(R.id.edit_text_full_name);
        buttonRegister = findViewById(R.id.button_register);
        textViewGoToLogin = findViewById(R.id.text_view_go_to_login);
        progressBar = findViewById(R.id.progress_bar);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        textViewGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay về màn hình Đăng nhập
                finish(); // Đóng màn hình hiện tại
            }
        });
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.email_required));
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getString(R.string.invalid_email));
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.password_required));
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError(getString(R.string.password_length_error));
            editTextPassword.requestFocus();
            return;
        }

        if (fullName.isEmpty()) {
            editTextFullName.setError(getString(R.string.full_name_required));
            editTextFullName.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        // Tạo người dùng trên Firebase Auth
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Cập nhật tên hiển thị
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(fullName).build();

                            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // Lưu thông tin vào Firestore
                                    saveUserToFirestore(user.getUid(), fullName, email);
                                }
                            });

                        } else {
                            // Đăng ký thất bại
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, getString(R.string.register_failed, task.getException().getMessage()), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    // Hàm này lưu người dùng vào Firestore Database
    private void saveUserToFirestore(String userId, String fullName, String email) {
        // Sử dụng App ID (giả sử là tên app) và User ID để tạo đường dẫn
        String appId = "AppHocTiengTrung"; // Hoặc bạn có thể dùng __app_id nếu có

        // Tạo một đối tượng (Map) chứa thông tin người dùng
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("fullName", fullName);
        userMap.put("email", email);

        // Lưu vào đường dẫn private của người dùng
        db.collection("artifacts").document(appId)
                .collection("users").document(userId)
                .collection("profile").document("info") // Lưu vào một doc cụ thể
                .set(userMap) // set() sẽ tạo mới hoặc ghi đè
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();

                            // CHỈNH SỬA Ở ĐÂY: Chuyển thẳng về MainActivity
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            // Cờ này sẽ xóa các màn hình cũ (Login/Register) và tạo task mới
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish(); // Đóng màn hình Register

                        } else {
                            Toast.makeText(RegisterActivity.this, getString(R.string.save_info_failed), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
