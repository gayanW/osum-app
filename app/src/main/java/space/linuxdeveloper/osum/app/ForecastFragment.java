/*
 * Copyright (c) 2017, Gayan Weerakutti <gayan@linuxdeveloper.space>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.linuxdeveloper.osum.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import space.linuxdeveloper.osum.app.forecast.UsageForecast;
import timber.log.Timber;

public class ForecastFragment extends Fragment {

    private static final String EXTRA_POSITION = "space.linuxdeveloper.osum.POSITION" ;
    private static final String EXTRA_USER_BADGE = "space.linuxdeveloper.osum.BADGE" ;
    private static final String EXTRA_FORECAST_TEXT = "space.linuxdeveloper.osum.FORECAST_TEXT";
    private static final String EXTRA_PEAK_DIFF_TEXT = "space.linuxdeveloper.osum.PEAK_DIFF_TEXT";
    private static final String EXTRA_IS_ACTIVE = "space.linuxdeveloper.osum.IS_ACTIVE";

    private int mPosition;
    private int mUserBadge;
    private String mForecastText;
    private String mPeakDiffText;
    private boolean mIsActive;

    private TextView mForecastTextView;
    private TextView mPeakDiffTextView;

    private ImageView mForecastBadge;

    public static ForecastFragment newInstance(int position, UsageForecast usageForecast) {
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_POSITION, position);
        bundle.putInt(EXTRA_USER_BADGE, usageForecast.getUserBadge());
        bundle.putString(EXTRA_FORECAST_TEXT, usageForecast.getPeakForecastMsg());
        bundle.putString(EXTRA_PEAK_DIFF_TEXT, usageForecast.getDpDtAsText());
        bundle.putBoolean(EXTRA_IS_ACTIVE, position == usageForecast.getUserBadge());

        ForecastFragment forecastFragment = new ForecastFragment();
        forecastFragment.setArguments(bundle);

        return forecastFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();

        mPosition = args.getInt(EXTRA_POSITION);
        mUserBadge = args.getInt(EXTRA_USER_BADGE);
        mForecastText = args.getString(EXTRA_FORECAST_TEXT);
        mPeakDiffText = args.getString(EXTRA_PEAK_DIFF_TEXT);
        mIsActive = args.getBoolean(EXTRA_IS_ACTIVE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        setViews(view);
        if (mIsActive) activateView();
        updateView();
        return view;
    }

    private void activateView() {
        mPeakDiffTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorForecastImageActive));
        mForecastTextView.setTextColor(Color.WHITE);
        mForecastBadge.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorForecastImageActive));
    }

    private void updateView() {
        String fragmentName = getFragmentName();
        String userBadge = getUserBadge();

        String stringArrName = fragmentName + "_texts_when_" + userBadge;
        Timber.d("arrName: %s", stringArrName);


        if (mIsActive) {
            mForecastTextView.setText(mForecastText);
        }
        else {
            int arrIdentifier = getResources().getIdentifier(stringArrName, "array", getActivity().getApplicationContext().getPackageName());
            String[] forecastTexts = getResources().getStringArray(arrIdentifier);

            int randIndex = new Random().nextInt(forecastTexts.length);
            mForecastTextView.setText(forecastTexts[randIndex]);
        }

        mPeakDiffTextView.setText(mPeakDiffText);

        // update forecast badge
        switch (mPosition) {
            case 0: // saver
                mForecastBadge.setImageResource(R.drawable.ic_saver);
                break;
            case 1: // spender
                mForecastBadge.setImageResource(R.drawable.ic_spender);
                break;
            case 2: // gambler
                mForecastBadge.setImageResource(R.drawable.ic_gambler);
                break;
        }

    }

    public String getFragmentName() {
        String[] usageBadges = getResources().getStringArray(R.array.usage_badges);
        return usageBadges[mPosition].toLowerCase();
    }

    private String getUserBadge() {
        String[] userBadge = getResources().getStringArray(R.array.usage_badges);
        return userBadge[mUserBadge].toLowerCase();
    }

    private void setViews(View view) {
        mForecastTextView = (TextView) view.findViewById(R.id.forecast_text_view);
        mPeakDiffTextView = (TextView) view.findViewById(R.id.peak_diff_text_view);
        mForecastBadge = (ImageView) view.findViewById(R.id.forecast_badge);
    }

}
