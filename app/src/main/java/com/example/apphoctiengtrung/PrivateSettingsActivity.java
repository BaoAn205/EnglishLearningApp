package com.example.apphoctiengtrung;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class PrivateSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.applyTheme(this);
        setContentView(R.layout.activity_private_settings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Cài đặt riêng");
        }

        // Kết nối và thiết lập các switch
        setupSwitch(R.id.switch_sound_effects, SettingsManager::isSoundEffectsEnabled, SettingsManager::setSoundEffectsEnabled);
        setupSwitch(R.id.switch_haptic_feedback, SettingsManager::isHapticFeedbackEnabled, SettingsManager::setHapticFeedbackEnabled);
        setupSwitch(R.id.switch_motivational_messages, SettingsManager::isMotivationalMessagesEnabled, SettingsManager::setMotivationalMessagesEnabled);
        setupSwitch(R.id.switch_listening_exercises, SettingsManager::isListeningExercisesEnabled, SettingsManager::setListeningExercisesEnabled);
        setupSwitch(R.id.switch_friend_quests, SettingsManager::isFriendQuestsEnabled, SettingsManager::setFriendQuestsEnabled);
        setupSwitch(R.id.switch_friend_streaks, SettingsManager::isFriendStreaksEnabled, SettingsManager::setFriendStreaksEnabled);
        setupSwitch(R.id.switch_pinyin, SettingsManager::isPinyinEnabled, SettingsManager::setPinyinEnabled);
        
        // Switch cho Chế độ tối cần xử lý đặc biệt
        SwitchMaterial darkModeSwitch = findViewById(R.id.switch_dark_mode);
        darkModeSwitch.setChecked(ThemeManager.isDarkMode(this));
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ThemeManager.setDarkMode(this, isChecked);
        });
    }

    // Helper method để giảm code lặp lại
    private void setupSwitch(int switchId, BooleanGetter getter, BooleanSetter setter) {
        SwitchMaterial switchView = findViewById(switchId);
        switchView.setChecked(getter.get(this));
        switchView.setOnCheckedChangeListener((buttonView, isChecked) -> setter.set(this, isChecked));
    }

    // Functional interfaces để truyền phương thức
    interface BooleanGetter {
        boolean get(Context context);
    }

    interface BooleanSetter {
        void set(Context context, boolean value);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
