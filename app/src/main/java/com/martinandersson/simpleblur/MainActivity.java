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

    public static final float FAST_BLUR_RADIUS = 10.0f;

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
        mBlurFragments.add(PlainFragment.newInstance(false));
        mBlurFragments.add(PlainFragment.newInstance(true));
        mBlurFragments.add(FastBlurFragment.newInstance(1.0f, FAST_BLUR_RADIUS, false));
        mBlurFragments.add(FastBlurFragment.newInstance(0.5f, FAST_BLUR_RADIUS, false));
        mBlurFragments.add(FastBlurFragment.newInstance(0.25f, FAST_BLUR_RADIUS, false));
        mBlurFragments.add(FastBlurFragment.newInstance(0.125f, FAST_BLUR_RADIUS, false));
        mBlurFragments.add(FastBlurFragment.newInstance(1.0f, FAST_BLUR_RADIUS, true));
        mBlurFragments.add(FastBlurFragment.newInstance(0.5f, FAST_BLUR_RADIUS, true));
        mBlurFragments.add(FastBlurFragment.newInstance(0.25f, FAST_BLUR_RADIUS, true));
        mBlurFragments.add(FastBlurFragment.newInstance(0.125f, FAST_BLUR_RADIUS, true));
        mBlurFragments.add(RenderScriptBlurFragment.newInstance(5.0f, false));
        mBlurFragments.add(RenderScriptBlurFragment.newInstance(10.0f, false));
        mBlurFragments.add(RenderScriptBlurFragment.newInstance(20.0f, false));
        mBlurFragments.add(RenderScriptBlurFragment.newInstance(5.0f, true));
        mBlurFragments.add(RenderScriptBlurFragment.newInstance(10.0f, true));
        mBlurFragments.add(RenderScriptBlurFragment.newInstance(20.0f, true));

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
