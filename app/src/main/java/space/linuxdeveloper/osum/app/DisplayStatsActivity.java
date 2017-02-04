/*
 * Copyright (c) 2016, Gayan Weerakutti <gayan@linuxdeveloper.space>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package space.linuxdeveloper.osum.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;

import space.linuxdeveloper.osum.app.forecast.UsageForecast;
import space.linuxdeveloper.osum.app.stat.StatItemGen;
import space.linuxdeveloper.osum.app.stat.Stats;
import space.linuxdeveloper.osum.app.stat.StatsParser;
import space.linuxdeveloper.osum.app.view.AnimatedBackground;
import space.linuxdeveloper.osum.app.view.StatItemView;


public class DisplayStatsActivity extends AppCompatActivity {

    private ScrollView mScrollView;
    private ViewPager mViewPager;
    private PagerTabStrip mPagerTabStrip;
    private AnimatedBackground mAnimatedBackground;

    private StatItemView mPeakStatItemView;
    private StatItemView mTotalStatItemView;
    private StatItemView mExtraStatItemView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_stats);

        setViews();

        final String html = getIntent().getStringExtra(LoginActivity.EXTRA_HTML);

        new ParseStats().execute(html);
    }


    /**
     * Parse stats on a background thread and use the results
     * to update the StatItemViews on the UI thread
     */
    class ParseStats extends AsyncTask<String, Void, Stats> {
        @Override
        protected Stats doInBackground(String... html) {
            StatsParser statsParser = new StatsParser(html[0]);
            StatItemGen statItemGen = new StatItemGen(statsParser);
            return new Stats(statItemGen);
        }

        @Override
        protected void onPostExecute(Stats stats) {
            super.onPostExecute(stats);
            mPeakStatItemView.setStatItem(stats.getPeakStat());
            mTotalStatItemView.setStatItem(stats.getTotalStat());
            mExtraStatItemView.setStatItem(stats.getExtraStat());

            setupViewPager(stats);
        }
    }

    private void setupViewPager(Stats stats) {
        final UsageForecast usageForecast = new UsageForecast(stats);
        final String[] usageBadges = getResources().getStringArray(R.array.usage_badges);

        // view pager
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ForecastFragment.newInstance(position, usageForecast);
            }

            @Override
            public int getCount() {
                return 3; // size set explicitly
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return usageBadges[position];
            }
        });

        mPagerTabStrip.setVisibility(View.VISIBLE);
        mViewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewPager.setCurrentItem(usageForecast.getUserBadge());
            }
        }, 3000);
    }

    /**
     * Perform fullScroll UP or DOWN
     */
    public void onScrollViewChildClick(View v) {
        if (!mScrollView.fullScroll(View.FOCUS_DOWN))
            mScrollView.fullScroll(View.FOCUS_UP);
    }

    /**
     * Enable disable background animation when viewpages is clicked and held
     */
    public void toggleAnimation(View v) {
        mAnimatedBackground.toggleAnimation();
    }

    /**
     * Set references to views
     */
    private void setViews() {
        mAnimatedBackground = (AnimatedBackground) findViewById(R.id.animated_background);

        mScrollView = (ScrollView) findViewById(R.id.scrollview);
        mPeakStatItemView = (StatItemView) findViewById(R.id.peak_stat_item_view);
        mTotalStatItemView = (StatItemView) findViewById(R.id.total_stat_item_view);
        mExtraStatItemView = (StatItemView) findViewById(R.id.extra_stat_item_view);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
    }
}
