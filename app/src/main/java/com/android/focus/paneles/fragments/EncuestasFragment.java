package com.android.focus.paneles.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.focus.FocusApp;
import com.android.focus.R;
import com.android.focus.model.Encuesta;
import com.android.focus.utils.DateUtils;

import java.util.List;

public class EncuestasFragment extends Fragment {

    public static final String FRAGMENT_TAG = EncuestasFragment.class.getCanonicalName();
    private static final String ARGS_PANEL_INDEX = FRAGMENT_TAG + ".panelIndex";

    private int panelId;
    private LinearLayout encuestasList;
    private List<Encuesta> encuestas;

    // region Factory methods

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EncuestasFragment.
     */
    @NonNull
    public static EncuestasFragment newInstance(int panelIndex) {
        EncuestasFragment fragment = new EncuestasFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_PANEL_INDEX, panelIndex);
        fragment.setArguments(args);

        return fragment;
    }
    // endregion

    // region Fragment lifecycle
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        panelId = getArguments().getInt(ARGS_PANEL_INDEX);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_encuestas, container, false);

        encuestasList = (LinearLayout) view.findViewById(R.id.list_encuestas);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        encuestas = Encuesta.getUserEncuestas(panelId);
        encuestasList.removeAllViews();

        for (Encuesta encuesta : encuestas) {
            encuestasList.addView(createViewForEncuesta(encuesta));
        }
    }
    // endregion

    // region UI methods
    private View createViewForEncuesta(Encuesta encuesta) {
        View view = View.inflate(FocusApp.getContext(), R.layout.card_detail, null);
        TextView title = (TextView) view.findViewById(R.id.txt_title);
        title.setText(encuesta.getNombre());
        TextView preguntas = (TextView) view.findViewById(R.id.txt_preguntas);
        preguntas.setText(getResources().getQuantityString(R.plurals.preguntas_count, encuesta.getPreguntas().size(), encuesta.getPreguntas().size()));
        preguntas.setVisibility(View.VISIBLE);
        TextView startDate = (TextView) view.findViewById(R.id.txt_start_date);
        startDate.setText(DateUtils.dateFormat(encuesta.getFechaInicio()));
        TextView endDate = (TextView) view.findViewById(R.id.txt_end_date);
        endDate.setText(DateUtils.dateFormat(encuesta.getFechaFin()));

        return view;
    }
    // endregion
}
