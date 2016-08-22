package com.android.focus.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.android.focus.FocusApp;

/**
 * Utils to perform UI actions.
 */

public class UIUtils {

    /**
     * Hides the keyboard that might be shown by any of the edit texts.
     *
     * @param activity Activity to customize.
     */
    public static void hideKeyboardIfShowing(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Hides the keyboard when a fragment starts.
     *
     * @param activity Activity to customize.
     */
    public static void hideKeyboardOnFragmentStart(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    /**
     * Get string resource.
     *
     * @param resourceId String resource id.
     */
    public static String getString(int resourceId) {
        return FocusApp.getAppResources().getString(resourceId);
    }
}
