package com.android.focus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class ToolbarActivity extends AppCompatActivity {

    private static boolean isAppInForeground = false;
    private static int resumed = 0;
    private static int stopped = 0;

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

    @Override
    protected void onResume() {
        super.onResume();

        ++resumed;

        if (!isAppInForeground) {
            isAppInForeground = true;

            if (this instanceof LoadingActivity) {
                return;
            }

            Intent intent = new Intent(this, LoadingActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        ++stopped;

        if (isAppInBackground()) {
            isAppInForeground = false;
            resumed = 0;
            stopped = 0;
        }
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

    // region Helper methods
    public static boolean isAppInBackground() {
        return resumed == stopped;
    }
    // endregion
}
