package com.example.assignmenttemplateproject.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.dao.GeneralQuery;


/**
 * A simple {@link Fragment} subclass.
 */
public class Statistic extends Fragment {


    private TextView tvStatisticByDay, tvStatisticByMonth, tvStatisticByYear;

    private GeneralQuery generalQuery;

    public Statistic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        generalQuery = new GeneralQuery(getActivity());
        generalQuery.connectDatabase();

        View view = inflater.inflate(R.layout.fragment_statistic, container, false);

        findAllViewById(view);

        setTextView();

        return view;
    }

    private void findAllViewById(View view) {
        tvStatisticByDay = view.findViewById(R.id.tvStatisticByDay);
        tvStatisticByMonth = view.findViewById(R.id.tvStatisticByMonth);
        tvStatisticByYear = view.findViewById(R.id.tvStatisticByYear);
    }

    private void setTextView() {
        tvStatisticByDay.setText(String.valueOf(generalQuery.getTotalDailySaleByDay()));
        tvStatisticByMonth.setText(String.valueOf(generalQuery.getTotalDailySaleByMonth()));
        tvStatisticByYear.setText(String.valueOf(generalQuery.getTotalDailySaleByYear()));
    }

    @Override
    public void onPause() {
        super.onPause();
        generalQuery.disconnectDatabase();
    }
}
