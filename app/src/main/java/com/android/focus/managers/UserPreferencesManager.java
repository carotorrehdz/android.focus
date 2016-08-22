package com.android.focus.managers;

import android.content.SharedPreferences;

import com.android.focus.FocusApp;
import com.android.focus.model.User;

import static com.android.focus.model.User.EMAIL;
import static com.android.focus.model.User.ID;
import static com.android.focus.model.User.NOMBRE;
import static com.android.focus.model.User.USERNAME;

public class UserPreferencesManager {

    private static SharedPreferences preferences;

    // region User
    public static boolean hasCurrentUser() {
        return FocusApp.getUserPreferences().contains(ID);
    }

    public static int getCurrentUserId() {
        preferences = FocusApp.getUserPreferences();

        return getInt(ID);
    }

    public static void saveCurrentUser(User user) {
        preferences = FocusApp.getUserPreferences();
        setInt(ID, user.getId());
        setString(USERNAME, user.getUsername());
        setString(EMAIL, user.getEmail());
        setString(NOMBRE, user.getNombre());
    }

    public static User getCurrentUser() {
        preferences = FocusApp.getUserPreferences();
        User currentUser = new User();
        currentUser.setId(getInt(ID));
        currentUser.setUsername(getString(USERNAME));
        currentUser.setEmail(getString(EMAIL));
        currentUser.setNombre(getString(NOMBRE));

        return currentUser;
    }

    public static void clearCurrentUser() {
        preferences = FocusApp.getUserPreferences();
        remove(ID);
        remove(USERNAME);
        remove(EMAIL);
        remove(NOMBRE);
    }
    // endregion

    // region Helper methods
    private static int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    private static void setInt(String key, int value) {
        preferences.edit().putInt(key, value).apply();
    }

    private static String getString(String key) {
        return preferences.getString(key, "");
    }

    private static void setString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    private static void remove(String key) {
        preferences.edit().remove(key).apply();
    }
    // endregion
}
