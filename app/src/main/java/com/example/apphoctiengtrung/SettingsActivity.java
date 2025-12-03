package com.example.apphoctiengtrung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.applyTheme(this);
        setContentView(R.layout.activity_settings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.nav_settings_title);
        }

        // Set click listeners
        findViewById(R.id.setting_private_settings).setOnClickListener(this);
        findViewById(R.id.setting_profile).setOnClickListener(this);
        findViewById(R.id.setting_notifications).setOnClickListener(this);
        findViewById(R.id.setting_courses).setOnClickListener(this);
        findViewById(R.id.setting_social_accounts).setOnClickListener(this);
        findViewById(R.id.setting_privacy_settings).setOnClickListener(this);
        findViewById(R.id.setting_choose_plan).setOnClickListener(this);
        findViewById(R.id.setting_super_gift).setOnClickListener(this);
        findViewById(R.id.button_restore_subscription).setOnClickListener(this);
        findViewById(R.id.setting_help_center).setOnClickListener(this);
        findViewById(R.id.setting_feedback).setOnClickListener(this);
        findViewById(R.id.button_logout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        
        if (id == R.id.setting_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        } else if (id == R.id.setting_private_settings) {
            startActivity(new Intent(this, PrivateSettingsActivity.class));
            return;
        } else if (id == R.id.setting_notifications) {
            startActivity(new Intent(this, NotificationsSettingsActivity.class));
            return;
        }

        // Tạm thời hiển thị Toast cho các mục khác
        String message = "Chức năng này sẽ được phát triển sau";

        if (id == R.id.button_logout) {
            message = "Xử lý Đăng xuất";
            // Ví dụ: FirebaseAuth.getInstance().signOut();
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
