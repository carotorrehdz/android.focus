package com.android.focus.network;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.focus.FocusApp;
import com.android.focus.R;
import com.android.focus.utils.UIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import static com.android.focus.network.APIConstants.ACTION;
import static com.android.focus.network.APIConstants.GET_DATA;
import static com.android.focus.network.APIConstants.SAVE_ANSWERS;
import static com.android.focus.network.APIConstants.SIGN_IN;
import static com.android.focus.network.APIConstants.START_SURVEY;

/**
 * Manager to perform network operations.
 */

public class NetworkManager {

    private static final String URL = "http://ec2-52-26-0-111.us-west-2.compute.amazonaws.com/focus/api/controller.php";

    //  region HTTP Requests
    public static void sigIn(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        post(SIGN_IN, params, responseHandler);
    }

    public static void getData(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        post(GET_DATA, params, responseHandler);
    }

    public static void startSurvey(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        post(START_SURVEY, params, responseHandler);
    }

    public static void saveAnswers(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        post(SAVE_ANSWERS, params, responseHandler);
    }

    private static void post(String action, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        params.put(ACTION, action);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.post(URL, params, responseHandler);
    }
    // endregion

    // region Helper methods
    public static boolean isNetworkUnavailable() {
        ConnectivityManager manager = (ConnectivityManager) FocusApp.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();

        return (activeNetworkInfo == null || !activeNetworkInfo.isConnectedOrConnecting());
    }

    public static boolean isNetworkUnavailable(Activity activity) {
        boolean isNetworkUnavailable = isNetworkUnavailable();

        if (isNetworkUnavailable) {
            Toast.makeText(activity, UIUtils.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

        return isNetworkUnavailable;
    }
    // endregion
}
