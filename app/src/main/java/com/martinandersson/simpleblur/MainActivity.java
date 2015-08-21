package com.martinandersson.simpleblur;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    public static final float BLUR_RADIUS_10 = 10.0f;
    public static final float BLUR_RADIUS_12 = 12.0f;
    public static final float BLUR_RADIUS_14 = 14.0f;
    public static final float BLUR_RADIUS_16 = 16.0f;
    public static final float BLUR_RADIUS_18 = 18.0f;
    public static final float BLUR_RADIUS_20 = 20.0f;

    public static final float BLUR_SCALE_1_OF_4 = 0.25f;

    public static final float OPACITY_5_PERCENT = 0.05f;
    public static final float OPACITY_10_PERCENT = 0.10f;
    public static final float OPACITY_100_PERCENT = 1.00f;

    @Bind(R.id.pager)
    ViewPager mPager;

    private List<Fragment> mBlurFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.d(TAG, "onCreate");
        mBlurFragments = new ArrayList<>();

        // No blur
        mBlurFragments.add(PlainFragment.newInstance(OPACITY_100_PERCENT));
        mBlurFragments.add(PlainFragment.newInstance(OPACITY_5_PERCENT));

        // Fast blur
        mBlurFragments.add(FastBlurFragment.newInstance(BLUR_SCALE_1_OF_4, BLUR_RADIUS_10, OPACITY_100_PERCENT));
        mBlurFragments.add(FastBlurFragment.newInstance(BLUR_SCALE_1_OF_4, BLUR_RADIUS_10, OPACITY_10_PERCENT));
        mBlurFragments.add(FastBlurFragment.newInstance(BLUR_SCALE_1_OF_4, BLUR_RADIUS_12, OPACITY_10_PERCENT));
        mBlurFragments.add(FastBlurFragment.newInstance(BLUR_SCALE_1_OF_4, BLUR_RADIUS_14, OPACITY_10_PERCENT));
        mBlurFragments.add(FastBlurFragment.newInstance(BLUR_SCALE_1_OF_4, BLUR_RADIUS_16, OPACITY_10_PERCENT));
        mBlurFragments.add(FastBlurFragment.newInstance(BLUR_SCALE_1_OF_4, BLUR_RADIUS_18, OPACITY_10_PERCENT));
        mBlurFragments.add(FastBlurFragment.newInstance(BLUR_SCALE_1_OF_4, BLUR_RADIUS_20, OPACITY_10_PERCENT));

        // Render Script
        mBlurFragments.add(RenderScriptBlurFragment.newInstance(BLUR_RADIUS_20, OPACITY_100_PERCENT));
        mBlurFragments.add(RenderScriptBlurFragment.newInstance(BLUR_RADIUS_20, OPACITY_10_PERCENT));
        mBlurFragments.add(RenderScriptBlurFragment.newInstance(BLUR_RADIUS_20, OPACITY_5_PERCENT));

        mPager.setAdapter(new BlurPagerAdapter(getSupportFragmentManager()));
    }

    private class BlurPagerAdapter extends FragmentStatePagerAdapter {

        public BlurPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(final int position) {
            return mBlurFragments.get(position);
        }

        @Override
        public int getCount() {
            return mBlurFragments.size();
        }
    }
}
