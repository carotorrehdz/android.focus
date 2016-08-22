package com.android.focus.paneles.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.focus.FocusApp;
import com.android.focus.R;
import com.android.focus.model.Panel;
import com.android.focus.paneles.activities.EncuestasActivity;
import com.android.focus.utils.DateUtils;

import java.util.List;

import static com.android.focus.paneles.activities.EncuestasActivity.EXTRA_PANEL_INDEX;

public class PanelesFragment extends Fragment {

    private LinearLayout panelsList;
    private List<Panel> panels;

    // region Factory methods

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PanelesFragment.
     */
    @NonNull
    public static PanelesFragment newInstance() {
        PanelesFragment fragment = new PanelesFragment();
        fragment.setArguments(new Bundle());

        return fragment;
    }
    // endregion

    // region Fragment lifecycle
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_paneles, container, false);

        panelsList = (LinearLayout) view.findViewById(R.id.list_panels);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        panels = Panel.getUserPaneles();
        panelsList.removeAllViews();

        for (Panel panel : panels) {
            panelsList.addView(createViewForPanel(panel));
        }
    }
    // endregion

    // region UI methods
    private View createViewForPanel(final Panel panel) {
        View view = View.inflate(FocusApp.getContext(), R.layout.card_detail, null);
        TextView title = (TextView) view.findViewById(R.id.txt_title);
        title.setText(panel.getNombre());
        TextView startDate = (TextView) view.findViewById(R.id.txt_start_date);
        startDate.setText(DateUtils.dateFormat(panel.getFechaInicio()));
        TextView endDate = (TextView) view.findViewById(R.id.txt_end_date);
        endDate.setText(DateUtils.dateFormat(panel.getFechaFin()));

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EncuestasActivity.class);
                intent.putExtra(EXTRA_PANEL_INDEX, panels.indexOf(panel));
                startActivity(intent);
            }
        });

        return view;
    }
    // endregion
}
