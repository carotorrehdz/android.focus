package com.android.focus;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class ToolbarActivity extends AppCompatActivity {

    protected int layoutId = R.layout.activity_toolbar;
    protected String title;
    private boolean displayHomeAsUpEnabled;

    // region Activity lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(layoutId);

        if (layoutId == R.layout.activity_loading) {
            return;
        }

        Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);
        displayHomeAsUpEnabled = (layoutId == R.layout.activity_toolbar);
        setTitle(title);
    }
    // endregion

    // region Menu methods
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    // endregion

    // region UI methods
    protected void setTitle(String title) {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
        }
    }
    // endregion
}
