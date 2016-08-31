package com.android.focus.paneles.activities;

import android.os.Bundle;

import com.android.focus.R;
import com.android.focus.ToolbarActivity;
import com.android.focus.paneles.fragments.EncuestasFragment;

public class EncuestasActivity extends ToolbarActivity {

    private static final String TAG = EncuestasActivity.class.getCanonicalName();
    public static final String EXTRA_PANEL_ID = TAG + ".panelId";

    private EncuestasFragment fragment;

    // region Activity lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        title = getString(R.string.surveys);
        super.onCreate(savedInstanceState);

        fragment = EncuestasFragment.newInstance(getIntent().getIntExtra(EXTRA_PANEL_ID, -1));

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment, EncuestasFragment.FRAGMENT_TAG)
                    .commit();
        }
    }
    // endregion

    // region OnBackPressed interface
    @Override
    public void onBackPressed() {
        fragment.handleOnBackPressedEvent();
    }
    // endregion
}
