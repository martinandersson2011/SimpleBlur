package com.martinandersson.simpleblur;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by martin.andersson on 8/20/15.
 */
public class PlainFragment extends Fragment {
    public static final String TAG = PlainFragment.class.getSimpleName();

    @Bind(R.id.status_text)
    TextView mStatusText;

    boolean mUseColorFilter;

    @Bind(R.id.image_view)
    ImageView mImageView;

    public static PlainFragment newInstance(boolean useColorFilter) {
        PlainFragment fragment = new PlainFragment();
        fragment.mUseColorFilter = useColorFilter;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView");
        mStatusText.setText(mUseColorFilter ? "Color filter, but no blur." : "Plain image. Swipe left for the first blur");

        if (mUseColorFilter) {
            BlurUtils.setColorFilterBlue(mImageView);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}