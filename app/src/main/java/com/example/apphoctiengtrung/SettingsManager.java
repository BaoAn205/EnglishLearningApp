package com.example.apphoctiengtrung;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {

    private static final String PREFS_NAME = "app_settings";

    // Keys
    private static final String KEY_SOUND_EFFECTS = "sound_effects";
    private static final String KEY_HAPTIC_FEEDBACK = "haptic_feedback";
    private static final String KEY_MOTIVATIONAL_MESSAGES = "motivational_messages";
    private static final String KEY_LISTENING_EXERCISES = "listening_exercises";
    private static final String KEY_FRIEND_QUESTS = "friend_quests";
    private static final String KEY_FRIEND_STREAKS = "friend_streaks";
    private static final String KEY_PINYIN = "show_pinyin";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Generic setter and getter for boolean settings
    private static void setBoolean(Context context, String key, boolean value) {
        getPrefs(context).edit().putBoolean(key, value).apply();
    }

    private static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getPrefs(context).getBoolean(key, defaultValue);
    }

    // --- Public methods for each setting ---

    public static void setSoundEffectsEnabled(Context context, boolean isEnabled) {
        setBoolean(context, KEY_SOUND_EFFECTS, isEnabled);
    }

    public static boolean isSoundEffectsEnabled(Context context) {
        return getBoolean(context, KEY_SOUND_EFFECTS, true); // Default: on
    }

    public static void setHapticFeedbackEnabled(Context context, boolean isEnabled) {
        setBoolean(context, KEY_HAPTIC_FEEDBACK, isEnabled);
    }

    public static boolean isHapticFeedbackEnabled(Context context) {
        return getBoolean(context, KEY_HAPTIC_FEEDBACK, true); // Default: on
    }

    public static void setMotivationalMessagesEnabled(Context context, boolean isEnabled) {
        setBoolean(context, KEY_MOTIVATIONAL_MESSAGES, isEnabled);
    }

    public static boolean isMotivationalMessagesEnabled(Context context) {
        return getBoolean(context, KEY_MOTIVATIONAL_MESSAGES, true); // Default: on
    }
    
    public static void setListeningExercisesEnabled(Context context, boolean isEnabled) {
        setBoolean(context, KEY_LISTENING_EXERCISES, isEnabled);
    }

    public static boolean isListeningExercisesEnabled(Context context) {
        return getBoolean(context, KEY_LISTENING_EXERCISES, true); // Default: on
    }

    public static void setFriendQuestsEnabled(Context context, boolean isEnabled) {
        setBoolean(context, KEY_FRIEND_QUESTS, isEnabled);
    }

    public static boolean isFriendQuestsEnabled(Context context) {
        return getBoolean(context, KEY_FRIEND_QUESTS, true); // Default: on
    }

    public static void setFriendStreaksEnabled(Context context, boolean isEnabled) {
        setBoolean(context, KEY_FRIEND_STREAKS, isEnabled);
    }

    public static boolean isFriendStreaksEnabled(Context context) {
        return getBoolean(context, KEY_FRIEND_STREAKS, true); // Default: on
    }

    public static void setPinyinEnabled(Context context, boolean isEnabled) {
        setBoolean(context, KEY_PINYIN, isEnabled);
    }

    public static boolean isPinyinEnabled(Context context) {
        return getBoolean(context, KEY_PINYIN, true); // Default: on
    }
}
