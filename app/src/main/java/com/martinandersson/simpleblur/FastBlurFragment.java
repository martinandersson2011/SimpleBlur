package com.martinandersson.simpleblur;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by martin.andersson on 8/20/15.
 */
public class FastBlurFragment extends Fragment {
    public static final String TAG = FastBlurFragment.class.getSimpleName();

    @Bind(R.id.image_view)
    ImageView mImageView;

    @Bind(R.id.blurred_view)
    ImageView mBlurredView;

    @Bind(R.id.status_text)
    TextView mStatusText;

    float mScaleFactor;
    float mBlurRadius;
    boolean mUseColorFilter;

    public static FastBlurFragment newInstance(float scaleFactor, float blurRadius, boolean useColorFilter) {
        FastBlurFragment fragment = new FastBlurFragment();
        fragment.mScaleFactor = scaleFactor;
        fragment.mBlurRadius = blurRadius;
        fragment.mUseColorFilter = useColorFilter;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onCreateView");
        if (mUseColorFilter) {
            BlurUtils.setColorFilterBlue(mBlurredView);
        }

        applyBlur();
        return view;
    }

    private void applyBlur() {
        mImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                mImageView.buildDrawingCache();

                Bitmap bitmap = mImageView.getDrawingCache();
                applyBlur(bitmap, mBlurredView);
                return true;
            }
        });
    }

    private void applyBlur(Bitmap bitmap, ImageView imageView) {
        long startMs = System.currentTimeMillis();

        Bitmap overlay = Bitmap.createBitmap(imageView.getMeasuredWidth(), imageView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-imageView.getLeft(), -imageView.getTop());
        canvas.drawBitmap(bitmap, 0, 0, null);
        overlay = BlurUtils.fastblur(overlay, mScaleFactor, (int) mBlurRadius);
//        imageView.setBackground(new BitmapDrawable(getResources(), overlay));
        imageView.setImageBitmap(overlay);
        mStatusText.setText((System.currentTimeMillis() - startMs) + "ms, " + TAG + ": radius=" + mBlurRadius + ", scale=" + mScaleFactor);
    }

}