package com.android.focus.paneles.activities;

import android.os.Bundle;

import com.android.focus.R;
import com.android.focus.ToolbarActivity;
import com.android.focus.paneles.fragments.EncuestasFragment;

public class EncuestasActivity extends ToolbarActivity {

    private static final String TAG = EncuestasActivity.class.getCanonicalName();
    public static final String EXTRA_PANEL_INDEX = TAG + ".encuestaIndex";

    // region Activity lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        title = getString(R.string.surveys);
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, EncuestasFragment.newInstance(getIntent().getIntExtra(EXTRA_PANEL_INDEX, -1)), EncuestasFragment.FRAGMENT_TAG)
                    .commit();
        }
    }
}
