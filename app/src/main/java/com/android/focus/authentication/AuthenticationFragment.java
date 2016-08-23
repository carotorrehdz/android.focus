package com.android.focus.authentication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.android.focus.R;
import com.android.focus.main.MainActivity;
import com.android.focus.managers.UserPreferencesManager;
import com.android.focus.model.User;
import com.android.focus.network.HttpResponseHandler;
import com.android.focus.network.NetworkManager;
import com.android.focus.utils.TextUtils;
import com.android.focus.utils.UIUtils;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.android.focus.network.APIConstants.PASSWORD;
import static com.android.focus.network.APIConstants.STATUS;
import static com.android.focus.network.APIConstants.SUCCESS;
import static com.android.focus.network.APIConstants.USERNAME;

public class AuthenticationFragment extends Fragment implements OnClickListener, OnEditorActionListener {

    private static final String TAG = AuthenticationFragment.class.getCanonicalName();
    public static final String FRAGMENT_TAG = TAG + ".authenticationFragment";

    private Activity activity;
    private boolean enableBack = true;
    private Button signInButton;
    private EditText username;
    private EditText password;
    private RequestParams params;
    private View loader;

    // region Text watchers
    private TextWatcher usernameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing.
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            username.setBackgroundResource(R.drawable.edit_text_border);
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Do nothing.
        }
    };

    private TextWatcher passwordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing.
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            password.setBackgroundResource(R.drawable.edit_text_border);
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Do nothing.
        }
    };
    // endregion

    // region Factory methods

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AuthenticationFragment.
     */
    @NonNull
    public static AuthenticationFragment newInstance() {
        AuthenticationFragment fragment = new AuthenticationFragment();
        fragment.setArguments(new Bundle());

        return fragment;
    }
    // endregion

    // region Fragment lifecycle
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_authentication, container, false);

        username = (EditText) view.findViewById(R.id.txt_username);
        username.addTextChangedListener(usernameTextWatcher);
        password = (EditText) view.findViewById(R.id.txt_password);
        password.addTextChangedListener(passwordTextWatcher);
        password.setOnEditorActionListener(this);
        signInButton = (Button) view.findViewById(R.id.btn_sign_in);
        signInButton.setOnClickListener(this);
        loader = view.findViewById(R.id.loader);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = getActivity();
        UIUtils.hideKeyboardOnFragmentStart(activity);
    }
    // endregion

    // region UI methods
    private void showError(String error) {
        enableBack = true;
        loader.setVisibility(View.GONE);
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
    }
    // endregion

    // region Helper methods
    private boolean checkForInvalidFields() {
        boolean invalidFields = false;
        String username = this.username.getText().toString().trim();
        String password = this.password.getText().toString().trim();

        if (!TextUtils.isValidString(username)) {
            invalidFields = true;
            this.username.setBackgroundResource(R.drawable.edit_text_border_invalid);
        }

        if (!TextUtils.isValidString(password)) {
            invalidFields = true;
            this.password.setBackgroundResource(R.drawable.edit_text_border_invalid);
        }

        if (invalidFields) {
            return true;
        }

        params.put(USERNAME, username);
        params.put(PASSWORD, password);

        return false;
    }
    // endregion

    // region Listeners
    @Override
    public void onClick(View view) {
        if (view.equals(signInButton)) {
            signIn();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == KeyEvent.KEYCODE_ENTER) {
            signInButton.callOnClick();
        }

        return false;
    }
    // endregion

    // region Click actions
    public void handleOnBackPressedEvent() {
        if (!enableBack) {
            return;
        }

        activity.finish();
    }

    private void signIn() {
        params = new RequestParams();
        enableBack = false;
        UIUtils.hideKeyboardIfShowing(activity);

        if (checkForInvalidFields()) {
            return;
        }

        if (NetworkManager.isNetworkUnavailable(activity)) {
            return;
        }

        loader.setVisibility(View.VISIBLE);

        NetworkManager.sigIn(params, new HttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (!response.optString(STATUS).equals(SUCCESS)) {
                    showError(getString(R.string.sign_in_error));
                    return;
                }

                // Save user and init data.
                User user = (new Gson()).fromJson(response.toString(), User.class);
                User.setCurrentUser(user);
                UserPreferencesManager.saveCurrentUser(user);
                UserPreferencesManager.initData(response);

                // TODO: Register device token.

                // Close any authentication activity before starting main activity.
                Intent intent = new Intent(activity, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                showError(errorResponse);
            }
        });
    }
    // endregion
}
