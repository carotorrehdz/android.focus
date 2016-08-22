package com.android.focus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.focus.main.MainActivity;
import com.android.focus.managers.UserPreferencesManager;
import com.android.focus.model.User;
import com.android.focus.network.APIConstants;
import com.android.focus.network.HttpResponseHandler;
import com.android.focus.network.NetworkManager;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoadingActivity extends AppCompatActivity {

    // region Activity lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        super.setContentView(R.layout.activity_loading);

        final Activity activity = this;
        RequestParams params = new RequestParams();
        params.put(APIConstants.PANELISTA, UserPreferencesManager.getCurrentUserId());
        NetworkManager.getData(params, new HttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Save user and init data.
                User.setCurrentUser(UserPreferencesManager.getCurrentUser());
                UserPreferencesManager.initData(response);

                // Close any loading activity before starting main activity.
                Intent intent = new Intent(activity, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {

            }
        });
    }
    // endregion
}
