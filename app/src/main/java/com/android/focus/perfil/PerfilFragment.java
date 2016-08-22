package com.android.focus.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.focus.R;
import com.android.focus.SplashScreenActivity;
import com.android.focus.managers.UserPreferencesManager;

public class PerfilFragment extends Fragment implements OnClickListener {

    private Button signOutButton;

    // region Factory methods

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AuthenticationFragment.
     */
    @NonNull
    public static PerfilFragment newInstance() {
        PerfilFragment fragment = new PerfilFragment();
        fragment.setArguments(new Bundle());

        return fragment;
    }
    // endregion

    // region Fragment lifecycle
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        signOutButton = (Button) view.findViewById(R.id.btn_sign_out);
        signOutButton.setOnClickListener(this);

        return view;
    }
    // endregion

    // region Listeners
    @Override
    public void onClick(View view) {
        if (view.equals(signOutButton)) {
            signOut();
        }
    }
    // endregion

    // region Click actions
    private void signOut() {
        UserPreferencesManager.clearCurrentUser();

        // Close any Schoolhub activity before starting Welcome activity.
        Intent intent = new Intent(getActivity(), SplashScreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    // endregion
}
